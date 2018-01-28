package com.rafalp.games.games.sudoku.board;

public class SudokuBoardWebDTO {

    private CellWebDTO[][] board;
    private Long userId = (long)-1;

    public SudokuBoardWebDTO() {
    }

    public SudokuBoardWebDTO(CellWebDTO[][] board, Long userId) {
        this.board = board;
        this.userId = userId;
    }

    public SudokuBoardWebDTO(CellWebDTO[][] board) {
        this.board = board;
    }

    public CellWebDTO[][] getBoard() {
        return board;
    }

    public Long getUserId() {
        return userId;
    }

    public void setBoard(CellWebDTO[][] board) {
        this.board = board;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
