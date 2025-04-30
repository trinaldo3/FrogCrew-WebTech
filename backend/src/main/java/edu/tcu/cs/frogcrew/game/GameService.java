package edu.tcu.cs.frogcrew.game;

import org.springframework.stereotype.Service;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import edu.tcu.cs.frogcrew.crewmember.CrewMemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository repository;

    // public GameService(GameRepository repository) {
    //     this.repository = repository;
    //     this.crewRepository = null;
    // }

    public List<GameDTO> getAllGameDTOs() {
        return repository.findAll()
                .stream()
                .map(GameDTO::new)
                .collect(Collectors.toList());
    }

    public List<Game> createGameSchedule(String token, List<GameDTO> gameDTOList) {
        List<Game> games = gameDTOList.stream().map(dto -> {
            Game g = new Game();
            g.setOpponent(dto.getOpponent());
            g.setLocation(dto.getLocation());
            g.setGameDate(dto.getGameDate());
            g.setCallTime(dto.getCallTime());
            g.setSport(dto.getSport()); 
            return g;
        }).toList();
    
        return repository.saveAll(games);
    }


    public void assignCrewMembers(Long gameId, List<Long> crewIds) {
    Game game = repository.findById(gameId)
            .orElseThrow(() -> new RuntimeException("Game not found"));

    List<CrewMember> crewList = crewRepository.findAllById(crewIds);
    game.setAssignedCrew(crewList); // This assumes a `List<CrewMember>` field on Game

    repository.save(game);
}
private final CrewMemberRepository crewRepository;

public GameService(GameRepository repository, CrewMemberRepository crewRepository) {
    this.repository = repository;
    this.crewRepository = crewRepository;
}


}
