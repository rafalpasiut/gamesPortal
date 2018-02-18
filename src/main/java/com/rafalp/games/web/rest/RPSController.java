package com.rafalp.games.web.rest;

import com.rafalp.games.games.rps.RPSFightResult;
import com.rafalp.games.games.rps.WebRPSGame;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RPSController {

    @Autowired
    WebRPSGame webRPSGame;

    @RequestMapping(method = RequestMethod.GET, value = "/rpsFightWithAI")
    public RPSFightResult fight(@RequestParam String champion) throws CantCreateChampionException {

        return webRPSGame.play(champion);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rpsFightMulti")
    public void fight(@RequestBody PlayerMoveDto playerData) throws CantCreateChampionException {
        webRPSGame.playMulti(playerData);
    }
}
