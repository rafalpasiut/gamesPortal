package com.rafalp.games.games.rps.web;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.games.rps.Champion;
import com.rafalp.games.games.rps.FightResult;
import com.rafalp.games.games.rps.Game;
import com.rafalp.games.games.rps.RPSFightResult;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

public class AIWebRPS extends Game implements GameType {

    private AIFightResolver fightResolver;
    private RPSGamesRepository rpsGamesRepository;
    private ChampionFactory championFactory;

    public AIWebRPS(RPSGamesRepository rpsGamesRepository) {
        this.rpsGamesRepository = rpsGamesRepository;
        fightResolver = new AIFightResolver(rpsGamesRepository);
        championFactory = new ChampionFactory();
    }

    @Override
    public void play(PlayerMoveDto playerMove) throws CantCreateChampionException {
        fightResolver.setPlayersMoves(playerMove, randomChampion());
        performRound(playerMove);
    }

    @Override
    public RPSFightResult getFightStatus(String playerName) {
        RPSFightResult fightResult;
        RPSGames game = rpsGamesRepository.findByPlayer1AndIsRoundFinished(playerName, true);
        if (game != null) {
            game.setPlayer1IsPlayed(false);
            if (!game.isPlayer1IsPlayed() && !game.isPlayer2IsPlayed()) {
                game.setIsRoundFinished(false);
            }
            rpsGamesRepository.save(game);
            if (!game.isIsRoundFinished() && game.isIsGameFinished()) {
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
        rpsGames.setPlayer2IsPlayed(false);
        rpsGamesRepository.save(rpsGames);
    }
}
