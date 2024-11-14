package ca.mcgill.ecse321.group12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.repository.WishlistRepository;
import jakarta.transaction.Transactional;

@Service
public class WishlistService {

	@Autowired
	private WishlistRepository wishlistRepo;

	/**
	 * Return the wishlist with the given ID.
	 * @param id The primary key of the wishlist to find.
	 * @return The wishlist with the given ID.
	 */
	public Wishlist findWishlistById(int id) {
		Wishlist emp = wishlistRepo.findWishlistById(id);
		if (emp == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no wishlist with ID " + id + ".");
		}
		return emp;
	}

	/**
	 * Create a new wishlist.
	 * @return The newly created wishlist.
	 */
	@Transactional
	public Wishlist createWishlist() {
		Wishlist wishlistToCreate = new Wishlist();
		return wishlistRepo.save(wishlistToCreate);
	}

	/**
	 * Add game to whislist
	 * @return wishlist with the game added
	 * @author Julien Heng
	 */
	@Transactional
	public Wishlist addGameToWishlist(int wishlistId, int gameId, GameService gameService) {
		Wishlist wishlistToUpdate = findWishlistById(wishlistId);
		Game gameToAdd = gameService.findGameById(gameId);
		wishlistToUpdate.addGame(gameToAdd);
		return wishlistRepo.save(wishlistToUpdate);
	}

	/**
	 * removes all games from the wishlist
	 * @author Julien Heng
	 * @param wishlistId
	 */
	@Transactional
	public Wishlist clearWishlist(int wishlistId) {

		Wishlist wishlist = findWishlistById(wishlistId);

		List<Game> games = wishlist.getGames();

		// go through each game and take it out of the cart
		while (games.size() > 0) {
			wishlist.removeGame(games.get(0));
		}

		return wishlistRepo.save(wishlist);

	}

	/**
	 * Remove a game from the wishlist
	 * @return the updated wishlist
	 * @author Julien Heng
	 */
	@Transactional
	public Wishlist removeGame(int wishlistId, int gameId, GameService gameService) {
		Wishlist wishlist = findWishlistById(wishlistId);
		Game gameToRemove = gameService.findGameById(gameId);
		if (!wishlist.removeGame(gameToRemove)) {
			throw new CustomException(HttpStatus.NOT_FOUND, "Game with ID " + gameId + " is not in the wishlist.");
		}
		return wishlistRepo.save(wishlist);
	}

}
