package com.exam.csvparser.controllers;

import com.exam.csvparser.services.PlayerDataService;
import com.exam.csvparser.services.CsvParserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PlayerController {

    private final PlayerDataService playerDataService;
    private final CsvParserService csvParserService;

    @Autowired
    public PlayerController(PlayerDataService playerDataService, CsvParserService csvParserService) {
        this.playerDataService = playerDataService;
        this.csvParserService = csvParserService;
    }

    @GetMapping(path = "/player", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getPlayerData() throws Exception {
        return Flux.fromIterable(csvParserService.parseCsvFile())
                .flatMap(playerId -> playerDataService.getPlayerData(playerId)
                        .map(info -> String.join(",",
                                String.valueOf(info.getId()),
                                info.getFirstName(),
                                info.getLastName()
                        )));
    }

}
