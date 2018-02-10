package com.rafalp.games.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rafalp.games.domain.SudokuCellEntity;

import com.rafalp.games.games.sudoku.board.SudokuBoardWebDTO;
import com.rafalp.games.games.sudoku.board.SudokuMapper;
import com.rafalp.games.games.sudoku.generator.SudokuGenerator;
import com.rafalp.games.repository.SudokuCellEntityRepository;
import com.rafalp.games.repository.SudokuRepositoryController;
import com.rafalp.games.web.rest.errors.BadRequestAlertException;
import com.rafalp.games.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SudokuCellEntity.
 */
@RestController
@RequestMapping("/api")
public class SudokuCellEntityResource {

    private final Logger log = LoggerFactory.getLogger(SudokuCellEntityResource.class);

    private static final String ENTITY_NAME = "sudokuCellEntity";

    private final SudokuCellEntityRepository sudokuCellEntityRepository;
    private SudokuGenerator sudokuGenerator;
    private SudokuMapper sudokuMapper;
    private SudokuRepositoryController repositoryController;

    @Autowired
    public SudokuCellEntityResource(SudokuCellEntityRepository sudokuCellEntityRepository, SudokuRepositoryController repositoryController, SudokuGenerator sudokuGenerator) {
        this.sudokuCellEntityRepository = sudokuCellEntityRepository;
        this.repositoryController = repositoryController;
        this.sudokuGenerator = sudokuGenerator;
        this.sudokuMapper = new SudokuMapper();
    }

    /**
     * POST  /sudoku-cell-entities : Create a new sudokuCellEntity.
     *
     * @param sudokuCellEntity the sudokuCellEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sudokuCellEntity, or with status 400 (Bad Request) if the sudokuCellEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sudoku-cell-entities")
    @Timed
    public ResponseEntity<SudokuCellEntity> createSudokuCellEntity(@RequestBody SudokuCellEntity sudokuCellEntity) throws URISyntaxException {
        log.debug("REST request to save SudokuCellEntity : {}", sudokuCellEntity);
        if (sudokuCellEntity.getId() != null) {
            throw new BadRequestAlertException("A new sudokuCellEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SudokuCellEntity result = sudokuCellEntityRepository.save(sudokuCellEntity);
        return ResponseEntity.created(new URI("/api/sudoku-cell-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sudoku-cell-entities : Updates an existing sudokuCellEntity.
     *
     * @param sudokuCellEntity the sudokuCellEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sudokuCellEntity,
     * or with status 400 (Bad Request) if the sudokuCellEntity is not valid,
     * or with status 500 (Internal Server Error) if the sudokuCellEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sudoku-cell-entities")
    @Timed
    public ResponseEntity<SudokuCellEntity> updateSudokuCellEntity(@RequestBody SudokuCellEntity sudokuCellEntity) throws URISyntaxException {
        log.debug("REST request to update SudokuCellEntity : {}", sudokuCellEntity);
        if (sudokuCellEntity.getId() == null) {
            return createSudokuCellEntity(sudokuCellEntity);
        }
        SudokuCellEntity result = sudokuCellEntityRepository.save(sudokuCellEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sudokuCellEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sudoku-cell-entities : get all the sudokuCellEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sudokuCellEntities in body
     */
    @GetMapping("/sudoku-cell-entities")
    @Timed
    public List<SudokuCellEntity> getAllSudokuCellEntities() {
        log.debug("REST request to get all SudokuCellEntities");
        return sudokuCellEntityRepository.findAll();
        }

    /**
     * GET  /sudoku-cell-entities/:id : get the "id" sudokuCellEntity.
     *
     * @param id the id of the sudokuCellEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sudokuCellEntity, or with status 404 (Not Found)
     */
    @GetMapping("/sudoku-cell-entities/{id}")
    @Timed
    public ResponseEntity<SudokuCellEntity> getSudokuCellEntity(@PathVariable Long id) {
        log.debug("REST request to get SudokuCellEntity : {}", id);
        SudokuCellEntity sudokuCellEntity = sudokuCellEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sudokuCellEntity));
    }

    /**
     * DELETE  /sudoku-cell-entities/:id : delete the "id" sudokuCellEntity.
     *
     * @param id the id of the sudokuCellEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sudoku-cell-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteSudokuCellEntity(@PathVariable Long id) {
        log.debug("REST request to delete SudokuCellEntity : {}", id);
        sudokuCellEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(value = "/sudokuGetSaved")
    public SudokuBoardWebDTO initializeSudokuGame(@RequestParam Long userId) {
        List<SudokuCellEntity> cells = repositoryController.readSudokuCellEntities(userId);
        if(cells.isEmpty()){
            sudokuGenerator.nextBoard(50);
            return sudokuGenerator.getSudokuDTO();
        }else{
            return sudokuMapper.mapCellsEntitiesToWebBoardDto(cells);
        }
    }

    @GetMapping(value = "/sudokuNew")
    public SudokuBoardWebDTO generateNewGame(@RequestParam Integer level) {
        sudokuGenerator.nextBoard(level);
        return sudokuGenerator.getSudokuDTO();
    }

    @PutMapping(value = "/sudokuSaveSudoku", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SudokuBoardWebDTO saveSudoku(@RequestBody SudokuBoardWebDTO sudoku) {
        repositoryController.saveSudoku(sudokuMapper.sudokuWebToSudokuCellEntity(sudoku));
        return sudoku;
    }
}
