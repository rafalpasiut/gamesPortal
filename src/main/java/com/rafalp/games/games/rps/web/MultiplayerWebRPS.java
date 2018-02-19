package com.rafalp.games.games.rps.web;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.games.rps.*;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public class MultiplayerWebRPS extends Game implements GameType {

    private RPSGamesRepository rpsGamesRepository;
    private FightResolver fightResolver;
    private ChampionFactory championFactory;
    private GameMaster gameMaster;

    public MultiplayerWebRPS(RPSGamesRepository rpsGamesRepository) {
        this.rpsGamesRepository = rpsGamesRepository;
        this.fightResolver = new FightResolver(rpsGamesRepository);
        this.championFactory = new ChampionFactory();
        this.gameMaster = new GameMaster();
    }

    @Override
    public void play(PlayerMoveDto playerMove) throws CantCreateChampionException {
        fightResolver.resolvePlayerPosition(playerMove);
        if (isFightReady(playerMove.getPlayerID())) {
            performRound(playerMove);
        }
    }

    @Override
    public RPSFightResult getFightStatus(String playerName) {
        RPSFightResult fightResult;
        RPSGames game = rpsGamesRepository.findByPlayer1OrPlayer2AndIsRoundFinished(playerName, playerName, true);
        if (game != null) {
            if (rpsGamesRepository.findByPlayer1(playerName) != null) {
                game.setPlayer1IsPlayed(false);
            } else {
                game.setPlayer2IsPlayed(false);
            }
            if (!game.isPlayer1IsPlayed() && !game.isPlayer2IsPlayed()) {
                game.setIsRoundFinished(false);
            }
            rpsGamesRepository.save(game);
            if(!game.isIsRoundFinished() && game.isIsGameFinished()){
                rpsGamesRepository.delete(game);
            }
            return game.getFightResultForPlayer(playerName);
        }
        return new RPSFightResult();
    }

    private void performRound(PlayerMoveDto playerMove) throws CantCreateChampionException {
        RPSGames rpsGames = rpsGamesRepository.findByPlayer1OrPlayer2(playerMove.getPlayerID(), playerMove.getPlayerID());
        Champion playerChampion1 = championFactory.createChampionFromString(rpsGames.getPlayer1Champion());
        Champion playerChampion2 = championFactory.createChampionFromString(rpsGames.getPlayer2Champion());
        FightResult fightResult = fight(playerChampion1, playerChampion2);
        rpsGames = fightResolver.resolveFightResult(rpsGames, fightResult, playerChampion1, playerChampion2, gameMaster);
        rpsGamesRepository.save(rpsGames);
    }


    private Boolean isFightReady(String playerName) {
        return rpsGamesRepository.isFightReady(playerName) != null;
    }
}
