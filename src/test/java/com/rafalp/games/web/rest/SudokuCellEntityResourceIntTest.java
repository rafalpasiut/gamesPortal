package com.rafalp.games.web.rest;

import com.rafalp.games.GamesPortalApp;

import com.rafalp.games.domain.SudokuCellEntity;
import com.rafalp.games.repository.SudokuCellEntityRepository;
import com.rafalp.games.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.rafalp.games.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SudokuCellEntityResource REST controller.
 *
 * @see SudokuCellEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GamesPortalApp.class)
public class SudokuCellEntityResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final Integer DEFAULT_SOLUTION = 1;
    private static final Integer UPDATED_SOLUTION = 2;

    private static final Integer DEFAULT_ROW_NUMBER = 1;
    private static final Integer UPDATED_ROW_NUMBER = 2;

    private static final Integer DEFAULT_COLUMN_NUMBER = 1;
    private static final Integer UPDATED_COLUMN_NUMBER = 2;

    private static final Boolean DEFAULT_DRAFT_NUMBER = false;
    private static final Boolean UPDATED_DRAFT_NUMBER = true;

    @Autowired
    private SudokuCellEntityRepository sudokuCellEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSudokuCellEntityMockMvc;

    private SudokuCellEntity sudokuCellEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SudokuCellEntityResource sudokuCellEntityResource = new SudokuCellEntityResource(sudokuCellEntityRepository);
        this.restSudokuCellEntityMockMvc = MockMvcBuilders.standaloneSetup(sudokuCellEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SudokuCellEntity createEntity(EntityManager em) {
        SudokuCellEntity sudokuCellEntity = new SudokuCellEntity()
            .userId(DEFAULT_USER_ID)
            .value(DEFAULT_VALUE)
            .solution(DEFAULT_SOLUTION)
            .rowNumber(DEFAULT_ROW_NUMBER)
            .columnNumber(DEFAULT_COLUMN_NUMBER)
            .draftNumber(DEFAULT_DRAFT_NUMBER);
        return sudokuCellEntity;
    }

    @Before
    public void initTest() {
        sudokuCellEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSudokuCellEntity() throws Exception {
        int databaseSizeBeforeCreate = sudokuCellEntityRepository.findAll().size();

        // Create the SudokuCellEntity
        restSudokuCellEntityMockMvc.perform(post("/api/sudoku-cell-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sudokuCellEntity)))
            .andExpect(status().isCreated());

        // Validate the SudokuCellEntity in the database
        List<SudokuCellEntity> sudokuCellEntityList = sudokuCellEntityRepository.findAll();
        assertThat(sudokuCellEntityList).hasSize(databaseSizeBeforeCreate + 1);
        SudokuCellEntity testSudokuCellEntity = sudokuCellEntityList.get(sudokuCellEntityList.size() - 1);
        assertThat(testSudokuCellEntity.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSudokuCellEntity.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSudokuCellEntity.getSolution()).isEqualTo(DEFAULT_SOLUTION);
        assertThat(testSudokuCellEntity.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testSudokuCellEntity.getColumnNumber()).isEqualTo(DEFAULT_COLUMN_NUMBER);
        assertThat(testSudokuCellEntity.isDraftNumber()).isEqualTo(DEFAULT_DRAFT_NUMBER);
    }

    @Test
    @Transactional
    public void createSudokuCellEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sudokuCellEntityRepository.findAll().size();

        // Create the SudokuCellEntity with an existing ID
        sudokuCellEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSudokuCellEntityMockMvc.perform(post("/api/sudoku-cell-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sudokuCellEntity)))
            .andExpect(status().isBadRequest());

        // Validate the SudokuCellEntity in the database
        List<SudokuCellEntity> sudokuCellEntityList = sudokuCellEntityRepository.findAll();
        assertThat(sudokuCellEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSudokuCellEntities() throws Exception {
        // Initialize the database
        sudokuCellEntityRepository.saveAndFlush(sudokuCellEntity);

        // Get all the sudokuCellEntityList
        restSudokuCellEntityMockMvc.perform(get("/api/sudoku-cell-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sudokuCellEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].solution").value(hasItem(DEFAULT_SOLUTION)))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER)))
            .andExpect(jsonPath("$.[*].columnNumber").value(hasItem(DEFAULT_COLUMN_NUMBER)))
            .andExpect(jsonPath("$.[*].draftNumber").value(hasItem(DEFAULT_DRAFT_NUMBER.booleanValue())));
    }

    @Test
    @Transactional
    public void getSudokuCellEntity() throws Exception {
        // Initialize the database
        sudokuCellEntityRepository.saveAndFlush(sudokuCellEntity);

        // Get the sudokuCellEntity
        restSudokuCellEntityMockMvc.perform(get("/api/sudoku-cell-entities/{id}", sudokuCellEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sudokuCellEntity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.solution").value(DEFAULT_SOLUTION))
            .andExpect(jsonPath("$.rowNumber").value(DEFAULT_ROW_NUMBER))
            .andExpect(jsonPath("$.columnNumber").value(DEFAULT_COLUMN_NUMBER))
            .andExpect(jsonPath("$.draftNumber").value(DEFAULT_DRAFT_NUMBER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSudokuCellEntity() throws Exception {
        // Get the sudokuCellEntity
        restSudokuCellEntityMockMvc.perform(get("/api/sudoku-cell-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSudokuCellEntity() throws Exception {
        // Initialize the database
        sudokuCellEntityRepository.saveAndFlush(sudokuCellEntity);
        int databaseSizeBeforeUpdate = sudokuCellEntityRepository.findAll().size();

        // Update the sudokuCellEntity
        SudokuCellEntity updatedSudokuCellEntity = sudokuCellEntityRepository.findOne(sudokuCellEntity.getId());
        // Disconnect from session so that the updates on updatedSudokuCellEntity are not directly saved in db
        em.detach(updatedSudokuCellEntity);
        updatedSudokuCellEntity
            .userId(UPDATED_USER_ID)
            .value(UPDATED_VALUE)
            .solution(UPDATED_SOLUTION)
            .rowNumber(UPDATED_ROW_NUMBER)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .draftNumber(UPDATED_DRAFT_NUMBER);

        restSudokuCellEntityMockMvc.perform(put("/api/sudoku-cell-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSudokuCellEntity)))
            .andExpect(status().isOk());

        // Validate the SudokuCellEntity in the database
        List<SudokuCellEntity> sudokuCellEntityList = sudokuCellEntityRepository.findAll();
        assertThat(sudokuCellEntityList).hasSize(databaseSizeBeforeUpdate);
        SudokuCellEntity testSudokuCellEntity = sudokuCellEntityList.get(sudokuCellEntityList.size() - 1);
        assertThat(testSudokuCellEntity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSudokuCellEntity.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSudokuCellEntity.getSolution()).isEqualTo(UPDATED_SOLUTION);
        assertThat(testSudokuCellEntity.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testSudokuCellEntity.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testSudokuCellEntity.isDraftNumber()).isEqualTo(UPDATED_DRAFT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingSudokuCellEntity() throws Exception {
        int databaseSizeBeforeUpdate = sudokuCellEntityRepository.findAll().size();

        // Create the SudokuCellEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSudokuCellEntityMockMvc.perform(put("/api/sudoku-cell-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sudokuCellEntity)))
            .andExpect(status().isCreated());

        // Validate the SudokuCellEntity in the database
        List<SudokuCellEntity> sudokuCellEntityList = sudokuCellEntityRepository.findAll();
        assertThat(sudokuCellEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSudokuCellEntity() throws Exception {
        // Initialize the database
        sudokuCellEntityRepository.saveAndFlush(sudokuCellEntity);
        int databaseSizeBeforeDelete = sudokuCellEntityRepository.findAll().size();

        // Get the sudokuCellEntity
        restSudokuCellEntityMockMvc.perform(delete("/api/sudoku-cell-entities/{id}", sudokuCellEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SudokuCellEntity> sudokuCellEntityList = sudokuCellEntityRepository.findAll();
        assertThat(sudokuCellEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SudokuCellEntity.class);
        SudokuCellEntity sudokuCellEntity1 = new SudokuCellEntity();
        sudokuCellEntity1.setId(1L);
        SudokuCellEntity sudokuCellEntity2 = new SudokuCellEntity();
        sudokuCellEntity2.setId(sudokuCellEntity1.getId());
        assertThat(sudokuCellEntity1).isEqualTo(sudokuCellEntity2);
        sudokuCellEntity2.setId(2L);
        assertThat(sudokuCellEntity1).isNotEqualTo(sudokuCellEntity2);
        sudokuCellEntity1.setId(null);
        assertThat(sudokuCellEntity1).isNotEqualTo(sudokuCellEntity2);
    }
}
