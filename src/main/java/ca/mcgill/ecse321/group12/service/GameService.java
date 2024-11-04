package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import jakarta.transaction.Transactional;


@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game findGameById(int gameId) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("There is no game with ID " + gameId + ".");
        }
        return game;
    }

    @Transactional
    public Game createGame(int aId, Category aCategory, Console aConsole, int aInventory, float aPrice, String aName,
        String aDescription, GameStatus aStatus) {
        
        if (aInventory < 0) {
            throw new IllegalArgumentException("Inventory has to be a positive integer.");
        }
        if (aPrice < 0) {
            throw new IllegalArgumentException("Price has to be a positive number.");
        }
        if (aName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (aDescription.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        Game gameToCreate = new Game(aId, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
        return gameRepository.save(gameToCreate);
    }
}
