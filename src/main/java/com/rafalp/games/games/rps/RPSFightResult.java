package com.rafalp.games.games.rps;

public class RPSFightResult {

    String userChampion;
    String opponentChampion;
    String fightResult;
    String fightMessage;

    public RPSFightResult() {
    }

    public RPSFightResult(String userChampion, String opponentChampion, String fightResult, String fightMessage) {
        this.userChampion = userChampion;
        this.opponentChampion = opponentChampion;
        this.fightResult = fightResult;
        this.fightMessage = fightMessage;
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
}
