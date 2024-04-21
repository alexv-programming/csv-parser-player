package com.exam.csvparser.repositories;

import com.exam.csvparser.model.Player;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ReactiveRedisRepository {
    private final ReactiveRedisTemplate<String, Player> reactiveRedisTemplate;

    public ReactiveRedisRepository(ReactiveRedisTemplate<String, Player> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Player> saveOrUpdate(Player player) {
        String key = "player:" + player.getId();
        return reactiveRedisTemplate.opsForValue().set(key, player).thenReturn(player);
    }

    public Mono<Player> findPlayerById(String id) {
        String key = "player:" + id;
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Flux<String> findAllPlayerIds() {
        return reactiveRedisTemplate.keys("player:*")
                .map(key -> key.replace("player:", ""));
    }
}
