package com.mach8.nba.service.impl;

import com.mach8.nba.comparator.PlayerInchesComparator;
import com.mach8.nba.model.Player;
import com.mach8.nba.service.PlayerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerServiceImpl implements PlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private static final String RAW_DATA_URL = "https://mach-eight.uc.r.appspot.com/";

    @Override
    public List<List<Player>> findPairsPlayersByHeightIn(Integer total) {
        List<List<Player>> results = new ArrayList<>();
        List<Player> players = loadPlayersFromURL();
        players.sort(new PlayerInchesComparator());
        logger.info("Processing {} players", players.size());
        List<String> uniquePairs = new ArrayList<>();
        int i = 0, j = players.size();
        for(; i < j; i++){
            Player player = players.get(i);
            logger.info("Processing player {}", player.getFullName());
            if(player.getHeightInches() < total){
                Integer secondPlayerHeight = total - player.getHeightInches();
                for(j = players.size() - 1; j > 0 ;j--){
                    Player secondPlayer = players.get(j);
                    if(secondPlayer.getHeightInches() > secondPlayerHeight){
                        j = players.size() - 1;
                        break;
                    }else if(secondPlayer.getHeightInches().equals(secondPlayerHeight)
                            && !player.equals(secondPlayer)
                            && !duplicatedPair(player, secondPlayer, uniquePairs)){
                        logger.info("Match!! {} - {}", player.getFullName(), secondPlayer.getFullName());
                        results.add(Arrays.asList(player, secondPlayer));
                    }
                }
            }
        }
        return results;
    }

    private boolean duplicatedPair(Player player, Player secondPlayer, List<String> uniquePairs) {
        if(!uniquePairs.contains(player.getIndex() + "__" + secondPlayer.getIndex()) && !uniquePairs.contains(secondPlayer.getIndex() + "__" + player.getIndex())){
            uniquePairs.add(player.getIndex() + "__" + secondPlayer.getIndex());
            return false;
        }
        return true;
    }

    private List<Player> loadPlayersFromURL() {
        List<Player> players = new ArrayList<>();
        try (InputStream input = new URL(RAW_DATA_URL).openStream()) {
            BufferedReader re = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String jsonData = readData(re);
            JSONObject json = new JSONObject(jsonData);
            JSONArray jsonPlayers = (JSONArray) json.get("values");
            AtomicInteger index = new AtomicInteger(1);
            jsonPlayers.forEach(j -> {
                JSONObject jsonObj = ((JSONObject) j);
                players.add(Player.build(
                        index.getAndIncrement(),
                        (String) jsonObj.get("first_name"),
                        (String) jsonObj.get("last_name"),
                        Integer.parseInt((String) jsonObj.get("h_in")),
                        Float.parseFloat((String) jsonObj.get("h_meters"))));
            });
        } catch (Exception e) {
            logger.error("An exception trying to read players from URL {}", e);
            return players;
        }
        return players;
    }

    private String readData(Reader re) throws IOException {
        StringBuilder str = new StringBuilder();
        int temp;
        do {
            temp = re.read();
            str.append((char) temp);
        } while (temp != -1);
        return str.toString();

    }
}
