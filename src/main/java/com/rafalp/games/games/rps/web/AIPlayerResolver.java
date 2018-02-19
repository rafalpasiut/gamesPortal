package com.rafalp.games.games.rps.web;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public class AIPlayerResolver extends PlayerResolver {

    RPSGames player;

    public AIPlayerResolver(RPSGamesRepository rpsGamesRepository) {
        super(rpsGamesRepository);
    }

    public void setPlayerData(PlayerMoveDto playerMoveDto) {
        playerMovement = playerMoveDto;
        if (isPlayerNo1()) {
            if (rpsGamesRepository.existsByPlayer1AndPlayer1IsPlayed(playerMoveDto.getPlayerID(), false)) {
                updatePlayer1Selection();
            }
        } else {
            player = saveNewGame();
        }
    }

    public void setAIData(PlayerMoveDto aiMove){
        playerMovement = aiMove;
        if(isPlayerNo2()) {
            updatePlayer2Selection();
        } else {
            addSecondPlayer(player);
        }
    }
}
