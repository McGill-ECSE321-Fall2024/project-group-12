package ca.mcgill.ecse321.group12.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.service.GameService;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	/**
	 * Return the game with the given ID.
	 * @param id The primary key of the person to find.
	 * @return The game with the given ID.
	 */
	@GetMapping("/game/{id}")
	public GameResponseDto findGameById(@PathVariable int id) {
		Game game = gameService.findGameById(id);
		return new GameResponseDto(game);
	}

	/**
	 * Deletes the game with the given ID.
	 * @param id The primary key of the game to delete.
	 * @return The game with the given ID.
	 */
	@DeleteMapping("/game/{id}")
	public void deleteGameById(@PathVariable int id) {
		gameService.deleteGameById(id);
	}

	/**
	 * Return all games.
	 * @param status filters out game by status.
	 * @return All games.
	 */
	@GetMapping("/game")
	public List<GameResponseDto> findGames(@RequestParam Optional<GameStatus> status) {
		Iterable<Game> games = gameService.findGames(status);
		List<GameResponseDto> gameResponseDtos = new ArrayList<>();
		for (Game game : games) {
			GameResponseDto dto = new GameResponseDto(game);
			gameResponseDtos.add(dto);
		}

		return gameResponseDtos;
	}

	/**
	 * Update a game.
	 * @param id The primary key for the game to be updated.
	 * @param game The game to create.
	 * @return The created game, including their ID.
	 */
	@PutMapping("/game/{id}")
	public GameResponseDto updateGame(@PathVariable int id, @RequestBody GameRequestDto game) {
		Game updatedGame = gameService.updateGame(id, game.getCategory(), game.getConsole(), game.getInventory(),
				game.getPrice(), game.getName(), game.getDescription(), game.getStatus());
		return new GameResponseDto(updatedGame);
	}

	/**
	 * Create a new game.
	 * @param game The game to create.
	 * @return The created game, including their ID.
	 */
	@PostMapping("/game")
	public GameResponseDto createGame(@RequestBody GameRequestDto game) {
		Game createdGame = gameService.createGame(game.getCategory(), game.getConsole(), game.getInventory(),
				game.getPrice(), game.getName(), game.getDescription(), game.getStatus());
		return new GameResponseDto(createdGame);
	}

}
