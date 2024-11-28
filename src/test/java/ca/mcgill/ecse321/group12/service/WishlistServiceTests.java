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
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.repository.WishlistRepository;

@SpringBootTest
public class WishlistServiceTests {

	@Mock
	private WishlistRepository repo;

	@Mock
	private GameRepository gameRepo;

	@InjectMocks
	private WishlistService service;

	@InjectMocks
	private GameService gameService;

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testCreateValidWishlist() {
		// Arrange
		when(repo.save(any(Wishlist.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// Act
		Wishlist createdwishlist = service.createWishlist();

		// Assert
		assertNotNull(createdwishlist);
		verify(repo, times(1)).save(createdwishlist);
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testFindWishlistByValidId() {
		// Arrange
		int id = 1;
		Wishlist thisWishlist = new Wishlist();
		when(repo.findWishlistById(id)).thenReturn(thisWishlist);

		// Act
		Wishlist wishlist = service.findWishlistById(id);

		// Assert
		assertNotNull(wishlist);
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testFindWishlistByInvalidId() {
		// Arrange
		int id = 42;
		when(repo.findWishlistById(id)).thenReturn(null);
		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> service.findWishlistById(id));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There is no wishlist with ID " + id + ".", e.getMessage());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	public void testAddGameToWishlist() {
		// Arrange
		int wishlistId = 1;

		int gameId = 400;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;
		int aYear = 2021;
		Game game = new Game(gameId, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus, aYear);

		Wishlist wishlist = new Wishlist();
		when(gameRepo.findGameById(gameId)).thenReturn(game);
		wishlist.addGame(gameService.findGameById(gameId));

		when(repo.findWishlistById(wishlistId)).thenReturn(wishlist);
		when(repo.save(any(Wishlist.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// Act
		Wishlist updatedWishlist = service.addGameToWishlist(wishlistId, gameId, gameService);

		// Assert
		assertNotNull(updatedWishlist);
		assertEquals(wishlist.getGames(), updatedWishlist.getGames());
	}

	/**
	 * Check that all games are successfully removed from the wishlist
	 * @author Julien Heng
	 */
	@Test
	public void testClearwishlist() {

		// create a wishlist
		Wishlist wishlist = new Wishlist();
		int id = wishlist.getId();

		// mock the saving and finding methods
		when(repo.save(any(Wishlist.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
		when(repo.findWishlistById(id)).thenReturn(wishlist);

		// add games to the wishlist
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();
		wishlist.addGame(game1);
		wishlist.addGame(game2);
		wishlist.addGame(game3);

		// make sure all the games were successfully added
		assertEquals(3, wishlist.getGames().size());

		// run the clear method
		Wishlist clearedwishlist = service.clearWishlist(id);

		// check that the returned wishlist has no games
		assertNotNull(clearedwishlist);
		assertEquals(0, clearedwishlist.getGames().size());

	}

	/**
	 * Check that a game is successfully removed from the wishlist
	 * @author Julien Heng
	 */
	@Test
	public void testRemoveGameFromWishlist() {

		// Arrange
		int wishlistId = 99;

		int gameId = 102;
		Category aCategory = Category.Action;
		Console aConsole = Console.PC;
		int aInventory = 1;
		float aPrice = 1.2f;
		String aName = "Game Name...";
		String aDescription = "Game Description...";
		GameStatus aStatus = GameStatus.Archived;
		int aYear = 2021;
		Game game = new Game(gameId, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus, aYear);

		Wishlist wishlist = new Wishlist();
		when(gameRepo.findGameById(gameId)).thenReturn(game);
		wishlist.addGame(gameService.findGameById(gameId));

		when(repo.findWishlistById(wishlistId)).thenReturn(wishlist);
		when(repo.save(any(Wishlist.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

		// Act
		Wishlist updatedWishlist = service.removeGame(wishlistId, gameId, gameService);

		// Assert
		assertNotNull(updatedWishlist);
		assertEquals(wishlist.getGames().size(), 0);

	}

}
