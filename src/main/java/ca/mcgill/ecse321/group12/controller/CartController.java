package ca.mcgill.ecse321.group12.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.service.CartService;
import ca.mcgill.ecse321.group12.service.GameService;

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
	@ResponseStatus(HttpStatus.OK)
	public CartResponseDto findCartById(@PathVariable int cartId) {
		Cart cart = cartService.findCartById(cartId);
		return new CartResponseDto(cart);
	}

	/**
	 * Modifies the cart (add, remove or clear) If no request parameters are passed, the
	 * gameId in the request body is added to the cart If a request parameter "remove" is
	 * passed with the value "all", all games are removed from the cart If a request
	 * parameter "remove" is passed with a gameId, the game with that gameId is removed
	 * from the cart Note: cart is always created with customer so we will always know the
	 * cardId and be able to PUT.
	 * @param cartId The primary key of the cart to find.
	 * @return The cart with the given ID.
	 */
	@CrossOrigin(origins = "http://localhost:5173") // Allow CORS for the frontend
	@PutMapping("/cart/{cartId}")
	@ResponseStatus(HttpStatus.OK)
	public CartResponseDto addGameToCart(@PathVariable int cartId, @RequestBody(required = false) CartRequestDto cart,
			@RequestParam(value = "remove") Optional<String> remove) {
		cartService.findCartById(cartId);

		// checks if the request parameter "remove" is passed
		if (!remove.isEmpty()) {

			// checks if the value of the request parameter "remove" is "all" or an
			// integer
			String param = remove.get();
			if (param.equals("all")) {
				// clears the cart
				Cart thisCart = cartService.clearCart(cartId);
				return new CartResponseDto(thisCart);
			}
			else {
				// removes the game with the gameId passed in the request parameter
				// "remove"
				try {
					int gameId = Integer.parseInt(param);
					Cart thisCart = cartService.removeGame(cartId, gameId, gameService);
					return new CartResponseDto(thisCart);
				}
				catch (NumberFormatException e) {
					// if the gameId is not an integer, throws an exception
					throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid game ID provided.");
				}

			}
		}
		else {

			if (cart == null) {
				throw new CustomException(HttpStatus.BAD_REQUEST, "No game ID provided.");
			}

			// adds the game with the gameId in the request body to the cart
			Cart thisCart = cartService.addGameToCart(cartId, cart.getGameId(), gameService);
			return new CartResponseDto(thisCart);
		}
	}

}
