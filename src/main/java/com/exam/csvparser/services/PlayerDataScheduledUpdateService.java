package com.exam.csvparser.services;

import com.exam.csvparser.controllers.WebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.exam.csvparser.repositories.ReactiveRedisRepository;

@Service
public class PlayerDataScheduledUpdateService {

    private final PlayerDataService playerDataService;
    private final ReactiveRedisRepository redisRepository;
    private final WebSocketController webSocketController;

    @Autowired
    public PlayerDataScheduledUpdateService(PlayerDataService playerDataService, ReactiveRedisRepository redisRepository, WebSocketController webSocketController) {
        this.playerDataService = playerDataService;
        this.redisRepository = redisRepository;
        this.webSocketController = webSocketController;
    }


    @Scheduled(fixedRate = 900000)
    public void updatePlayerData() {

        redisRepository.findAllPlayerIds()
                .flatMap(playerDataService::fetchPlayerInfo)
                .flatMap(player -> redisRepository.saveOrUpdate(player)
                        .doOnSuccess(savedPlayer -> {
                            String updateMessage = "Player with ID " + savedPlayer.getId() + " was updated.";
                            webSocketController.notifyFrontend(updateMessage);
                        })
                )
                .subscribe();
    }
}
