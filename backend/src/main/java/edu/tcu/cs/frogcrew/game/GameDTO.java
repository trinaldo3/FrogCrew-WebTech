package edu.tcu.cs.frogcrew.game;

import java.time.LocalDate;

public class GameDTO {

    private Long gameId;
    private Long scheduleId;
    private LocalDate gameDate;
    private String venue;
    private String opponent;
    private boolean isFinalized;

    public GameDTO() {}

    public GameDTO(Game game) {
        this.gameId = game.getGameId();
        this.scheduleId = game.getScheduleId();
        this.gameDate = game.getGameDate();
        this.venue = game.getVenue();
        this.opponent = game.getOpponent();
        this.isFinalized = game.isFinalized();
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }

    public void setIsFinalized(boolean b) {
        this.isFinalized = isFinalized;
    }
}
