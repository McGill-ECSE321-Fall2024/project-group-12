package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;

@SpringBootTest
public class CartServiceTests {

	@Mock
	private CartRepository repo;

	@Mock
	private GameRepository gameRepo;

	@InjectMocks
	private CartService service;

	@InjectMocks
	private GameService gameService;

	@Test
	public void testCreateValidCart() {
		// Arrange
		when(repo.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// Act
		Cart createdCart = service.createCart();

		// Assert
		assertNotNull(createdCart);
		verify(repo, times(1)).save(createdCart);
	}

	@Test
	public void testFindCartByValidId() {
		// Arrange
		int id = 1;
		Cart thisCart = new Cart();
		when(repo.findCartById(id)).thenReturn(thisCart);

		// Act
		Cart cart = service.findCartById(id);

		// Assert
		assertNotNull(cart);
	}

	@Test
	public void testFindCartByInvalidId() {
		// Arrange
		int id = 42;
		when(repo.findCartById(id)).thenReturn(null);
		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> service.findCartById(id));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There is no cart with ID " + id + ".", e.getMessage());
	}

	@Test
	public void testAddGameToCart() {
		// Arrange
		int cartId = 1;

		int gameId = 40;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;
		Game game = new Game(gameId, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);

		Cart cart = new Cart();
		when(gameRepo.findGameById(gameId)).thenReturn(game);
		cart.addGame(gameService.findGameById(gameId));

		when(repo.findCartById(cartId)).thenReturn(cart);
		when(repo.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// Act
		Cart updatedCart = service.addGameToCart(cartId, gameId, gameService);

		// Assert
		assertNotNull(updatedCart);
		assertEquals(cart.getGames(), updatedCart.getGames());
	}

}
