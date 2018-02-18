package com.rafalp.games.games.rps;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WebRPSGame extends Game {

    public static final int GAMES_TO_WIN = 3;
    @Autowired
    private RPSGamesRepository rpsGamesRepository;

    public WebRPSGame() {
    }

    public RPSFightResult play(String userChampion) throws CantCreateChampionException {

        Champion playerChampion = createChampionFromString(userChampion);
        Champion aIChampion = randomChampion();
        FightResult fightResult = fight(playerChampion, aIChampion);
        String message = gameMaster.fightResult(fightResult, playerChampion, aIChampion);

        return new RPSFightResult(playerChampion.getName(), aIChampion.getName(), fightResult.toString(), message, true, true);
    }

    public void playMulti(PlayerMoveDto playerMove) throws CantCreateChampionException {
        if (rpsGamesRepository.findByPlayer1(playerMove.getPlayerID()) != null) {
            rpsGamesRepository.updatePlayer1Game(playerMove.getPlayerID(), playerMove.getChampionName());
        } else if (rpsGamesRepository.findByPlayer2(playerMove.getPlayerID()) != null) {
            rpsGamesRepository.updatePlayer2Game(playerMove.getPlayerID(), playerMove.getChampionName());
        } else {
            RPSGames waitingPlayer = rpsGamesRepository.findWaitingPlayer();
            if (waitingPlayer != null) {
                rpsGamesRepository.addSecondPlayer(waitingPlayer.getPlayer1(), playerMove.getPlayerID(), playerMove.getChampionName());
            } else {
                rpsGamesRepository.save(new RPSGames(playerMove.getPlayerID(), playerMove.getChampionName(), true, playerMove.getFightWithAI())); //TODO add times handling
            }
        }
        if (isFightReady(playerMove.getPlayerID())) {
            RPSGames rpsGames = rpsGamesRepository.findByPlayer1OrPlayer2(playerMove.getPlayerID(), playerMove.getPlayerID());
            Champion playerChampion1 = createChampionFromString(rpsGames.getPlayer1Champion());
            Champion playerChampion2 = createChampionFromString(rpsGames.getPlayer2Champion());
            FightResult fightResult = fight(playerChampion1, playerChampion2);
            rpsGames = resolveFightResult(rpsGames, fightResult, playerChampion1, playerChampion2);
            rpsGamesRepository.save(rpsGames);
        }
    }

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
        }
        rpsGamesRepository.save(game);
        return game.getFightResultForPlayer(playerName);
    }

    private RPSGames resolveFightResult(RPSGames game, FightResult result, Champion player1, Champion player2) {
        switch (result) {
            case WIN:
                if (game.getPlayer1Count() != null) {
                    game.setPlayer1Count(game.getPlayer1Count() + 1);
                } else {
                    game.setPlayer1Count(1);
                }
                game.setPlayer1Win(true);
                break;
            case LOSE:
                if (game.getPlayer2Count() != null) {
                    game.setPlayer2Count(game.getPlayer2Count() + 1);
                } else {
                    game.setPlayer2Count(1);
                }
                game.setPlayer2Win(true);
                break;
            case TIE:
                break;
        }
        game.setMessage(gameMaster.fightResult(result, player1, player2));
        game.setIsRoundFinished(true);
        if (game.getPlayer1Count() >= GAMES_TO_WIN || game.getPlayer2Count() >= GAMES_TO_WIN) {
            game.setIsGameFinished(true);
        }
        return game;
    }


    private Boolean isFightReady(String playerName) {
        return rpsGamesRepository.isFightReady(playerName) != null;
    }

    private Champion createChampionFromString(String champion) throws CantCreateChampionException {

        champion = "com.rafalp.games.games.rps." + champion;
        try {
            Class<Champion> championObject = (Class<Champion>) Class.forName(champion);
            return championObject.newInstance();
        } catch (Exception e) {
            throw new CantCreateChampionException("Cant create champion. " + champion + " not found.");
        }
    }

}
