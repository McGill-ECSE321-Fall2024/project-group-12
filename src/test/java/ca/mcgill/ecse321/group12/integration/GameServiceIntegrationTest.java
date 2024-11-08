package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameServiceIntegrationTest {

	@Autowired
	private TestRestTemplate client;

	private final String VALID_NAME = "Game Test Integration";

	private final Category VALID_CATEGORY = Category.Action;

	private final int VALID_INVENTORY = 10;

	private final float VALID_PRICE = 79.99f;

	private final String VALID_DESCRIPTION = "This is a test game for integration testing";

	private final Console VALID_CONSOLE = Console.PC;

	private final GameStatus VALID_STATUS = GameStatus.InCatalog;

	private int validId;

	@Test
	@Order(1)
	public void testCreateValidPerson() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS);

		// Act
		ResponseEntity<GameResponseDto> response = client.postForEntity("/games", request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		GameResponseDto createdGame = response.getBody();
		assertNotNull(createdGame);
		assertEquals(VALID_NAME, createdGame.getName());
		assertEquals(VALID_CATEGORY, createdGame.getCategory());
		assertEquals(VALID_CONSOLE, createdGame.getConsole());
		assertEquals(VALID_INVENTORY, createdGame.getInventory());
		assertEquals(VALID_PRICE, createdGame.getPrice());
		assertEquals(VALID_DESCRIPTION, createdGame.getDescription());
		assertEquals(VALID_STATUS, createdGame.getStatus());
		assertNotNull(createdGame.getId());
		assertTrue(createdGame.getId() > 0, "Response should have a positive ID.");

		this.validId = createdGame.getId();
	}

	@Test
	@Order(2)
	public void testReadPersonByValidId() {
		// Arrange
		String url = "/games/" + this.validId;

		// Act
		ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		GameResponseDto game = response.getBody();
		assertNotNull(game);
		assertEquals(VALID_NAME, game.getName());
		assertEquals(VALID_CATEGORY, game.getCategory());
		assertEquals(VALID_CONSOLE, game.getConsole());
		assertEquals(VALID_INVENTORY, game.getInventory());
		assertEquals(VALID_PRICE, game.getPrice());
		assertEquals(VALID_DESCRIPTION, game.getDescription());
		assertEquals(VALID_STATUS, game.getStatus());
		assertEquals(this.validId, game.getId());

	}

}