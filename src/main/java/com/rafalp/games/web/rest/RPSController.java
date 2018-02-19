package com.rafalp.games.web.rest;

import com.rafalp.games.games.rps.RPSFightResult;
import com.rafalp.games.games.rps.web.AIWebRPS;
import com.rafalp.games.games.rps.web.GameType;
import com.rafalp.games.games.rps.web.MultiplayerWebRPS;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RPSController {

    @Autowired
    RPSGamesRepository gamesRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/rpsFightMulti")
    public void fight(@RequestBody PlayerMoveDto playerData) throws CantCreateChampionException {
        getGame(playerData.getFightWithAI()).play(playerData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rpsGetFightStatus")
    public RPSFightResult getFightStatus(@RequestParam String player, @RequestParam Boolean isAI) {
        return getGame(isAI).getFightStatus(player);
    }

    private GameType getGame(Boolean isAI){
        if(isAI){
            return new AIWebRPS(gamesRepository);
        } else {
            return new MultiplayerWebRPS(gamesRepository);
        }
    }
}
