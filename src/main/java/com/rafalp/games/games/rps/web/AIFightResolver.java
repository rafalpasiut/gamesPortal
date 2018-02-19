package com.rafalp.games.games.rps.web;

import com.rafalp.games.games.rps.Champion;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public class AIFightResolver extends FightResolver {

    AIPlayerResolver aiPlayerResolver;

    public AIFightResolver(RPSGamesRepository rpsGamesRepository) {
        super(rpsGamesRepository);
        aiPlayerResolver = new AIPlayerResolver(rpsGamesRepository);
    }

    public void setPlayersMoves(PlayerMoveDto playerMove, Champion aiChampion) {
        PlayerMoveDto aiMove = new PlayerMoveDto(aiChampion.getName(), true, playerMove.getPlayerID() + "_AI");
        aiPlayerResolver.setPlayerData(playerMove);
        aiPlayerResolver.setAIData(aiMove);
    }
}
