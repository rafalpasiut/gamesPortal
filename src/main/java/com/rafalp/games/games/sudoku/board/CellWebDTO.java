package com.rafalp.games.games.sudoku.board;

public class CellWebDTO {

    private int value;
    private int solution;
    private Boolean draftNumber;
    private int x;
    private int y;

    public CellWebDTO() {
    }

    public CellWebDTO(int value, int solution, int x, int y, boolean draftNumber) {
        this.value = value;
        this.solution = solution;
        this.draftNumber = draftNumber;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public int getSolution() {
        return solution;
    }

    public Boolean getDraftNumber() {
        return draftNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public void setDraftNumber(Boolean draftNumber) {
        this.draftNumber = draftNumber;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
