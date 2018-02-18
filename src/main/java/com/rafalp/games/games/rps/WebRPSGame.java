package com.rafalp.games.games.rps;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;
import org.springframework.stereotype.Controller;

@Controller
public class WebRPSGame extends Game {

    private RPSGamesRepository rpsGamesRepository;

    public WebRPSGame() {
    }

    public RPSFightResult play(String userChampion) throws CantCreateChampionException {

        Champion playerChampion = createChampionFromString(userChampion);
        Champion aIChampion = randomChampion();
        FightResult fightResult = fight(playerChampion, aIChampion);
        String message = gameMaster.fightResult(fightResult, playerChampion, aIChampion);

        return new RPSFightResult(playerChampion.getName(), aIChampion.getName(), fightResult.toString(), message, true);
    }

    public void playMulti(PlayerMoveDto playerMove) {
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
    }

    public FightResult getFightStatus(String playerName){

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
