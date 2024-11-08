package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.GameRepository;

@SpringBootTest
public class GameServiceTests {

	@Mock
	private GameRepository gameRepository;

	@InjectMocks
	private GameService gameService;

	@SuppressWarnings("null")
	@Test
	public void testCreateValidGame() {

		// Arrange
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.0f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game();
		game.setCategory(aCategory);
		game.setConsole(aConsole);
		game.setInventory(aInventory);
		game.setPrice(aPrice);
		game.setName(aName);
		game.setStatus(aStatus);
		game.setDescription(aDescription);
		when(gameRepository.save(any(Game.class))).thenReturn(game);

		// Act
		Game createdGame = gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription,
				aStatus);

		// Assert
		assertNotNull(createdGame);
		assertEquals(aName, createdGame.getName());
		assertEquals(aCategory, createdGame.getCategory());
		assertEquals(aConsole, createdGame.getConsole());
		assertEquals(aInventory, createdGame.getInventory());
		assertEquals(aPrice, createdGame.getPrice());
		assertEquals(aDescription, createdGame.getDescription());
		assertEquals(aStatus, createdGame.getStatus());
		verify(gameRepository, times(1)).save(any(Game.class));
	}

	@Test
	public void testReadGameByValidId() {
		// Arrange
		int id = 42;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);

		when(gameRepository.findGameById(id)).thenReturn(game);

		// Act
		Game createdGame = gameService.findGameById(id);

		// Assert
		assertNotNull(createdGame);
		assertEquals(aName, createdGame.getName());
		assertEquals(aCategory, createdGame.getCategory());
		assertEquals(aConsole, createdGame.getConsole());
		assertEquals(aInventory, createdGame.getInventory());
		assertEquals(aPrice, createdGame.getPrice());
		assertEquals(aDescription, createdGame.getDescription());
		assertEquals(aStatus, createdGame.getStatus());
	}

	@Test
	public void testReadGameByInvalidId() {
		// Set up
		int id = 42;

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.findGameById(id));
		assertEquals("There is no game with ID " + id + ".", e.getMessage());
	}

	@Test
	public void testUpdateGameWithValidArguments() {
		// Arrange
		int id = 40;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
		when(gameRepository.findGameById(id)).thenReturn(game);

		Category updatedCategory = Category.Adventure;
		Console updatedConsole = Console.PlayStation;
		int updatedInventory = 2;
		float updatedPrice = 2f;
		String updatedName = "Game Name 2...";
		String updatedDescription = "Game Description 2...";
		GameStatus updatedStatus = GameStatus.InCatalog;

		Game updatedGame = new Game();
		updatedGame.setCategory(updatedCategory);
		updatedGame.setConsole(updatedConsole);
		updatedGame.setInventory(updatedInventory);
		updatedGame.setPrice(updatedPrice);
		updatedGame.setName(updatedName);
		updatedGame.setStatus(updatedStatus);
		updatedGame.setDescription(updatedDescription);

		when(gameRepository.save(any(Game.class))).thenReturn(updatedGame);

		// Act

		gameService.updateGame(id, updatedCategory, updatedConsole, updatedInventory, updatedPrice, updatedName,
				updatedDescription, updatedStatus);

		// Assert
		assertNotNull(updatedGame);
		assertEquals(updatedName, updatedGame.getName());
		assertEquals(updatedCategory, updatedGame.getCategory());
		assertEquals(updatedConsole, updatedGame.getConsole());
		assertEquals(updatedInventory, updatedGame.getInventory());
		assertEquals(updatedPrice, updatedGame.getPrice());
		assertEquals(updatedDescription, updatedGame.getDescription());
		assertEquals(updatedStatus, updatedGame.getStatus());
	}

	@Test
	public void testUpdateGameWithInvalidInventory() {
		// Arrange
		int id = 50;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
		when(gameRepository.findGameById(id)).thenReturn(game);

		Category updatedCategory = Category.Adventure;
		Console updatedConsole = Console.PlayStation;
		int updatedInventory = -2;
		float updatedPrice = 2f;
		String updatedName = "Game Name 2...";
		String updatedDescription = "Game Description 2...";
		GameStatus updatedStatus = GameStatus.InCatalog;

		Game updatedGame = new Game();
		updatedGame.setCategory(updatedCategory);
		updatedGame.setConsole(updatedConsole);
		updatedGame.setInventory(updatedInventory);
		updatedGame.setPrice(updatedPrice);
		updatedGame.setName(updatedName);
		updatedGame.setStatus(updatedStatus);
		updatedGame.setDescription(updatedDescription);

		when(gameRepository.save(any(Game.class))).thenReturn(updatedGame);

		// Act

		// Assert

		CustomException e = assertThrows(CustomException.class, () -> gameService.updateGame(id, updatedCategory,
				updatedConsole, updatedInventory, updatedPrice, updatedName, updatedDescription, updatedStatus));
		assertEquals("Inventory has to be a positive integer.", e.getMessage());
	}

	@Test
	public void testUpdateGameWithInvalidName() {
		// Arrange
		int id = 51;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
		when(gameRepository.findGameById(id)).thenReturn(game);

		Category updatedCategory = Category.Adventure;
		Console updatedConsole = Console.PlayStation;
		int updatedInventory = 2;
		float updatedPrice = 2f;
		String updatedName = "";
		String updatedDescription = "Game Description 2...";
		GameStatus updatedStatus = GameStatus.InCatalog;

		Game updatedGame = new Game();
		updatedGame.setCategory(updatedCategory);
		updatedGame.setConsole(updatedConsole);
		updatedGame.setInventory(updatedInventory);
		updatedGame.setPrice(updatedPrice);
		updatedGame.setName(updatedName);
		updatedGame.setStatus(updatedStatus);
		updatedGame.setDescription(updatedDescription);

		when(gameRepository.save(any(Game.class))).thenReturn(updatedGame);

		// Act

		// Assert

		CustomException e = assertThrows(CustomException.class, () -> gameService.updateGame(id, updatedCategory,
				updatedConsole, updatedInventory, updatedPrice, updatedName, updatedDescription, updatedStatus));
		assertEquals("Name cannot be empty.", e.getMessage());
	}

	@Test
	public void testUpdateGameWithInvalidDescription() {
		// Arrange
		int id = 52;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
		when(gameRepository.findGameById(id)).thenReturn(game);

		Category updatedCategory = Category.Adventure;
		Console updatedConsole = Console.PlayStation;
		int updatedInventory = 2;
		float updatedPrice = 2f;
		String updatedName = "Game Name 2...";
		String updatedDescription = "";
		GameStatus updatedStatus = GameStatus.InCatalog;

		Game updatedGame = new Game();
		updatedGame.setCategory(updatedCategory);
		updatedGame.setConsole(updatedConsole);
		updatedGame.setInventory(updatedInventory);
		updatedGame.setPrice(updatedPrice);
		updatedGame.setName(updatedName);
		updatedGame.setStatus(updatedStatus);
		updatedGame.setDescription(updatedDescription);

		when(gameRepository.save(any(Game.class))).thenReturn(updatedGame);

		// Act

		// Assert

		CustomException e = assertThrows(CustomException.class, () -> gameService.updateGame(id, updatedCategory,
				updatedConsole, updatedInventory, updatedPrice, updatedName, updatedDescription, updatedStatus));
		assertEquals("Description cannot be empty.", e.getMessage());
	}

	@Test
	public void testUpdateGameWithInvalidPrice() {
		// Arrange
		int id = 55;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
		when(gameRepository.findGameById(id)).thenReturn(game);

		Category updatedCategory = Category.Adventure;
		Console updatedConsole = Console.PlayStation;
		int updatedInventory = 2;
		float updatedPrice = -2f;
		String updatedName = "Game Name 2...";
		String updatedDescription = "Game Description 2...";
		GameStatus updatedStatus = GameStatus.InCatalog;

		Game updatedGame = new Game();
		updatedGame.setCategory(updatedCategory);
		updatedGame.setConsole(updatedConsole);
		updatedGame.setInventory(updatedInventory);
		updatedGame.setPrice(updatedPrice);
		updatedGame.setName(updatedName);
		updatedGame.setStatus(updatedStatus);
		updatedGame.setDescription(updatedDescription);

		when(gameRepository.save(any(Game.class))).thenReturn(updatedGame);

		// Act

		// Assert

		CustomException e = assertThrows(CustomException.class, () -> gameService.updateGame(id, updatedCategory,
				updatedConsole, updatedInventory, updatedPrice, updatedName, updatedDescription, updatedStatus));
		assertEquals("Price has to be a positive number.", e.getMessage());
	}

}
