package com.rafalp.games.web.rest;

import com.rafalp.games.GamesPortalApp;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.repository.RPSGamesRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.rafalp.games.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RPSGamesResource REST controller.
 *
 * @see RPSGamesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GamesPortalApp.class)
public class RPSGamesResourceIntTest {

    private static final String DEFAULT_PLAYER_1 = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYER_1_CHAMPION = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_1_CHAMPION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLAYER_1_COUNT = 1;
    private static final Integer UPDATED_PLAYER_1_COUNT = 2;

    private static final Boolean DEFAULT_PLAYER_1_IS_PLAYED = false;
    private static final Boolean UPDATED_PLAYER_1_IS_PLAYED = true;

    private static final String DEFAULT_PLAYER_2 = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYER_2_CHAMPION = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_2_CHAMPION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLAYER_2_COUNT = 1;
    private static final Integer UPDATED_PLAYER_2_COUNT = 2;

    private static final Boolean DEFAULT_PLAYER_2_IS_PLAYED = false;
    private static final Boolean UPDATED_PLAYER_2_IS_PLAYED = true;

    private static final Boolean DEFAULT_IS_AI = false;
    private static final Boolean UPDATED_IS_AI = true;

    private static final Instant DEFAULT_GAME_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GAME_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_ACTION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_ACTION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_GAME_FINISHED = false;
    private static final Boolean UPDATED_IS_GAME_FINISHED = true;

    private static final Boolean DEFAULT_IS_ROUND_FINISHED = false;
    private static final Boolean UPDATED_IS_ROUND_FINISHED = true;

    @Autowired
    private RPSGamesRepository rPSGamesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRPSGamesMockMvc;

    private RPSGames rPSGames;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RPSGamesResource rPSGamesResource = new RPSGamesResource(rPSGamesRepository);
        this.restRPSGamesMockMvc = MockMvcBuilders.standaloneSetup(rPSGamesResource)
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
    public static RPSGames createEntity(EntityManager em) {
        RPSGames rPSGames = new RPSGames()
            .player1(DEFAULT_PLAYER_1)
            .player1Champion(DEFAULT_PLAYER_1_CHAMPION)
            .player1Count(DEFAULT_PLAYER_1_COUNT)
            .player1IsPlayed(DEFAULT_PLAYER_1_IS_PLAYED)
            .player2(DEFAULT_PLAYER_2)
            .player2Champion(DEFAULT_PLAYER_2_CHAMPION)
            .player2Count(DEFAULT_PLAYER_2_COUNT)
            .player2IsPlayed(DEFAULT_PLAYER_2_IS_PLAYED)
            .isAI(DEFAULT_IS_AI)
            .gameStartTime(DEFAULT_GAME_START_TIME)
            .lastActionTime(DEFAULT_LAST_ACTION_TIME)
            .isGameFinished(DEFAULT_IS_GAME_FINISHED)
            .isRoundFinished(DEFAULT_IS_ROUND_FINISHED);
        return rPSGames;
    }

    @Before
    public void initTest() {
        rPSGames = createEntity(em);
    }

