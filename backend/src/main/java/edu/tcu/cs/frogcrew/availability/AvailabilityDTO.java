package edu.tcu.cs.frogcrew.availability;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvailabilityDTO {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Game id is required")
    private Long gameId;

    @NotNull(message = "Availability is required")
    private Integer availability;

    @Size(max = 255)
    private String comment;

    public AvailabilityDTO() {}

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Integer getAvailability() { return availability; }
    public void setAvailability(Integer availability) { this.availability = availability; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
