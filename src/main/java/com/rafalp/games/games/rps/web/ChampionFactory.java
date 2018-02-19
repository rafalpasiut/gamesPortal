package com.rafalp.games.games.rps.web;

import com.rafalp.games.games.rps.Champion;
import com.rafalp.games.games.rps.exception.CantCreateChampionException;

public class ChampionFactory {

    public Champion createChampionFromString(String champion) throws CantCreateChampionException {

        champion = "com.rafalp.games.games.rps." + champion;
        try {
            Class<Champion> championObject = (Class<Champion>) Class.forName(champion);
            return championObject.newInstance();
        } catch (Exception e) {
            throw new CantCreateChampionException("Cant create champion. " + champion + " not found.");
        }
    }
}