    @Test
    @Transactional
    public void createRPSGames() throws Exception {
        int databaseSizeBeforeCreate = rPSGamesRepository.findAll().size();

        // Create the RPSGames
        restRPSGamesMockMvc.perform(post("/api/rps-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rPSGames)))
            .andExpect(status().isCreated());

        // Validate the RPSGames in the database
        List<RPSGames> rPSGamesList = rPSGamesRepository.findAll();
        assertThat(rPSGamesList).hasSize(databaseSizeBeforeCreate + 1);
        RPSGames testRPSGames = rPSGamesList.get(rPSGamesList.size() - 1);
        assertThat(testRPSGames.getPlayer1()).isEqualTo(DEFAULT_PLAYER_1);
        assertThat(testRPSGames.getPlayer1Champion()).isEqualTo(DEFAULT_PLAYER_1_CHAMPION);
        assertThat(testRPSGames.getPlayer1Count()).isEqualTo(DEFAULT_PLAYER_1_COUNT);
        assertThat(testRPSGames.isPlayer1IsPlayed()).isEqualTo(DEFAULT_PLAYER_1_IS_PLAYED);
        assertThat(testRPSGames.getPlayer2()).isEqualTo(DEFAULT_PLAYER_2);
        assertThat(testRPSGames.getPlayer2Champion()).isEqualTo(DEFAULT_PLAYER_2_CHAMPION);
        assertThat(testRPSGames.getPlayer2Count()).isEqualTo(DEFAULT_PLAYER_2_COUNT);
        assertThat(testRPSGames.isPlayer2IsPlayed()).isEqualTo(DEFAULT_PLAYER_2_IS_PLAYED);
        assertThat(testRPSGames.isIsAI()).isEqualTo(DEFAULT_IS_AI);
        assertThat(testRPSGames.getGameStartTime()).isEqualTo(DEFAULT_GAME_START_TIME);
        assertThat(testRPSGames.getLastActionTime()).isEqualTo(DEFAULT_LAST_ACTION_TIME);
        assertThat(testRPSGames.isIsGameFinished()).isEqualTo(DEFAULT_IS_GAME_FINISHED);
        assertThat(testRPSGames.isIsRoundFinished()).isEqualTo(DEFAULT_IS_ROUND_FINISHED);
    }

    @Test
    @Transactional
    public void createRPSGamesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rPSGamesRepository.findAll().size();

        // Create the RPSGames with an existing ID
        rPSGames.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRPSGamesMockMvc.perform(post("/api/rps-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rPSGames)))
            .andExpect(status().isBadRequest());

        // Validate the RPSGames in the database
        List<RPSGames> rPSGamesList = rPSGamesRepository.findAll();
        assertThat(rPSGamesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRPSGames() throws Exception {
        // Initialize the database
        rPSGamesRepository.saveAndFlush(rPSGames);

        // Get all the rPSGamesList
        restRPSGamesMockMvc.perform(get("/api/rps-games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rPSGames.getId().intValue())))
            .andExpect(jsonPath("$.[*].player1").value(hasItem(DEFAULT_PLAYER_1.toString())))
            .andExpect(jsonPath("$.[*].player1Champion").value(hasItem(DEFAULT_PLAYER_1_CHAMPION.toString())))
            .andExpect(jsonPath("$.[*].player1Count").value(hasItem(DEFAULT_PLAYER_1_COUNT)))
            .andExpect(jsonPath("$.[*].player1IsPlayed").value(hasItem(DEFAULT_PLAYER_1_IS_PLAYED.booleanValue())))
            .andExpect(jsonPath("$.[*].player2").value(hasItem(DEFAULT_PLAYER_2.toString())))
            .andExpect(jsonPath("$.[*].player2Champion").value(hasItem(DEFAULT_PLAYER_2_CHAMPION.toString())))
            .andExpect(jsonPath("$.[*].player2Count").value(hasItem(DEFAULT_PLAYER_2_COUNT)))
            .andExpect(jsonPath("$.[*].player2IsPlayed").value(hasItem(DEFAULT_PLAYER_2_IS_PLAYED.booleanValue())))
            .andExpect(jsonPath("$.[*].isAI").value(hasItem(DEFAULT_IS_AI.booleanValue())))
            .andExpect(jsonPath("$.[*].gameStartTime").value(hasItem(DEFAULT_GAME_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastActionTime").value(hasItem(DEFAULT_LAST_ACTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].isGameFinished").value(hasItem(DEFAULT_IS_GAME_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRoundFinished").value(hasItem(DEFAULT_IS_ROUND_FINISHED.booleanValue())));
    }

    @Test
    @Transactional
    public void getRPSGames() throws Exception {
        // Initialize the database
        rPSGamesRepository.saveAndFlush(rPSGames);

        // Get the rPSGames
        restRPSGamesMockMvc.perform(get("/api/rps-games/{id}", rPSGames.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rPSGames.getId().intValue()))
            .andExpect(jsonPath("$.player1").value(DEFAULT_PLAYER_1.toString()))
            .andExpect(jsonPath("$.player1Champion").value(DEFAULT_PLAYER_1_CHAMPION.toString()))
            .andExpect(jsonPath("$.player1Count").value(DEFAULT_PLAYER_1_COUNT))
            .andExpect(jsonPath("$.player1IsPlayed").value(DEFAULT_PLAYER_1_IS_PLAYED.booleanValue()))
            .andExpect(jsonPath("$.player2").value(DEFAULT_PLAYER_2.toString()))
            .andExpect(jsonPath("$.player2Champion").value(DEFAULT_PLAYER_2_CHAMPION.toString()))
            .andExpect(jsonPath("$.player2Count").value(DEFAULT_PLAYER_2_COUNT))
            .andExpect(jsonPath("$.player2IsPlayed").value(DEFAULT_PLAYER_2_IS_PLAYED.booleanValue()))
            .andExpect(jsonPath("$.isAI").value(DEFAULT_IS_AI.booleanValue()))
            .andExpect(jsonPath("$.gameStartTime").value(DEFAULT_GAME_START_TIME.toString()))
            .andExpect(jsonPath("$.lastActionTime").value(DEFAULT_LAST_ACTION_TIME.toString()))
            .andExpect(jsonPath("$.isGameFinished").value(DEFAULT_IS_GAME_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.isRoundFinished").value(DEFAULT_IS_ROUND_FINISHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRPSGames() throws Exception {
        // Get the rPSGames
        restRPSGamesMockMvc.perform(get("/api/rps-games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRPSGames() throws Exception {
        // Initialize the database
        rPSGamesRepository.saveAndFlush(rPSGames);
        int databaseSizeBeforeUpdate = rPSGamesRepository.findAll().size();

        // Update the rPSGames
        RPSGames updatedRPSGames = rPSGamesRepository.findOne(rPSGames.getId());
        // Disconnect from session so that the updates on updatedRPSGames are not directly saved in db
        em.detach(updatedRPSGames);
        updatedRPSGames
            .player1(UPDATED_PLAYER_1)
            .player1Champion(UPDATED_PLAYER_1_CHAMPION)
            .player1Count(UPDATED_PLAYER_1_COUNT)
            .player1IsPlayed(UPDATED_PLAYER_1_IS_PLAYED)
            .player2(UPDATED_PLAYER_2)
            .player2Champion(UPDATED_PLAYER_2_CHAMPION)
            .player2Count(UPDATED_PLAYER_2_COUNT)
            .player2IsPlayed(UPDATED_PLAYER_2_IS_PLAYED)
            .isAI(UPDATED_IS_AI)
            .gameStartTime(UPDATED_GAME_START_TIME)
            .lastActionTime(UPDATED_LAST_ACTION_TIME)
            .isGameFinished(UPDATED_IS_GAME_FINISHED)
            .isRoundFinished(UPDATED_IS_ROUND_FINISHED);

        restRPSGamesMockMvc.perform(put("/api/rps-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRPSGames)))
            .andExpect(status().isOk());

        // Validate the RPSGames in the database
        List<RPSGames> rPSGamesList = rPSGamesRepository.findAll();
        assertThat(rPSGamesList).hasSize(databaseSizeBeforeUpdate);
        RPSGames testRPSGames = rPSGamesList.get(rPSGamesList.size() - 1);
        assertThat(testRPSGames.getPlayer1()).isEqualTo(UPDATED_PLAYER_1);
        assertThat(testRPSGames.getPlayer1Champion()).isEqualTo(UPDATED_PLAYER_1_CHAMPION);
        assertThat(testRPSGames.getPlayer1Count()).isEqualTo(UPDATED_PLAYER_1_COUNT);
        assertThat(testRPSGames.isPlayer1IsPlayed()).isEqualTo(UPDATED_PLAYER_1_IS_PLAYED);
        assertThat(testRPSGames.getPlayer2()).isEqualTo(UPDATED_PLAYER_2);
        assertThat(testRPSGames.getPlayer2Champion()).isEqualTo(UPDATED_PLAYER_2_CHAMPION);
        assertThat(testRPSGames.getPlayer2Count()).isEqualTo(UPDATED_PLAYER_2_COUNT);
        assertThat(testRPSGames.isPlayer2IsPlayed()).isEqualTo(UPDATED_PLAYER_2_IS_PLAYED);
        assertThat(testRPSGames.isIsAI()).isEqualTo(UPDATED_IS_AI);
        assertThat(testRPSGames.getGameStartTime()).isEqualTo(UPDATED_GAME_START_TIME);
        assertThat(testRPSGames.getLastActionTime()).isEqualTo(UPDATED_LAST_ACTION_TIME);
        assertThat(testRPSGames.isIsGameFinished()).isEqualTo(UPDATED_IS_GAME_FINISHED);
        assertThat(testRPSGames.isIsRoundFinished()).isEqualTo(UPDATED_IS_ROUND_FINISHED);
    }

    @Test
    @Transactional
    public void updateNonExistingRPSGames() throws Exception {
        int databaseSizeBeforeUpdate = rPSGamesRepository.findAll().size();

        // Create the RPSGames

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRPSGamesMockMvc.perform(put("/api/rps-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rPSGames)))
            .andExpect(status().isCreated());

        // Validate the RPSGames in the database
        List<RPSGames> rPSGamesList = rPSGamesRepository.findAll();
        assertThat(rPSGamesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRPSGames() throws Exception {
        // Initialize the database
        rPSGamesRepository.saveAndFlush(rPSGames);
        int databaseSizeBeforeDelete = rPSGamesRepository.findAll().size();

        // Get the rPSGames
        restRPSGamesMockMvc.perform(delete("/api/rps-games/{id}", rPSGames.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RPSGames> rPSGamesList = rPSGamesRepository.findAll();
        assertThat(rPSGamesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RPSGames.class);
        RPSGames rPSGames1 = new RPSGames();
        rPSGames1.setId(1L);
        RPSGames rPSGames2 = new RPSGames();
        rPSGames2.setId(rPSGames1.getId());
        assertThat(rPSGames1).isEqualTo(rPSGames2);
        rPSGames2.setId(2L);
        assertThat(rPSGames1).isNotEqualTo(rPSGames2);
        rPSGames1.setId(null);
        assertThat(rPSGames1).isNotEqualTo(rPSGames2);
    }
}
