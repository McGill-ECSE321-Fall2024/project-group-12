package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.service.CartService;
import ca.mcgill.ecse321.group12.service.GameService;
import ca.mcgill.ecse321.group12.exception.CustomException;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private GameService gameService;
	/**
	 * Return the cart with the given ID.
	 * @param cartId The primary key of the cart to find.
	 * @return The cart with the given ID.
	 */
	@GetMapping("/cart/{cartId}")
	public CartResponseDto findCartById(@PathVariable int cartId) {
		Cart cart = cartService.findCartById(cartId);
		return new CartResponseDto(cart);
	}

	/**
	 * Add game to a cart with given ID.
	 * @param cartId The primary key of the cart to find.
	 * @return The cart with the given ID.
	 */
	@PutMapping("/cart/{cartId}")
	public CartResponseDto addGameToCart(@PathVariable int cartId, @RequestBody CartRequestDto cart) {
		try {
			cartService.findCartById(cartId);
		}
		catch (CustomException e) {
			cartService.createCart();
		}

		Cart thisCart = cartService.addGameToCart(cartId, cart.getGameId(), gameService);

		return new CartResponseDto(thisCart);
	}
}
