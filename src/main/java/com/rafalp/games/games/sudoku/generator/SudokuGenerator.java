package com.rafalp.games.games.sudoku.generator;

import com.rafalp.games.games.sudoku.board.SudokuBoard;
import com.rafalp.games.games.sudoku.board.SudokuBoardWebDTO;
import com.rafalp.games.games.sudoku.board.SudokuCell;
import com.rafalp.games.games.sudoku.board.SudokuMapper;
import com.rafalp.games.games.sudoku.exceptions.NoSolutionException;
import com.rafalp.games.games.sudoku.exceptions.NoUniqueSolution;
import com.rafalp.games.games.sudoku.solver.SudokuSolver;
import com.rafalp.games.games.sudoku.solver.algorithm.backtracking.EnchancedBacktrackingAlgorithm;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Random;
import java.util.stream.IntStream;

public class SudokuGenerator {

    public static final int MAX_GEN_TRIES = 80;
    private int[][] board;
    private SudokuBoard sudokuBoard;
    private SudokuSolver sudokuSolver = new EnchancedBacktrackingAlgorithm();
    private SudokuMapper sudokuMapper = new SudokuMapper();
    private SudokuBoard solution;

    public int[][] nextBoard(int difficulty) {
        switch (difficulty) {
            case 1:
                difficulty = 45;
                break;
            case 2:
                difficulty = 50;
                break;
            case 3:
                difficulty = 55;
                break;
            default:
                difficulty = 50;
                break;
        }
        board = new int[SudokuBoard.SUDOKU_SIZE][SudokuBoard.SUDOKU_SIZE];
        nextCell(0, 0);
        makeHoles(difficulty);
        return board;
    }

    public boolean nextCell(int x, int y) {
        int nextX = x;
        int nextY = y;
        int[] toCheck = IntStream.range(1, SudokuBoard.SUDOKU_SIZE + 1).toArray();
        Random random = new Random();
        int tmp;
        int current;
        int top = toCheck.length;

        for (int i = top - 1; i > 0; i--) {
            current = random.nextInt(i);
            tmp = toCheck[current];
            toCheck[current] = toCheck[i];
            toCheck[i] = tmp;
        }

        for (int aToCheck : toCheck) {
            if (legalMove(x, y, aToCheck)) {
                board[x][y] = aToCheck;
                if (x == 8) {
                    if (y == 8) {
                        return true;
                    } else {
                        nextX = 0;
                        nextY = y + 1;
                    }
                } else {
                    nextX = x + 1;
                }
                if (nextCell(nextX, nextY)) {
                    return true;
                }
            }
        }
        board[x][y] = 0;
        return false;
    }

    private boolean legalMove(int x, int y, int current) {
        for (int i = 0; i < SudokuBoard.SUDOKU_SIZE; i++) {
            if (current == board[x][i]) {
                return false;
            }
        }
        for (int i = 0; i < SudokuBoard.SUDOKU_SIZE; i++) {
            if (current == board[i][y]) {
                return false;
            }
        }
        int cornerX = 0;
        int cornerY = 0;
        if (x > 2) {
            if (x > 5) {
                cornerX = 6;
            } else {
                cornerX = 3;
            }
        }
        if (y > 2) {
            if (y > 5) {
                cornerY = 6;
            } else {
                cornerY = 3;
            }
        }
        for (int i = cornerX; i < 10 && i < cornerX + 3; i++)
            for (int j = cornerY; j < 10 && j < cornerY + 3; j++)
                if (current == board[i][j])
                    return false;
        return true;
    }


    public void makeHoles(int holesToMake) {

        Point cellCoords;
        int tryCount = 0;
        int remainingHoles = holesToMake;
        boolean finish = false;

        while (!finish) {
            cellCoords = pickRandomCoordinate();

            int cellValue = board[cellCoords.x][cellCoords.y];
            board[cellCoords.x][cellCoords.y] = SudokuCell.EMPTY_CELL_VALUE;
            sudokuBoard = sudokuMapper.boardFromArray(board);
            try {
                sudokuSolver = new EnchancedBacktrackingAlgorithm();
                solution = sudokuSolver.solve(sudokuBoard);
                remainingHoles--;
            } catch (NoSolutionException e) {
                board[cellCoords.x][cellCoords.y] = cellValue;
                tryCount++;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            } catch (NoUniqueSolution noUniqueSolution) {
                board[cellCoords.x][cellCoords.y] = cellValue;
                tryCount++;
            }
            if (remainingHoles < 1 || tryCount >= MAX_GEN_TRIES) {
                finish = true;
            }
        }

        System.out.println(sudokuBoard);
        System.out.println(solution);
        System.out.println(remainingHoles);
        System.out.println(tryCount);
    }

    private Point pickRandomCoordinate() {
        Random random = new Random();
        Point point = new Point();
        while (true) {
            point.x = random.nextInt(SudokuBoard.SUDOKU_SIZE);
            point.y = random.nextInt(SudokuBoard.SUDOKU_SIZE);
            if (board[point.x][point.y] != SudokuCell.EMPTY_CELL_VALUE) {
                return point;
            }
        }
    }

    public void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(board[i][j] + "  ");
            System.out.println();
        }
        System.out.println();
    }

    public void printRaw() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (board[i][j] != SudokuCell.EMPTY_CELL_VALUE) {
                    System.out.print((i + 1) + "," + (j + 1) + "," + board[i][j] + ",");
                }
        }
        System.out.println();
    }

    public SudokuBoardWebDTO getSudokuDTO() {
        return sudokuMapper.mapBoardToWebDTO(board, solution);
    }

    public static void main(String[] args) {
        SudokuGenerator sg = new SudokuGenerator();
        sg.nextBoard(45);
        sg.print();
        sg.printRaw();
    }
}
