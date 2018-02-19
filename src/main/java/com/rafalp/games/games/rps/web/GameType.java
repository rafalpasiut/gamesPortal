package com.rafalp.games.games.rps.web;

import com.rafalp.games.games.rps.RPSFightResult;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public interface GameType {

    int GAMES_TO_WIN = 3;

    void play(PlayerMoveDto playerMove) throws CantCreateChampionException;
    RPSFightResult getFightStatus(String playerName);
}
