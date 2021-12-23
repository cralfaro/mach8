package com.mach8.nba.controller;

import com.mach8.nba.model.Player;
import com.mach8.nba.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {


    Logger logger = LoggerFactory.getLogger(PlayerController.class);

    private static final String NO_MATCHES_FOUND = "No matches found";

    final
    PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/pairs/height/{total}/detail")
    public ResponseEntity<Object> getPairsByHeightInchesDetails(@PathVariable("total")Integer total) {
        logger.info("Searching pairs by Total Inches {}", total);
        Map<String, Object> data = new HashMap<>();
        try {
            List<List<Player>> playersPairs = playerService.findPairsPlayersByHeightIn(total);
            logger.info("Found total of {} pairs", playersPairs.size());
            if(!playersPairs.isEmpty()) {
                data.put("totalPairs", playersPairs.size());
                data.put("pairs", playersPairs);
                return new ResponseEntity<>(data, HttpStatus.OK);
            }
            data.put("totalPairs", 0);
            data.put("message", NO_MATCHES_FOUND);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch(Exception ex){
            logger.error("An exception trying to find pairs {}", ex);
            data.put("totalPlayers", 0);
            data.put("error", ex.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pairs/height/{total}")
    public ResponseEntity<Object> getPairsByHeightInches(@PathVariable("total")Integer total) {
        logger.info("Searching pairs by Total Inches {}", total);
        Map<String, Object> data = new HashMap<>();
        try {
            List<List<Player>> playersPairs = playerService.findPairsPlayersByHeightIn(total);
            logger.info("Found total of {} pairs", playersPairs.size());
            if(!playersPairs.isEmpty()) {
                data.put("totalPairs", playersPairs.size());
                data.put("pairs", playersPairs.stream().map(l->l.stream().map(Player::getFullName).collect(Collectors.joining(" - "))));
                return new ResponseEntity<>(data, HttpStatus.OK);
            }
            data.put("totalPairs", 0);
            data.put("message", NO_MATCHES_FOUND);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch(Exception ex){
            logger.error("An exception trying to find pairs {}", ex);
            data.put("totalPlayers", 0);
            data.put("error", ex.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}