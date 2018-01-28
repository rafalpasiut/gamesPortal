package com.rafalp.games.games.rps;

public final class Scissors extends Champion {

    public Scissors() {
        super("Scissors");
        super.putWins("Paper", "cuts");
        super.putWins("Lizard", "decapitates");
    }
}
