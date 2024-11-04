package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.group12.exception.CustomException;

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

}
