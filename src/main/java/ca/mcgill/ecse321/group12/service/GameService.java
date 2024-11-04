package ca.mcgill.ecse321.group12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Game;
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

    /**
     * Goes through each game specified and reduces its stock. If any game is out of stock, it will throw
     * an error and the transaction will be cancelled.
     * @author James Madden
     * @param games
     */
    @Transactional
    public void reduceGamesInventory (List<Game> games) {
      
      // go through each game
      for (Game game : games) {

        // error if the inventory is 0
        if (game.getInventory() <= 0) {
          throw new IllegalArgumentException("Game " + game.getName() + " is out of stock.");
        } else {
          game.setInventory(game.getInventory() - 1);
          gameRepository.save(game);
        }
  
      }

    }
}