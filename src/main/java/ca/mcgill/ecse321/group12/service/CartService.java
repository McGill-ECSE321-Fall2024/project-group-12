package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Game;
import java.util.List;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	/**
	 * Find a cart by its ID
	 * @param cartId
	 * @return Cart
	 */
	public Cart findCartById(int cartId) {
		Cart c = cartRepo.findCartById(cartId);
		if (c == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no cart with ID " + cartId + ".");
		}
		return c;
	}

	/**
	 * Create a cart
	 * @return Cart
	 */
	@Transactional
	public Cart createCart() {
		Cart cartToCreate = new Cart();
		return cartRepo.save(cartToCreate);
	}

	/**
	 * Create a cart
	 * @return Cart
	 */
	@Transactional
	public Cart createCart(int cartId) {
		Cart cartToCreate = new Cart(cartId);
		return cartRepo.save(cartToCreate);
	}

	/**
	 * Add game to cart
	 * @return Cart
	 */
	@Transactional
	public Cart addGameToCart(int cartId, int gameId, GameService gameService) {
		Cart cartToUpdate = findCartById(cartId);
		Game gameToAdd = gameService.findGameById(gameId);
		cartToUpdate.addGame(gameToAdd);
		return cartRepo.save(cartToUpdate);
	}

	/**
	 * removes all games from the cart
	 * @author James Madden
	 * @param cartId
	 */
	@Transactional
	public Cart clearCart(int cartId) {

		Cart cart = findCartById(cartId);

		List<Game> games = cart.getGames();

		// go through each game and take it out of the cart
		while (games.size() > 0) {
			cart.removeGame(games.get(0));
		}

		return cartRepo.save(cart);

	}

}
