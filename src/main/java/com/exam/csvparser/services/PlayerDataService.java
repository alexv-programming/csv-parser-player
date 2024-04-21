package com.exam.csvparser.services;

import com.exam.csvparser.dtos.BallDontliePlayerResponse;
import com.exam.csvparser.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PlayerDataService {
    private final WebClient webClient;

    private final ReactiveRedisTemplate<String, Player> reactiveRedisTemplate;

    @Autowired
    public PlayerDataService(@Value("${io.balldontlie.token}") String apiToken, ReactiveRedisTemplate<String, Player> redisTemplate) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.balldontlie.io/v1/players")
                .defaultHeader(HttpHeaders.AUTHORIZATION,apiToken)
                .build();
        this.reactiveRedisTemplate = redisTemplate;
    }

    public Mono<Player> getPlayerData(String playerId) {
        String redisKey = "player:" + playerId;
        Mono<Player> cachedData = reactiveRedisTemplate.opsForValue().get(redisKey);

        return cachedData.switchIfEmpty(Mono.defer(
                () -> fetchPlayerInfo(playerId)
                        .flatMap(
                                player -> reactiveRedisTemplate.opsForValue()
                                        .set(redisKey, player)
                                        .thenReturn(player))));
    }
    public Mono<Player> fetchPlayerInfo(String playerId) {
        return webClient.get()
                .uri("/{id}",  playerId)
                .retrieve()
                .bodyToMono(BallDontliePlayerResponse.class)
                .map(BallDontliePlayerResponse::getData)
                .map(apiPlayer -> {
                    Player player = new Player();
                    player.setId(playerId);
                    player.setFirstName(apiPlayer.getFirst_name());
                    player.setLastName(apiPlayer.getLast_name());
                    return player;
                });
    }
}
