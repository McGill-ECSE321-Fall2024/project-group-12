package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CartRepositoryTests {
  
  @Autowired
	private CartRepository cartRepository;
  @Autowired
  private GameRepository gameRepository;

	@AfterEach
	public void clearDatabase() {
		cartRepository.deleteAll();
    gameRepository.deleteAll();
	}

	@Test
  @Transactional
	public void testPersistAndLoadCart() {

		// Create cart
		Cart cart = new Cart();

    // Create game
		Category category = Category.Action;
		Console console = Console.XBox;
		int inventory = 1;
		float price = 19.99f;
		String name = "FIFA" ;
		String description = "FIFA is a football game.";
		GameStatus status = GameStatus.InCatalog;
		Game game = new Game(0, category, console, inventory, price, name, description, status);
		game = gameRepository.save(game);

    assertNotNull(game);

    cart.addGame(game);

    // save cart
    cart = cartRepository.save(cart);
    int id = cart.getId();

    // read cart from db
    Cart cartFromDb = cartRepository.findCartById(id);

    List<Game> gamesCollection = cartFromDb.getGames();

    Hibernate.initialize(gamesCollection);

    // assert correct
    assertNotNull(cartFromDb);
    assertEquals(game, gamesCollection.get(0));

  }
    

}