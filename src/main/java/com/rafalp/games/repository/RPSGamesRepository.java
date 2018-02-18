package com.rafalp.games.repository;

import com.rafalp.games.domain.RPSGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Spring Data JPA repository for the RPSGames entity.
 */
@SuppressWarnings("unused")
@Repository
@Transactional
public interface RPSGamesRepository extends JpaRepository<RPSGames, Long> {

    RPSGames findByPlayer1(String player);

    RPSGames findByPlayer2(String player);

    RPSGames findByPlayer1AndPlayer2(String player1, String player2);

    RPSGames findByPlayer1OrPlayer2(String player, String player2);

    @Modifying
    void deleteByPlayer1AndPlayer2AndPlayer1IsPlayedAndPlayer2IsPlayed(String player1, String player2, Boolean player1IsPlayed, Boolean player2IsPlayed);

    @Modifying
    @Query(value = "UPDATE RPSGames set player1Champion = :CHAMPION, player1IsPlayed = TRUE WHERE player1 = :PLAYER AND player1IsPlayed = false")
    void updatePlayer1Game(@Param("PLAYER") String player, @Param("CHAMPION") String champion);

    @Modifying
    @Query(value = "UPDATE RPSGames set player2Champion = :CHAMPION, player2IsPlayed = TRUE WHERE player2 = :PLAYER AND player2IsPlayed = false")
    void updatePlayer2Game(@Param("PLAYER") String player, @Param("CHAMPION") String champion);

    @Query(value = "From RPSGames where player1IsPlayed = true AND player2IsPlayed = true AND isRoundFinished = false AND (player1 = :PLAYER OR player2 = :PLAYER)")
    RPSGames isFightReady(@Param("PLAYER") String player);

    @Query(value = "FROM RPSGames where player2 = null")
    RPSGames findWaitingPlayer();

    @Modifying
    @Query(value = "UPDATE RPSGames set player2 = :PLAYER2, player2Champion = :CHAMPION, player2Count = 0, player2IsPlayed = true where player1 = :PLAYER1")
    void addSecondPlayer(@Param("PLAYER1") String player1, @Param("PLAYER2") String player2, @Param("CHAMPION") String champion);
}
