package ca.mcgill.ecse321.group12.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.service.GameService;

@RestController
public class GameController {
    
    @Autowired
    private GameService gameService;

    /**
     * Return the game with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The game with the given ID.
     */
    @GetMapping("/game/{id}")
    public GameResponseDto findGameById(@PathVariable int id) {
        Game game = gameService.findGameById(id);
        return new GameResponseDto(game);
    }

}
