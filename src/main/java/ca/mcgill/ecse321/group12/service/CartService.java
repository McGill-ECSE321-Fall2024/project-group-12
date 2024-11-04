package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	public Cart findCartById(int cartId) {
		Cart c = cartRepo.findCartById(cartId);
		if (c == null) {
			throw new IllegalArgumentException("There is no cart with ID " + cartId + ".");
		}
		return c;
	}

	@Transactional
	public Cart createCart() {
		Cart cartToCreate = new Cart();
		return cartRepo.save(cartToCreate);
	}

	/**
	 * removes all games from the cart
	 * @author James Madden
	 * @param cartId
	 */
	@Transactional
	public Cart clearCart(int cartId) {

		Cart cart = findCartById(cartId);

		// go through each game and take it out of the cart
		for (Game game : cart.getGames()) {
			cart.removeGame(game);
		}

		return cartRepo.save(cart);

	}

}
