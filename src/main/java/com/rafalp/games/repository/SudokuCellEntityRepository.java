package com.rafalp.games.repository;

import com.rafalp.games.domain.SudokuCellEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the SudokuCellEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SudokuCellEntityRepository extends JpaRepository<SudokuCellEntity, Long> {

    @Override
    SudokuCellEntity save(SudokuCellEntity entity);

    SudokuCellEntity findByUserIdAndColumnNumberAndRowNumber(Long userId, Integer column, Integer row);

    List<SudokuCellEntity> findAllByUserId(Long userId);

}
