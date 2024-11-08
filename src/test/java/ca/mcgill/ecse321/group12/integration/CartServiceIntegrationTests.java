package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CartServiceIntegrationTests {

	@Autowired
	private TestRestTemplate client;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private GameRepository gameRepo;

	private int validId;

	private Cart cart;

    private int gameId;

	@BeforeAll
	public void setup() {
		// Create a cart for testing
		this.cart = new Cart();
		cartRepo.save(this.cart);
		this.validId = this.cart.getId();
        // Create a game for testing
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
		gameRepo.save(game);
		this.gameId = game.getId();
	}

    @AfterAll
	public void clearDatabase() {
		cartRepo.deleteAll();
        gameRepo.deleteAll();
	}

	@Test
	@Order(1)
	public void testFindCartByValidId() {
		// Arrange
		String url = "/cart/" + this.validId;

		// Act
		ResponseEntity<CartResponseDto> response = client.getForEntity(url, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		assertEquals(this.validId, cart.getId());
	}

	@Test
	@Order(2)
	public void testAddGameToCart() {
		// Arrange
		String url = "/cart/" + this.validId;
		this.cart.addGame(gameRepo.findGameById(this.gameId));

		// Act
        CartRequestDto body = new CartRequestDto();
        body.setGameId(this.gameId);
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.post(url)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.PUT, CartRequestEntity,
				CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		assertEquals(this.cart.getGames(), cart.getGames());
	}

}
