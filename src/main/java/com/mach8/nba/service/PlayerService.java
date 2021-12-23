package com.mach8.nba.service;

import com.mach8.nba.model.Player;

import java.util.List;

public interface PlayerService {
    List<List<Player>> findPairsPlayersByHeightIn(Integer total) ;
}
