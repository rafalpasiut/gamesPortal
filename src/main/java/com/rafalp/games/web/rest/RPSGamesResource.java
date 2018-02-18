package com.rafalp.games.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rafalp.games.domain.RPSGames;

import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.web.rest.errors.BadRequestAlertException;
import com.rafalp.games.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RPSGames.
 */
@RestController
@RequestMapping("/api")
public class RPSGamesResource {

    private final Logger log = LoggerFactory.getLogger(RPSGamesResource.class);

    private static final String ENTITY_NAME = "rPSGames";

    private final RPSGamesRepository rPSGamesRepository;

    public RPSGamesResource(RPSGamesRepository rPSGamesRepository) {
        this.rPSGamesRepository = rPSGamesRepository;
    }

    /**
     * POST  /rps-games : Create a new rPSGames.
     *
     * @param rPSGames the rPSGames to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rPSGames, or with status 400 (Bad Request) if the rPSGames has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rps-games")
    @Timed
    public ResponseEntity<RPSGames> createRPSGames(@RequestBody RPSGames rPSGames) throws URISyntaxException {
        log.debug("REST request to save RPSGames : {}", rPSGames);
        if (rPSGames.getId() != null) {
            throw new BadRequestAlertException("A new rPSGames cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RPSGames result = rPSGamesRepository.save(rPSGames);
        return ResponseEntity.created(new URI("/api/rps-games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rps-games : Updates an existing rPSGames.
     *
     * @param rPSGames the rPSGames to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rPSGames,
     * or with status 400 (Bad Request) if the rPSGames is not valid,
     * or with status 500 (Internal Server Error) if the rPSGames couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rps-games")
    @Timed
    public ResponseEntity<RPSGames> updateRPSGames(@RequestBody RPSGames rPSGames) throws URISyntaxException {
        log.debug("REST request to update RPSGames : {}", rPSGames);
        if (rPSGames.getId() == null) {
            return createRPSGames(rPSGames);
        }
        RPSGames result = rPSGamesRepository.save(rPSGames);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rPSGames.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rps-games : get all the rPSGames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rPSGames in body
     */
    @GetMapping("/rps-games")
    @Timed
    public List<RPSGames> getAllRPSGames() {
        log.debug("REST request to get all RPSGames");
        return rPSGamesRepository.findAll();
        }

    /**
     * GET  /rps-games/:id : get the "id" rPSGames.
     *
     * @param id the id of the rPSGames to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rPSGames, or with status 404 (Not Found)
     */
    @GetMapping("/rps-games/{id}")
    @Timed
    public ResponseEntity<RPSGames> getRPSGames(@PathVariable Long id) {
        log.debug("REST request to get RPSGames : {}", id);
        RPSGames rPSGames = rPSGamesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rPSGames));
    }

    /**
     * DELETE  /rps-games/:id : delete the "id" rPSGames.
     *
     * @param id the id of the rPSGames to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rps-games/{id}")
    @Timed
    public ResponseEntity<Void> deleteRPSGames(@PathVariable Long id) {
        log.debug("REST request to delete RPSGames : {}", id);
        rPSGamesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
