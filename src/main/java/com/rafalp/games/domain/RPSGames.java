package com.rafalp.games.domain;

import com.rafalp.games.games.rps.FightResult;
import com.rafalp.games.games.rps.RPSFightResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A RPSGames.
 */
@Entity
@Table(name = "rps_games")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RPSGames implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "player_1")
    private String player1;

    @Column(name = "player_1_champion")
    private String player1Champion;

    @Column(name = "player_1_count")
    private Integer player1Count;

    @Column(name = "player_1_is_played")
    private Boolean player1IsPlayed;

    @Column(name = "player_2")
    private String player2;

    @Column(name = "player_2_champion")
    private String player2Champion;

    @Column(name = "player_2_count")
    private Integer player2Count;

    @Column(name = "player_2_is_played")
    private Boolean player2IsPlayed;

    @Column(name = "is_ai")
    private Boolean isAI;

    @Column(name = "game_start_time")
    private Instant gameStartTime;

    @Column(name = "last_action_time")
    private Instant lastActionTime;

    @Column(name = "is_game_finished")
    private Boolean isGameFinished;

    @Column(name = "is_round_finished")
    private Boolean isRoundFinished;

    @Column(name = "message")
    private String message;

    @Column(name = "player_1_win")
    private Boolean player1Win;

    @Column(name = "player_2_win")
    private Boolean player2Win;

    public RPSGames() {
    }

    public RPSGames(String player1, String player1Champion, Integer player1Count, Boolean player1IsPlayed, String player2, String player2Champion, Integer player2Count, Boolean player2IsPlayed, Boolean isAI, Instant gameStartTime, Instant lastActionTime, Boolean isGameFinished, Boolean isRoundFinished) {
        this.player1 = player1;
        this.player1Champion = player1Champion;
        this.player1Count = player1Count;
        this.player1IsPlayed = player1IsPlayed;
        this.player2 = player2;
        this.player2Champion = player2Champion;
        this.player2Count = player2Count;
        this.player2IsPlayed = player2IsPlayed;
        this.isAI = isAI;
        this.gameStartTime = gameStartTime;
        this.lastActionTime = lastActionTime;
        this.isGameFinished = isGameFinished;
        this.isRoundFinished = isRoundFinished;
    }

    public RPSGames(String player1, String player1Champion, Boolean player1IsPlayed, Boolean isAI) {
        this.player1 = player1;
        this.player1Champion = player1Champion;
        this.player1IsPlayed = player1IsPlayed;
        this.isAI = isAI;
        player1Count = 0;
        player2Count = 0;
        player2IsPlayed = false;
        isGameFinished = false;
        isRoundFinished = false;
        message = "";
        player1Win = false;
        player2Win = false;
    }

    public RPSFightResult getFightResultForPlayer(String player) {
        if (player.equals(player1)) {
            FightResult fightResult;
            if (player1Win == true) {
                fightResult = FightResult.WIN;
            } else if (player1Win == false && player2Win == false) {
                fightResult = FightResult.TIE;
            } else {
                fightResult = FightResult.LOSE;
            }
            return new RPSFightResult(player1Champion, player2Champion, fightResult.toString(), message, isRoundFinished, isGameFinished, player1Count, player2Count);
        } else {
            FightResult fightResult;
            if (player2Win == true) {
                fightResult = FightResult.WIN;
            } else if (player1Win == false && player2Win == false) {
                fightResult = FightResult.TIE;
            } else {
                fightResult = FightResult.LOSE;
            }
            return new RPSFightResult(player2Champion, player1Champion, fightResult.toString(), message, isRoundFinished, isGameFinished, player2Count, player1Count);
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public RPSGames player1(String player1) {
        this.player1 = player1;
        return this;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer1Champion() {
        return player1Champion;
    }

    public RPSGames player1Champion(String player1Champion) {
        this.player1Champion = player1Champion;
        return this;
    }

    public void setPlayer1Champion(String player1Champion) {
        this.player1Champion = player1Champion;
    }

    public Integer getPlayer1Count() {
        return player1Count;
    }

    public RPSGames player1Count(Integer player1Count) {
        this.player1Count = player1Count;
        return this;
    }

    public void setPlayer1Count(Integer player1Count) {
        this.player1Count = player1Count;
    }

    public Boolean isPlayer1IsPlayed() {
        return player1IsPlayed;
    }

    public RPSGames player1IsPlayed(Boolean player1IsPlayed) {
        this.player1IsPlayed = player1IsPlayed;
        return this;
    }

    public void setPlayer1IsPlayed(Boolean player1IsPlayed) {
        this.player1IsPlayed = player1IsPlayed;
    }

    public String getPlayer2() {
        return player2;
    }

    public RPSGames player2(String player2) {
        this.player2 = player2;
        return this;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer2Champion() {
        return player2Champion;
    }

    public RPSGames player2Champion(String player2Champion) {
        this.player2Champion = player2Champion;
        return this;
    }

    public void setPlayer2Champion(String player2Champion) {
        this.player2Champion = player2Champion;
    }

    public Integer getPlayer2Count() {
        return player2Count;
    }

    public RPSGames player2Count(Integer player2Count) {
        this.player2Count = player2Count;
        return this;
    }

    public void setPlayer2Count(Integer player2Count) {
        this.player2Count = player2Count;
    }

    public Boolean isPlayer2IsPlayed() {
        return player2IsPlayed;
    }

    public RPSGames player2IsPlayed(Boolean player2IsPlayed) {
        this.player2IsPlayed = player2IsPlayed;
        return this;
    }

    public void setPlayer2IsPlayed(Boolean player2IsPlayed) {
        this.player2IsPlayed = player2IsPlayed;
    }

    public Boolean isIsAI() {
        return isAI;
    }

    public RPSGames isAI(Boolean isAI) {
        this.isAI = isAI;
        return this;
    }

    public void setIsAI(Boolean isAI) {
        this.isAI = isAI;
    }

    public Instant getGameStartTime() {
        return gameStartTime;
    }

    public RPSGames gameStartTime(Instant gameStartTime) {
        this.gameStartTime = gameStartTime;
        return this;
    }

    public void setGameStartTime(Instant gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public Instant getLastActionTime() {
        return lastActionTime;
    }

    public RPSGames lastActionTime(Instant lastActionTime) {
        this.lastActionTime = lastActionTime;
        return this;
    }

    public void setLastActionTime(Instant lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public Boolean isIsGameFinished() {
        return isGameFinished;
    }

    public RPSGames isGameFinished(Boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
        return this;
    }

    public void setIsGameFinished(Boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public Boolean isIsRoundFinished() {
        return isRoundFinished;
    }

    public RPSGames isRoundFinished(Boolean isRoundFinished) {
        this.isRoundFinished = isRoundFinished;
        return this;
    }

    public void setIsRoundFinished(Boolean isRoundFinished) {
        this.isRoundFinished = isRoundFinished;
    }

    public String getMessage() {
        return message;
    }

    public RPSGames message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isPlayer1Win() {
        return player1Win;
    }

    public RPSGames player1Win(Boolean player1Win) {
        this.player1Win = player1Win;
        return this;
    }

    public void setPlayer1Win(Boolean player1Win) {
        this.player1Win = player1Win;
    }

    public Boolean isPlayer2Win() {
        return player2Win;
    }

    public RPSGames player2Win(Boolean player2Win) {
        this.player2Win = player2Win;
        return this;
    }

    public void setPlayer2Win(Boolean player2Win) {
        this.player2Win = player2Win;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RPSGames rPSGames = (RPSGames) o;
        if (rPSGames.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rPSGames.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RPSGames{" +
            "id=" + getId() +
            ", player1='" + getPlayer1() + "'" +
            ", player1Champion='" + getPlayer1Champion() + "'" +
            ", player1Count=" + getPlayer1Count() +
            ", player1IsPlayed='" + isPlayer1IsPlayed() + "'" +
            ", player2='" + getPlayer2() + "'" +
            ", player2Champion='" + getPlayer2Champion() + "'" +
            ", player2Count=" + getPlayer2Count() +
            ", player2IsPlayed='" + isPlayer2IsPlayed() + "'" +
            ", isAI='" + isIsAI() + "'" +
            ", gameStartTime='" + getGameStartTime() + "'" +
            ", lastActionTime='" + getLastActionTime() + "'" +
            ", isGameFinished='" + isIsGameFinished() + "'" +
            ", isRoundFinished='" + isIsRoundFinished() + "'" +
            ", message='" + getMessage() + "'" +
            ", player1Win='" + isPlayer1Win() + "'" +
            ", player2Win='" + isPlayer2Win() + "'" +
            "}";
    }
}
