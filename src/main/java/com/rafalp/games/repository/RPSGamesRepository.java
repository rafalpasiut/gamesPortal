package com.rafalp.games.repository;

import com.rafalp.games.domain.RPSGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the RPSGames entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RPSGamesRepository extends JpaRepository<RPSGames, Long> {

    RPSGames findByPlayer1(String player);

    RPSGames findByPlayer2(String player);

    RPSGames findByPlayer1AndPlayer2(String player1, String player2);

    RPSGames deleteByPlayer1AndPlayer2AndPlayer1IsPlayedAndPlayer2IsPlayed(String player1, String player2, Boolean player1IsPlayed, Boolean player2IsPlayed);

    @Query(value = "UPDATE RPSGames set player1Champion = :CHAMPION, player1IsPlayed = TRUE WHERE player1 = :PLAYER AND player1IsPlayed = false")
    RPSGames updatePlayer1Game(@Param("PLAYER") String player, @Param("CHAMPION") String champion);

    @Query(value = "UPDATE RPSGames set player2Champion = :CHAMPION, player2IsPlayed = TRUE WHERE player2 = :PLAYER AND player2IsPlayed = false")
    RPSGames updatePlayer2Game(@Param("PLAYER") String player, @Param("CHAMPION") String champion);

    @Query(value = "From RPSGames where player1IsPlayed = true AND player2IsPlayed = true AND (player1 = :player OR player2 = :player)")
    List<RPSGames> isRoundFinished(@Param("PLAYER") String player);

    @Query(value = "UPDATE RPSGames set player1Count = :COUNT1, player2Count = :COUNT2 WHERE player1 = :PLAYER OR player2 = :PLAYER and ")
    RPSGames updateFightResult(@Param("PLAYER") String player, @Param("COUNT1") Integer count1, @Param("COUNT2") Integer count2);

    @Query(value = "FROM RPSGames where player2 = null")
    RPSGames findWaitingPlayer();

    @Query(value = "UPDATE RPSGames set isGameFinished = :FINISHED where player1 = :PLAYER1 and player2 = :PLAYER2")
    RPSGames setGameFinish(@Param("FINISHED") Boolean finished, @Param("PLAYER1") Boolean player1, @Param("PLAYER2") Boolean player2);

    @Query(value = "UPDATE RPSGames set player2 = :PLAYER2, player2Champion = :CHAMPION, player2IsPlayed = true where player1 = :PLAYER1")
    RPSGames addSecondPlayer((@Param("PLAYER1") String player1, @Param("PLAYER2") String player2, @Param("CHAMPION") String champion);
}
