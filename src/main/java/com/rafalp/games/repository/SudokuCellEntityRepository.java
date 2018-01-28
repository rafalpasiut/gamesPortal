package com.rafalp.games.repository;

import com.rafalp.games.domain.SudokuCellEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SudokuCellEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SudokuCellEntityRepository extends JpaRepository<SudokuCellEntity, Long> {

}
