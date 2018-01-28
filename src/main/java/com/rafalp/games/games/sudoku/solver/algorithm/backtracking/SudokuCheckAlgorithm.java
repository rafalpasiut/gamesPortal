package com.rafalp.games.games.sudoku.solver.algorithm.backtracking;

import com.rafalp.games.games.sudoku.board.SudokuCell;
import com.rafalp.games.games.sudoku.exceptions.BadNumberException;

public interface SudokuCheckAlgorithm {

    boolean runAlgorithm(SudokuCell cell, Integer number) throws BadNumberException;
}
