package com.rafalp.games.repository;

import com.rafalp.games.domain.SudokuCellEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SudokuRepositoryController {

    @Autowired
    private SudokuCellEntityRepository sudokuCellEntityDao;

    public List<SudokuCellEntity> readSudokuCellEntities(Long userId){
        List<SudokuCellEntity> cells;
        cells = sudokuCellEntityDao.findAllByUserId(userId);

        if(cells == null){
            cells = new ArrayList<>();
        }
        return cells;
    }

    public void saveSudoku(List<SudokuCellEntity> cells) {
        Long currentId;
        for (SudokuCellEntity cell : cells) {
            currentId = getIdIfExists(cell.getUserId(), cell.getColumnNumber(), cell.getRowNumber());
            if (currentId != -1) {
                cell.setId(currentId);
            }
            sudokuCellEntityDao.save(cell);
        }
    }

    private Long getIdIfExists(Long userId, Integer column, Integer row) {

        SudokuCellEntity entity = sudokuCellEntityDao.findByUserIdAndColumnNumberAndRowNumber(userId, column, row);
        if(entity!=null) {
            return entity.getId();
        } else {
            return (long)-1;
        }
    }
}
