package edu.tcu.cs.frogcrew.crewlist;

import java.util.List;

public class CrewListDTO {

    private Long gameId;
    private List<Long> crewMemberIds;

    public CrewListDTO() {
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<Long> getCrewMemberIds() {
        return crewMemberIds;
    }

    public void setCrewMemberIds(List<Long> crewMemberIds) {
        this.crewMemberIds = crewMemberIds;
    }
}
