package com.rafalp.games.web.rest;

import com.rafalp.games.games.rps.RPSFightResult;
import com.rafalp.games.games.rps.WebAIRPSGame;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RPSController {

    @Autowired
    WebAIRPSGame webAIRPSGame;

    @RequestMapping(method = RequestMethod.GET, value = "/rpsFightWithAI")
    public RPSFightResult fight(@RequestParam String champion) throws CantCreateChampionException{
        return webAIRPSGame.play(champion);
    }
}
