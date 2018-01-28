package com.rafalp.games.games.sudoku;

import com.rafalp.games.games.sudoku.processor.GameProcessor;

public class Game {

    public static void main(String[] args) {
        GameProcessor processor = new GameProcessor();
        processor.run();
    }
}
