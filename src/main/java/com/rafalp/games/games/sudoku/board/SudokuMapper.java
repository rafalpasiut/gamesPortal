package com.rafalp.games.games.sudoku.board;

import com.rafalp.games.domain.SudokuCellEntity;
import com.rafalp.games.games.sudoku.creator.CellValueDTO;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SudokuMapper {

    public SudokuBoard boardFromArray(int[][] board) {
        SudokuBoard sudokuBoard = new SudokuBoard();
        int i = 1;
        for (int[] row : board) {
            int j = 1;
            for (int cellValue : row) {
                sudokuBoard.setCell(new CellValueDTO(i, j, cellValue));
                j++;
            }
            i++;
        }
        return sudokuBoard;
    }

    public int[][] sudokuArrayFromBoard(SudokuBoard sudokuBoard) {
        int[][] board = new int[9][9];

        sudokuBoard.getRows().stream().forEach(row -> {
            row.getCells().stream().forEach(cell -> {
                board[cell.getRowNumber() - 1][cell.getColumnNumber() - 1] = cell.getValue();
            });
        });
        return board;
    }

    public SudokuBoardWebDTO mapBoardToWebDTO(int[][] board, SudokuBoard solution) {
        int[][] solutionArray = sudokuArrayFromBoard(solution);
        CellWebDTO[][] webBoard = new CellWebDTO[9][9];
        int i = 0, j;
        for (int[] row : board) {
            j = 0;
            for (int cell : row) {
                boolean isDraft = (board[i][j] != 0);
                webBoard[i][j] = new CellWebDTO(cell, solutionArray[i][j], i, j, isDraft);
                j++;
            }
            i++;
        }
        return new SudokuBoardWebDTO(webBoard);
    }

    public List<SudokuCellEntity> sudokuWebToSudokuCellEntity(SudokuBoardWebDTO boardWebDTO) {

        List<SudokuCellEntity> cells = new ArrayList<>();

        for (CellWebDTO[] row : boardWebDTO.getBoard()) {
            for (CellWebDTO cellWebDTO : row) {
                cells.add(mapWebCellToCellEntity(cellWebDTO, boardWebDTO.getUserId()));
            }
        }
        return cells;
    }

    public SudokuBoardWebDTO mapCellsEntitiesToWebBoardDto(List<SudokuCellEntity> cells) {

        CellWebDTO[][] webBoard = new CellWebDTO[9][9];

        for (SudokuCellEntity cell : cells) {
            webBoard[cell.getRowNumber()][cell.getColumnNumber()] = mapCellEntityToWebCell(cell);
        }

        return new SudokuBoardWebDTO(webBoard);
    }

    private SudokuCellEntity mapWebCellToCellEntity(CellWebDTO cellWebDTO, Long userId) {

        SudokuCellEntity sudokuCellEntity = new SudokuCellEntity();

        sudokuCellEntity.setUserId(userId);
        sudokuCellEntity.setValue(cellWebDTO.getValue());
        sudokuCellEntity.setSolution(cellWebDTO.getSolution());
        sudokuCellEntity.setRowNumber(cellWebDTO.getX());
        sudokuCellEntity.setColumnNumber(cellWebDTO.getY());
        sudokuCellEntity.setDraftNumber(cellWebDTO.getDraftNumber());

        return sudokuCellEntity;
    }

    private CellWebDTO mapCellEntityToWebCell(SudokuCellEntity cell) {

        CellWebDTO cellWebDTO = new CellWebDTO();

        cellWebDTO.setValue(cell.getValue());
        cellWebDTO.setSolution(cell.getSolution());
        cellWebDTO.setX(cell.getRowNumber());
        cellWebDTO.setY(cell.getColumnNumber());
        cellWebDTO.setDraftNumber(cell.isDraftNumber());

        return cellWebDTO;
    }


}
