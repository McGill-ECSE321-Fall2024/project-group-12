package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.group12.dto.AuthRequestDto;
import ca.mcgill.ecse321.group12.dto.AuthResponseDto;
import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.GameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameServiceIntegrationTest {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private GameRepository gameRepository;

	private final String VALID_NAME = "Game Test Integration";

	private final Category VALID_CATEGORY = Category.Action;

	private final int VALID_INVENTORY = 10;

	private final float VALID_PRICE = 79.99f;

	private final String VALID_DESCRIPTION = "This is a test game for integration testing";

	private final Console VALID_CONSOLE = Console.PC;

	private final GameStatus VALID_STATUS = GameStatus.InCatalog;

	private final String VALID_NAME_2 = "Game Test Integration 2";

	private final Category VALID_CATEGORY_2 = Category.Adventure;

	private final int VALID_INVENTORY_2 = 15;

	private final float VALID_PRICE_2 = 99.99f;

	private final String VALID_DESCRIPTION_2 = "This is a test game for integration testing 2";

	private final Console VALID_CONSOLE_2 = Console.PlayStation;

	private final GameStatus VALID_STATUS_2 = GameStatus.Archived;

	private final int VALID_YEAR = 2021;

	private final int VALID_YEAR_2 = 2020;

	private final String INVALID_NAME = "";

	private final int INVALID_INVENTORY = -10;

	private final float INVALID_PRICE = -79.99f;

	private final String INVALID_DESCRIPTION = "";

	private int validId;

	private String authToken;

	@BeforeAll
	public void setup() {

		// create an employee so we have an auth token
		EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
		employeeRequest.setName("Employee");
		employeeRequest.setEmail("johndeer@company.com");
		employeeRequest.setPassword("password123");
		employeeRequest.setPhoneNumber("604 000 0000");
		ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employees", employeeRequest,
				EmployeeResponseDto.class);
		assertEquals(HttpStatus.CREATED, employeeResponse.getStatusCode());
		// use the auth endpoint to get a token for the employee
		AuthRequestDto authRequest = new AuthRequestDto();
		authRequest.setEmail(employeeRequest.getEmail());
		authRequest.setPassword(employeeRequest.getPassword());
		ResponseEntity<AuthResponseDto> authResponse = client.postForEntity("/auth/signin", authRequest,
				AuthResponseDto.class);
		assertEquals(HttpStatus.OK, authResponse.getStatusCode());
		// store the token
		AuthResponseDto auth = authResponse.getBody();
		assertNotNull(auth);
		assertNotNull(auth.getToken());
		authToken = "Bearer " + auth.getToken();

	}

	@AfterTestClass
	public void clearDatabase() {
		gameRepository.deleteAll();
	}

	/**
	 * Tries to create a game with an invalid inventory value.
	 * @author Julien Heng
	 */
	@Test
	@Order(1)
	public void testCreateGameWithInvalidInventory() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, INVALID_INVENTORY, VALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);

		// Act
		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<GameResponseDto> response = client.exchange(req, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Tries to create a game with an invalid price value.
	 * @author Julien Heng
	 */
	@Test
	@Order(2)
	public void testCreateGameWithInvalidPrice() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, INVALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);

		// Act
		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<GameResponseDto> response = client.exchange(req, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Tries to create a game with an empty name.
	 * @author Julien Heng
	 */
	@Test
	@Order(3)
	public void testCreateGameWithInvalidName() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				INVALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);

		// Act
		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<GameResponseDto> response = client.exchange(req, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Tries to create a game with an empty description.
	 * @author Julien Heng
	 */
	@Test
	@Order(4)
	public void testCreateGameWithInvalidDescription() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				INVALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);

		// Act
		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<GameResponseDto> response = client.exchange(req, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Tries to create a game with valid fields.
	 * @author Julien Heng
	 */
	@Test
	@Order(5)
	public void testCreateValidGame() {
		// Arrange
		GameRequestDto request = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);

		// Act
		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<GameResponseDto> response = client.exchange(req, GameResponseDto.class);

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

	/**
	 * Tries to read a game with an existing ID.
	 * @author Julien Heng
	 */
	@Test
	@Order(6)
	public void testReadGameByValidId() {
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

	/**
	 * Tries to read a game with a non-existing ID.
	 * @author Julien Heng
	 */
	@Test
	@Order(7)
	public void testReadGameByInvalidId() {
		// Arrange
		int invalidId = this.validId + 1;
		String url = "/games/" + invalidId;

		// Act
		ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Tries to update a game with an invalid price value.
	 * @author Julien Heng
	 */
	@Test
	@Order(8)
	public void testUpdateGameWithInvalidPrice() {
		// Arrange
		String url = "/games/" + this.validId;
		GameRequestDto body = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, INVALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);
		RequestEntity<GameRequestDto> request = RequestEntity.put(url)
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	/**
	 * Tries to update a game with an invalid inventory value.
	 * @author Julien Heng
	 */
	@Test
	@Order(9)
	public void testUpdateGameWithInvalidInventory() {
		// Arrange
		String url = "/games/" + this.validId;
		GameRequestDto body = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, INVALID_INVENTORY, VALID_PRICE,
				VALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);
		RequestEntity<GameRequestDto> request = RequestEntity.put(url)
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	/**
	 * Tries to update a game with an empty name.
	 * @author Julien Heng
	 */
	@Test
	@Order(10)
	public void testUpdateGameWithInvalidName() {
		// Arrange
		String url = "/games/" + this.validId;
		GameRequestDto body = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				INVALID_NAME, VALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);
		RequestEntity<GameRequestDto> request = RequestEntity.put(url)
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	/**
	 * Tries to update a game with an empty description.
	 * @author Julien Heng
	 */
	@Test
	@Order(11)
	public void testUpdateGameWithInvalidDescription() {
		// Arrange
		String url = "/games/" + this.validId;
		GameRequestDto body = new GameRequestDto(VALID_CATEGORY, VALID_CONSOLE, VALID_INVENTORY, VALID_PRICE,
				VALID_NAME, INVALID_DESCRIPTION, VALID_STATUS, VALID_YEAR);
		RequestEntity<GameRequestDto> request = RequestEntity.put(url)
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	/**
	 * Tries to update a game with a valid fields.
	 * @author Julien Heng
	 */
	@Test
	@Order(12)
	public void testUpdateGameWithValidArguments() {
		// Arrange
		String url = "/games/" + this.validId;
		GameRequestDto body = new GameRequestDto(VALID_CATEGORY_2, VALID_CONSOLE_2, VALID_INVENTORY_2, VALID_PRICE_2,
				VALID_NAME_2, VALID_DESCRIPTION_2, VALID_STATUS_2, VALID_YEAR_2);
		RequestEntity<GameRequestDto> request = RequestEntity.put(url)
			.header("Authorization", authToken)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, request, GameResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		GameResponseDto updatedGame = response.getBody();
		assertNotNull(updatedGame);
		assertEquals(VALID_NAME_2, updatedGame.getName());
		assertEquals(VALID_CATEGORY_2, updatedGame.getCategory());
		assertEquals(VALID_CONSOLE_2, updatedGame.getConsole());
		assertEquals(VALID_INVENTORY_2, updatedGame.getInventory());
		assertEquals(VALID_PRICE_2, updatedGame.getPrice());
		assertEquals(VALID_DESCRIPTION_2, updatedGame.getDescription());
		assertEquals(VALID_STATUS_2, updatedGame.getStatus());
		assertNotNull(updatedGame.getId());
		assertTrue(updatedGame.getId() > 0, "Response should have a positive ID.");

		this.validId = updatedGame.getId();

	}

}