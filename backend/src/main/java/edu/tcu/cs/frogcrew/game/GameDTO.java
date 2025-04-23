package edu.tcu.cs.frogcrew.game;

import java.time.LocalDate;
import java.time.LocalTime;

public class GameDTO {

    private Long gameId;
    private String opponent;
    private String location;
    private LocalDate gameDate;
    private LocalTime callTime;
    private boolean isFinalized;

    public GameDTO() {
    }

    public GameDTO(Game game) {
        this.gameId = game.getGameId();
        this.opponent = game.getOpponent();
        this.location = game.getLocation();
        this.gameDate = game.getGameDate();
        this.callTime = game.getCallTime();
        this.isFinalized = game.isFinalized();
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public LocalTime getCallTime() {
        return callTime;
    }

    public void setCallTime(LocalTime callTime) {
        this.callTime = callTime;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }
}
