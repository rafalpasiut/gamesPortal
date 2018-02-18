package com.rafalp.games.games.rps;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class RPSFightResult {

    String userChampion;
    String opponentChampion;
    String fightResult;
    String fightMessage;
    Boolean isRoundFinished;
    Boolean isGameFinished;

    public RPSFightResult() {
    }

    public RPSFightResult(String userChampion, String opponentChampion, String fightResult, String fightMessage, Boolean isRoundFinished, Boolean isGameFinished) {
        this.userChampion = userChampion;
        this.opponentChampion = opponentChampion;
        this.fightResult = fightResult;
        this.fightMessage = fightMessage;
        this.isRoundFinished = isRoundFinished;
        this.isGameFinished = isGameFinished;
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
}
