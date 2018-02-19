package com.rafalp.games.games.rps;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class RPSFightResult {

    String userChampion;
    String opponentChampion;
    String fightResult;
    String fightMessage;
    Boolean isRoundFinished;
    Boolean isGameFinished;
    Integer winCount;
    Integer looseCount;

    public RPSFightResult() {
    }

    public RPSFightResult(String userChampion, String opponentChampion, String fightResult, String fightMessage, Boolean isRoundFinished, Boolean isGameFinished, Integer winCount, Integer looseCount) {
        this.userChampion = userChampion;
        this.opponentChampion = opponentChampion;
        this.fightResult = fightResult;
        this.fightMessage = fightMessage;
        this.isRoundFinished = isRoundFinished;
        this.isGameFinished = isGameFinished;
        this.winCount = winCount;
        this.looseCount = looseCount;
    }

    public String getUserChampion() {
        return userChampion;
    }

    public String getOpponentChampion() {
        return opponentChampion;
    }

    public String getFightResult() {
        return fightResult;
    }

    public String getFightMessage() {
        return fightMessage;
    }

    public Boolean getRoundFinished() {
        return isRoundFinished;
    }

    public Boolean getGameFinished() {
        return isGameFinished;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public Integer getLooseCount() {
        return looseCount;
    }

    public void setUserChampion(String userChampion) {
        this.userChampion = userChampion;
    }

    public void setOpponentChampion(String opponentChampion) {
        this.opponentChampion = opponentChampion;
    }

    public void setFightResult(String fightResult) {
        this.fightResult = fightResult;
    }

    public void setFightMessage(String fightMessage) {
        this.fightMessage = fightMessage;
    }

    public void setRoundFinished(Boolean roundFinished) {
        isRoundFinished = roundFinished;
    }

    public void setGameFinished(Boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public void setLooseCount(Integer looseCount) {
        this.looseCount = looseCount;
    }
}
