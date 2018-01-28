package com.rafalp.games.games.sudoku.solver.algorithm.backtracking;

import com.rafalp.games.games.sudoku.board.SudokuBoard;
import com.rafalp.games.games.sudoku.exceptions.NoSolutionException;

import java.util.ArrayList;
import java.util.List;

public class Backtracker {

    private NumberGuesser numberGuesser;
    private List<SudokuBoard> backtrackList;

    public Backtracker() {
        numberGuesser = new NumberGuesser();
        backtrackList = new ArrayList<>();
    }

    public SudokuBoard checkBactrackAndRestore() throws NoSolutionException {
        if (backtrackList.isEmpty()) {
            throw new NoSolutionException();
        } else {
            return restoreSudokuFromBacktrack();
        }
    }

    public void guessNumber(SudokuBoard sudoku) throws CloneNotSupportedException {
        numberGuesser.guessNumber(sudoku, backtrackList);
    }

    private SudokuBoard restoreSudokuFromBacktrack() {
        SudokuBoard sudokuBoard;
        sudokuBoard = backtrackList.get(backtrackList.size() - 1);
        backtrackList.remove(backtrackList.size() - 1);
        return sudokuBoard;
    }
}
