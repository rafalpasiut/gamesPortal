package com.rafalp.games.games.sudoku.solver.algorithm.backtracking;

import com.rafalp.games.games.sudoku.board.SudokuBoard;
import com.rafalp.games.games.sudoku.board.SudokuCell;
import com.rafalp.games.games.sudoku.exceptions.BadNumberException;
import com.rafalp.games.games.sudoku.exceptions.NoSolutionException;
import com.rafalp.games.games.sudoku.exceptions.NoUniqueSolution;
import com.rafalp.games.games.sudoku.solver.SudokuSolver;

public class EnchancedBacktrackingAlgorithm implements SudokuSolver {

    private Backtracker backtracker;
    private SudokuBoard sudoku;
    private SudokuBoard soluion;
    private Boolean uniqueSolution;
    private CellsIterator cellsIterator;

    public EnchancedBacktrackingAlgorithm() {
        backtracker = new Backtracker();
    }

    @Override
    public SudokuBoard solve(SudokuBoard sudokuToSolve) throws CloneNotSupportedException, NoSolutionException, NoUniqueSolution {
        int solutionCount = 0;

        while (true) {
            if (sudokuToSolve != null) {
                if (solutionCount == 0) {
                    sudoku = sudokuToSolve;
                }
                boolean sudokuChanged = true;
                while (!sudoku.areAllCellsFilled()) {
                    if (sudokuChanged) {
                        try {
                            sudokuChanged = runCheckAlgorithms();
                        } catch (NoSolutionException e) {
                            if (solutionCount == 0) {
                                throw e;
                            } else {
                                return soluion;
                            }
                        }

                    } else {
                        backtracker.guessNumber(sudoku);
                        sudokuChanged = true;
                    }
                }
                soluion = sudoku.deepCopy();
                solutionCount++;
                if (solutionCount > 1) {
                    throw new NoUniqueSolution();
                }
                try {
                    sudoku = backtracker.checkBactrackAndRestore();
                } catch (NoSolutionException e) {
                    if (solutionCount == 0) {
                        throw e;
                    } else {
                        return soluion;
                    }
                }
            }
        }
    }

    private boolean runCheckAlgorithms() throws NoSolutionException {
        boolean sudokuChanged;
        try {
            sudokuChanged = checkSudokuWithAlgorithm(this::checkNumberSimple, false);
            if (!sudokuChanged) {
                sudokuChanged = checkSudokuWithAlgorithm(this::checkNumberByElimination, true);
            }
        } catch (BadNumberException e) {
            sudoku = backtracker.checkBactrackAndRestore();
            sudokuChanged = true;
        }
        return sudokuChanged;
    }

    private boolean checkSudokuWithAlgorithm(SudokuCheckAlgorithm algorithm, boolean breakOnChange) throws BadNumberException {
        cellsIterator = new CellsIterator(sudoku, algorithm, breakOnChange);
        return cellsIterator.iterateThroughAllCells();
    }

    private boolean checkNumberSimple(SudokuCell cell, Integer number) {
        Checker checker = new Checker(sudoku);
        boolean isNumberNOK = checker.isNumberInRowColumnSection(cell, number);
        if (isNumberNOK) {
            return true;
        } else {
            if (cell.isOnlyOneSolution()) {
                cell.setSolution();
                return true;
            }
            return false;
        }
    }

    private boolean checkNumberByElimination(SudokuCell cell, Integer number) {
        Checker checker = new Checker(sudoku);
        boolean isNumberNOK = checker.canNumberBeAnywhereElse(cell, number);
        if (isNumberNOK) {
            return false;
        } else {
            cell.setValue(number);
            return true;
        }

    }
}
