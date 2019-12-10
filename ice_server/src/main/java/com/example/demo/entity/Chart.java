package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

public class Chart {
    private Integer gameId;

    private Integer userId;

    @Getter @Setter
    private Integer consoleId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}