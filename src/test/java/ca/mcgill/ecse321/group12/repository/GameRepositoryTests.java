package ca.mcgill.ecse321.group12.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

@SpringBootTest
public class GameRepositoryTests {
	@Autowired
	private GameRepository gameRepository;

	@AfterEach
	public void clearDatabase() {
		gameRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadGame() {
		// Create Game
		Category category = Category.Action;
		Console console = Console.XBox;
		int inventory = 1;
		float price = 19.99f;
		String name = "FIFA" ;
		String description = "FIFA is a football game.";
		GameStatus status = GameStatus.InCatalog;

		Game game = new Game(0, category, console, inventory, price, name, description, status);

		// Save person
		game = gameRepository.save(game);
		int id = game.getId();

		// Read person from database
		Game gameFromDb = gameRepository.findGameById(id);

		// Assert correct response
		assertNotNull(game);
		assertEquals(gameFromDb.getName(), name);
		assertEquals(gameFromDb.getCategory(), category);
		assertEquals(gameFromDb.getConsole(), console);
		assertEquals(gameFromDb.getInventory(), inventory);
		assertEquals(gameFromDb.getPrice(), price);
		assertEquals(gameFromDb.getDescription(), description);
		assertEquals(gameFromDb.getStatus(), status);
	}
}