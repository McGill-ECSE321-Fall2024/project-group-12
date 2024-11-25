package ca.mcgill.ecse321.group12.integration;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import ca.mcgill.ecse321.group12.dto.AuthRequestDto;
import ca.mcgill.ecse321.group12.dto.AuthResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerCreateResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.dto.WishlistRequestDto;
import ca.mcgill.ecse321.group12.dto.WishlistResponseDto;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.repository.WishlistRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class WishlistServiceIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private WishlistRepository wishlistRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private CustomerRepository customerRepo;

	private int validId;

	private CustomerCreateResponseDto customer;

	private GameResponseDto game;

	private String customerAuth;

	private String employeeAuth;

	@BeforeAll
	public void setup() {
		// Create (POST) a customer to use their wishlist for tests
		CustomerRequestDto customerRequest = new CustomerRequestDto("abdefghij@mail.mcgill.ca", "hello123456", "Test",
				"987654321");
		ResponseEntity<CustomerCreateResponseDto> customerResponse = client.postForEntity("/customers", customerRequest,
				CustomerCreateResponseDto.class);
		// Save the response
		this.customer = customerResponse.getBody();
		// get the auth token
		customerAuth = "Bearer " + customer.getToken();

		// create an employee for the auth token
		EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
		employeeRequest.setName("Carmin 3");
		employeeRequest.setEmail("carmin3@company.com");
		employeeRequest.setPassword("password123");
		employeeRequest.setPhoneNumber("604 000 0000");
		ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employees", employeeRequest,
				EmployeeResponseDto.class);
		assertEquals(HttpStatus.CREATED, employeeResponse.getStatusCode());
		assertNotNull(employeeResponse.getBody());
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
		employeeAuth = "Bearer " + auth.getToken();

		// Create (POST) a game to use it for tests
		GameRequestDto gameRequest = new GameRequestDto(Category.Action, Console.PC, 1, 1.0f, "Game Name...",
				"Game Description...", GameStatus.Archived, 2021);

		RequestEntity<GameRequestDto> req = RequestEntity.post("/games")
			.header("Authorization", employeeAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(gameRequest);
		GameResponseDto gameDto = client.exchange(req, GameResponseDto.class).getBody();
		// Save the response
		this.game = gameDto;
	}

	@AfterAll
	public void clearDatabase() {
		customerRepo.deleteAll();
		wishlistRepo.deleteAll();
		gameRepo.deleteAll();
	}

	/**
	 * Test finding a wishlist by a valid ID.
	 * @author Julien Heng
	 */
	@Test
	@Order(1)
	public void testFindWishlistByValidId() {
		// Arrange
		this.validId = this.customer.getWishlist().getId();
		String url = "/wishlist/" + this.validId;

		// Act
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response = client.exchange(req, WishlistResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WishlistResponseDto wishlist = response.getBody();
		assertNotNull(wishlist);
		// Check that the wishlist found has the correct ID
		assertEquals(this.validId, wishlist.getId());
	}

	/**
	 * Test adding a game to wishlist.
	 * @author Julien Heng
	 */
	@Test
	@Order(2)
	public void testAddGameToWishlist() {
		// Start with an empty wishlist, add a game to the wishlist
		// Arrange
		this.validId = this.customer.getWishlist().getId();
		String url = "/wishlist/" + this.validId;

		// Act
		// Add the game ID (of the game to be added to wishlist) to the request body
		WishlistRequestDto body = new WishlistRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<WishlistRequestDto> wishlistRequestEntity = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		// PUT request
		ResponseEntity<WishlistResponseDto> response = client.exchange(url, HttpMethod.PUT, wishlistRequestEntity,
				WishlistResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WishlistResponseDto wishlist = response.getBody();
		assertNotNull(wishlist);
		// Check each game attribute
		assertEquals(this.game.getCategory(), wishlist.getGames().get(0).getCategory());
		assertEquals(this.game.getConsole(), wishlist.getGames().get(0).getConsole());
		assertEquals(this.game.getInventory(), wishlist.getGames().get(0).getInventory());
		assertEquals(this.game.getPrice(), wishlist.getGames().get(0).getPrice());
		assertEquals(this.game.getName(), wishlist.getGames().get(0).getName());
		assertEquals(this.game.getDescription(), wishlist.getGames().get(0).getDescription());
		assertEquals(this.game.getStatus(), wishlist.getGames().get(0).getStatus());
		assertEquals(this.game.getId(), wishlist.getGames().get(0).getId());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(3)
	public void testRemoveGameFromWishlistByInvalidId() {
		// Start with non-empty wishlist
		// Arrange
		this.validId = this.customer.getWishlist().getId();
		String url = "/wishlist/" + this.validId;
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response = client.exchange(req, WishlistResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WishlistResponseDto wishlist = response.getBody();
		assertNotNull(wishlist);
		List<GameResponseDto> games = wishlist.getGames();
		int wrongGameId = games.get(0).getId() + 1;

		// Act
		// Remove a game with an invalid ID
		RequestEntity<Void> reqEntity = RequestEntity.put(url + "?remove=" + wrongGameId)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response2 = client.exchange(reqEntity, WishlistResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(4)
	public void testRemoveGameFromWishlistByInvalidString() {
		// Start with non-empty wishlist
		// Arrange
		this.validId = this.customer.getWishlist().getId();
		String url = "/wishlist/" + this.validId;
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response = client.exchange(req, WishlistResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Act
		// Remove a game with an invalid ID
		RequestEntity<Void> reqEntity = RequestEntity.put(url + "?remove=" + "wrongstr")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response2 = client.exchange(reqEntity, WishlistResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(5)
	public void testRemoveGameFromWishlistByValidId() {
		// Start with non-empty wishlist
		// Arrange
		this.validId = this.customer.getWishlist().getId();
		String url = "/wishlist/" + this.validId;
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response = client.exchange(req, WishlistResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WishlistResponseDto wishlist = response.getBody();
		assertNotNull(wishlist);
		List<GameResponseDto> games = wishlist.getGames();
		int gameId = games.get(0).getId();

		// Act
		// Remove a game with an invalid ID
		RequestEntity<Void> reqEntity = RequestEntity.put(url + "?remove=" + gameId)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response2 = client.exchange(reqEntity, WishlistResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertNotNull(response2.getBody());
		WishlistResponseDto wishlist2 = response2.getBody();
		if (wishlist2 != null) {
			assertNotNull(wishlist2.getGames());
			assertEquals(0, wishlist2.getGames().size());
		}
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(6)
	public void testClearWishlist() {
		// Arrange

		// Start with empty wishlist
		this.validId = this.customer.getCart().getId();
		String url = "/wishlist/" + this.validId;

		// Add the game ID (of the game to be added to wishlist) to the request body
		WishlistRequestDto body = new WishlistRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<WishlistRequestDto> WishlistRequestEntity = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		// PUT request
		ResponseEntity<WishlistResponseDto> gameResponse = client.exchange(url, HttpMethod.PUT, WishlistRequestEntity,
				WishlistResponseDto.class);
		assertNotNull(gameResponse);

		// Act
		// Clear the cart
		RequestEntity<Void> reqEntity = RequestEntity.put(url + "?remove=all")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<WishlistResponseDto> response = client.exchange(reqEntity, WishlistResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		WishlistResponseDto cart = response.getBody();
		if (cart != null) {
			assertNotNull(cart.getGames());
			assertEquals(0, cart.getGames().size());
		}
	}

}
