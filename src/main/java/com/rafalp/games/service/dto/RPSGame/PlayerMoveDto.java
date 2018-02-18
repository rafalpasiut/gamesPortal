package com.rafalp.games.service.dto.RPSGame;

public class PlayerMoveDto {

    private String championName;
    private Boolean fightWithAI;
    private String playerID;

    public PlayerMoveDto(String championName, Boolean fightWithAI, String playerID) {
        this.championName = championName;
        this.fightWithAI = fightWithAI;
        this.playerID = playerID;
    }

    public PlayerMoveDto() {
    }

    public String getChampionName() {
        return championName;
    }

    public Boolean getFightWithAI() {
        return fightWithAI;
    }

    public String getPlayerID() {
        return playerID;
    }
}
