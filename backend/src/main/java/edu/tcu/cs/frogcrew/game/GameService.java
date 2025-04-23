package edu.tcu.cs.frogcrew.game;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

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
            return g;
        }).toList();

        return repository.saveAll(games);
    }
}
