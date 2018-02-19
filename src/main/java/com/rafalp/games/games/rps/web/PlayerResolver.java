package com.rafalp.games.games.rps.web;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public class PlayerResolver {

    protected RPSGamesRepository rpsGamesRepository;
    protected PlayerMoveDto playerMovement;

    public PlayerResolver(RPSGamesRepository rpsGamesRepository) {
        this.rpsGamesRepository = rpsGamesRepository;
    }

    public void resolvePlayerPosition(PlayerMoveDto playerMove){
        playerMovement = playerMove;
        if (isPlayerNo1()) {
            updatePlayer1Selection();
        } else if (isPlayerNo2()) {
            updatePlayer2Selection();
        } else {
            RPSGames waitingPlayer = rpsGamesRepository.findWaitingPlayer();
            if (waitingPlayer != null) {
                addSecondPlayer(waitingPlayer);
            } else {
                saveNewGame();
            }
        }
    }

    boolean isPlayerNo1(){
        return rpsGamesRepository.findByPlayer1(playerMovement.getPlayerID()) != null;
    }

    boolean isPlayerNo2(){
        return rpsGamesRepository.findByPlayer2(playerMovement.getPlayerID()) != null;
    }

    void updatePlayer1Selection(){
        rpsGamesRepository.updatePlayer1Game(playerMovement.getPlayerID(), playerMovement.getChampionName());
    }

    void updatePlayer2Selection(){
        rpsGamesRepository.updatePlayer2Game(playerMovement.getPlayerID(), playerMovement.getChampionName());
    }

    void addSecondPlayer(RPSGames waitingPlayer){
        rpsGamesRepository.addSecondPlayer(waitingPlayer.getPlayer1(), playerMovement.getPlayerID(), playerMovement.getChampionName());
    }

    RPSGames saveNewGame(){
        RPSGames newPlayer = new RPSGames(playerMovement.getPlayerID(), playerMovement.getChampionName(), true, playerMovement.getFightWithAI());
        rpsGamesRepository.save(newPlayer); //TODO add times handling
        return newPlayer;
    }
}
