package ca.mcgill.ecse321.group12.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.dto.ImageDto;
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
	 * @author Julien Heng
	 */
	@GetMapping("/games/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GameResponseDto findGameById(@PathVariable int id) {
		Game game = gameService.findGameById(id);
		return new GameResponseDto(game);
	}

	/**
	 * Return all games.
	 * @param status filters out game by status.
	 * @return All games of the specified status.
	 * @author Julien Heng
	 */
	@GetMapping("/games")
	@ResponseStatus(HttpStatus.OK)
	public List<GameResponseDto> findGames(@RequestParam Optional<GameStatus> status) {
		Iterable<Game> games = gameService.findGames(status);
		List<GameResponseDto> gameResponseDtos = new ArrayList<>();

		// iterates through the list of games and returns a list of gameResponseDto
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
	 * @author Julien Heng
	 */
	@PutMapping("/games/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GameResponseDto updateGame(@PathVariable int id, @RequestBody GameRequestDto game) {
		Game updatedGame = gameService.updateGame(id, game.getCategory(), game.getConsole(), game.getInventory(),
				game.getPrice(), game.getName(), game.getDescription(), game.getStatus(), game.getYear());
		return new GameResponseDto(updatedGame);
	}

	/**
	 * Create a new game.
	 * @param game The game to create.
	 * @return The created game, including their ID.
	 * @author Julien Heng
	 */
	@PostMapping("/games")
	@ResponseStatus(HttpStatus.CREATED)
	public GameResponseDto createGame(@RequestBody GameRequestDto game) {
		Game createdGame = gameService.createGame(game.getCategory(), game.getConsole(), game.getInventory(),
				game.getPrice(), game.getName(), game.getDescription(), game.getStatus(), game.getYear());
		return new GameResponseDto(createdGame);
	}

	/**
	 * Get the cover image of a game.
	 * @param id The primary key of the game.
	 * @param imageDto The image to set as the cover.
	 * @return The image that was set as the cover.
	 */
	@GetMapping("/games/{id}/cover")
	public ImageDto getCover(@PathVariable int id) {
		return gameService.getCover(id);
	}

	/**
	 * Get the background image of a game.
	 * @param id The primary key of the game.
	 * @param imageDto The image to set as the background.
	 * @return The image that was set as the background.
	 */
	@GetMapping("/games/{id}/background")
	public ImageDto getBackground(@PathVariable int id) {
		return gameService.getBackground(id);
	}

	/**
	 * Set the cover image of a game.
	 * @param id The primary key of the game.
	 * @param imageDto The image to set as the cover.
	 * @return The image that was set as the cover.
	 */
	@PostMapping("/games/{id}/cover")
	public ImageDto setCover(@PathVariable int id, @RequestBody ImageDto imageDto) {
		gameService.setCover(id, imageDto.getImage(), imageDto.getType());
		return imageDto;
	}

	/**
	 * Set the background image of a game.
	 * @param id The primary key of the game.
	 * @param imageDto The image to set as the background.
	 * @return The image that was set as the background.
	 */
	@PostMapping("/games/{id}/background")
	public ImageDto setBackground(@PathVariable int id, @RequestBody ImageDto imageDto) {
		gameService.setBackground(id, imageDto.getImage(), imageDto.getType());
		return imageDto;
	}

}
