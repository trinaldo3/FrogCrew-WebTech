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
}
