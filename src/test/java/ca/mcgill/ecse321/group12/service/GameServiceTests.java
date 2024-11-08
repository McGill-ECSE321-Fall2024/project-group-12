package ca.mcgill.ecse321.group12.service;

import java.util.ArrayList;
import java.util.List;

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
import org.mockito.invocation.InvocationOnMock;
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

	/**
	 * Tries to create a game with valid fields
	 * @author Julien Heng
	 */
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

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testCreateGameWithInvalidPrice() {
		// Arrange
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = -1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		// Act

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus));
		assertEquals("Price has to be a positive number.", e.getMessage());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testCreateGameWithInvalidInventory() {
		// Arrange
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = -1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		// Act

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus));
		assertEquals("Inventory has to be a positive number.", e.getMessage());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testCreateGameWithInvalidName() {
		// Arrange
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;

		// Act

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus));
		assertEquals("Name cannot be empty.", e.getMessage());
	}
	/**
	 * @author Julien Heng
	 */
	@Test
	public void testCreateGameWithInvalidDescription() {
		// Arrange
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game name";
		String aDescription = "";
		GameStatus aStatus = GameStatus.Archived;

		// Act

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus));
		assertEquals("Description cannot be empty.", e.getMessage());
	}

	/**
	 * @author Julien Heng
	 */
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

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testReadGameByInvalidId() {
		// Set up
		int id = 42;

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> gameService.findGameById(id));
		assertEquals("There is no game with ID " + id + ".", e.getMessage());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testUpdateGameWithValidArguments() {
		// Arrange
		int id = 47;
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

	/**
	 * @author Julien Heng Updating a game with a negative inventory should not be
	 * allowed.
	 */
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

	/**
	 * @author Julien Heng Updating a game with a null or empty name should not be
	 * allowed.
	 */
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

	/**
	 * @author Julien Heng Updating a game with a null or empty description should not be
	 * allowed.
	 */
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

	/**
	 * @author Julien Heng Updating a game with a negative price should not be allowed.
	 */
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

	/**
	 * Test that the stock of a list of games will be successfully reduced when every
	 * stock is at least 1.
	 * @author James Madden
	 */
	@Test
	public void testSuccessfullyReduceGameInventory() {

		// create games
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();

		// set their inventories
		int inv1 = 1;
		int inv2 = 50;
		int inv3 = 1000;
		game1.setInventory(inv1);
		game2.setInventory(inv2);
		game3.setInventory(inv3);

		// mock the saving method
		when(gameRepository.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		List<Game> games = new ArrayList<>();
		games.add(game1);
		games.add(game2);
		games.add(game3);

		// call the method
		gameService.reduceGamesInventory(games);

		// check the inventory of the games is one less than it was
		assertEquals(inv1 - 1, game1.getInventory());
		assertEquals(inv2 - 1, game2.getInventory());
		assertEquals(inv3 - 1, game3.getInventory());

	}

	/**
	 * Test that if a game is out of stock, reduceGamesInventory will throw an error.
	 * @author James Madden
	 */
	@Test
	public void testUnsuccessfullyReduceGameInventory() {

		// create games
		Game game1 = new Game();
		game1.setName("Game 1");
		Game game2 = new Game();
		game2.setName("Game 2");
		Game game3 = new Game();
		game3.setName("Game 3");

		// set their inventories
		int inv1 = 1;
		int inv2 = 50;
		int inv3 = 0;
		game1.setInventory(inv1);
		game2.setInventory(inv2);
		game3.setInventory(inv3);

		// mock the saving method
		when(gameRepository.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		List<Game> games = new ArrayList<>();
		games.add(game1);
		games.add(game2);
		games.add(game3);

		// call the method and expect an error
		CustomException error = assertThrows(CustomException.class, () -> gameService.reduceGamesInventory(games));
		assertEquals("Game " + game3.getName() + " is out of stock.", error.getMessage());

	}

	/**
	 * Test that when an order is returned, the inventory on all games are updated
	 * @author James Madden
	 */
	public void testReturnGame() {

		// mock the saving method
		when(gameRepository.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// create games
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();
		int inv1 = 9;
		int inv2 = 19;
		int inv3 = 29;
		game1.setInventory(inv1);
		game2.setInventory(inv2);
		game3.setInventory(inv3);

		// make list of games
		List<Game> games = new ArrayList<>();
		games.add(game1);
		games.add(game2);
		games.add(game3);

		// use the service method to increase inventory
		gameService.returnGames(games);

		// check that the inventories were updated correctly
		assertEquals(inv1 + 1, games.get(0).getInventory());
		assertEquals(inv2 + 1, games.get(1).getInventory());
		assertEquals(inv3 + 1, games.get(2).getInventory());

	}

}
