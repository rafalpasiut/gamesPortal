package com.rafalp.games.games.sudoku.solver.algorithm.backtracking;

import com.rafalp.games.games.sudoku.board.SudokuCell;

public interface CellChecker {

    boolean checkCell(SudokuCell cell, int number);
}
