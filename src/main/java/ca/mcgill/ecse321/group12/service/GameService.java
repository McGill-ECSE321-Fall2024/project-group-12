package ca.mcgill.ecse321.group12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.dto.ImageDto;
import ca.mcgill.ecse321.group12.exception.CustomException;
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

	/**
	 * @author Julien Heng
	 */
	public Game findGameById(int gameId) {
		Game game = gameRepository.findGameById(gameId);
		if (game == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no game with ID " + gameId + ".");
		}
		return game;
	}

	/**
	 * @author Julien Heng
	 */
	public Iterable<Game> findGames(Optional<GameStatus> status) {
		Iterable<Game> games = gameRepository.findAll();

		// if no status is provided then all the games are returned
		if (status.isEmpty()) {
			return games;
		}
		else {
			// filters out the game by their status
			List<Game> filteredGames = new ArrayList<>();
			for (Game game : games) {
				if (game.getStatus() == status.get()) {
					filteredGames.add(game);
				}
			}
			return filteredGames;
		}
	}

	/**
	 * @author Julien Heng
	 */
	@Transactional
	public Game createGame(Category aCategory, Console aConsole, int aInventory, float aPrice, String aName,
			String aDescription, GameStatus aStatus, int aYear) {

		// input validation
		if (aInventory < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Inventory has to be a positive integer.");
		}
		if (aPrice < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Price has to be a positive number.");
		}
		if (aName == null || aName.isBlank()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
		}
		if (aDescription == null || aDescription.isBlank()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Description cannot be empty.");
		}
		if (aYear < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Year has to be a positive integer.");
		}

		Game gameToCreate = new Game();
		gameToCreate.setCategory(aCategory);
		gameToCreate.setConsole(aConsole);
		gameToCreate.setInventory(aInventory);
		gameToCreate.setPrice(aPrice);
		gameToCreate.setName(aName);
		gameToCreate.setDescription(aDescription);
		gameToCreate.setStatus(aStatus);
		gameToCreate.setYear(aYear);

		return gameRepository.save(gameToCreate);
	}

	/**
	 * @author Julien Heng
	 */
	@Transactional
	public Game updateGame(int aId, Category aCategory, Console aConsole, int aInventory, float aPrice, String aName,
			String aDescription, GameStatus aStatus, int aYear) {

		// input validation
		if (aInventory < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Inventory has to be a positive integer.");
		}
		if (aPrice < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Price has to be a positive number.");
		}
		if (aName == null || aName.isBlank()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
		}
		if (aDescription == null || aDescription.isBlank()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Description cannot be empty.");
		}
		if (aYear < 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Year has to be a positive integer.");
		}

		Game gameToUpdate = gameRepository.findGameById(aId);
		gameToUpdate.setCategory(aCategory);
		gameToUpdate.setConsole(aConsole);
		gameToUpdate.setInventory(aInventory);
		gameToUpdate.setPrice(aPrice);
		gameToUpdate.setName(aName);
		gameToUpdate.setDescription(aDescription);
		gameToUpdate.setStatus(aStatus);
		gameToUpdate.setYear(aYear);

		return gameRepository.save(gameToUpdate);
	}

	/**
	 * Goes through each game specified and reduces its stock. If any game is out of
	 * stock, it will throw an error and the transaction will be cancelled.
	 * @author James Madden
	 * @param games
	 */
	@Transactional
	public void reduceGamesInventory(List<Game> games) {

		// go through each game
		for (Game game : games) {

			// error if the inventory is 0
			if (game.getInventory() <= 0) {
				throw new CustomException(HttpStatus.BAD_REQUEST, "Game " + game.getName() + " is out of stock.");
			}
			else {
				game.setInventory(game.getInventory() - 1);
				gameRepository.save(game);
			}

		}

	}

	/**
	 * Increase the game inventory since the games have been returned
	 * @author James Madden
	 */
	@Transactional
	public void returnGames(List<Game> games) {

		// go through each game
		for (Game game : games) {
			game.setInventory(game.getInventory() + 1);
			gameRepository.save(game);
		}

	}

	/**
	 * Set the cover of a game
	 * @param id The ID of the game
	 * @param cover The cover of the game
	 */
	@Transactional
	public void setCover(int id, byte[] cover, String coverType) {
		Game game = gameRepository.findGameById(id);
		game.setCover(cover);
		game.setCoverType(coverType);
		gameRepository.save(game);
	}

	/**
	 * Set the background image of a game
	 * @param id The ID of the game
	 * @param cover The background image of the game
	 */
	@Transactional
	public void setBackground(int id, byte[] background, String backgroundType) {
		Game game = gameRepository.findGameById(id);
		game.setBackground(background);
		game.setBackgroundType(backgroundType);
		gameRepository.save(game);
	}

	/**
	 * Get the cover image of a game
	 * @param id The ID of the game
	 * @return The cover image of the game
	 */
	public ImageDto getCover(int id) {
		Game game = gameRepository.findGameById(id);
		return new ImageDto(game.getCoverType(), game.getCover());
	}

	/**
	 * Get the cover image of a game
	 * @param id The ID of the game
	 * @return The cover image of the game
	 */
	public ImageDto getBackground(int id) {
		Game game = gameRepository.findGameById(id);
		return new ImageDto(game.getBackgroundType(), game.getBackground());
	}

}
