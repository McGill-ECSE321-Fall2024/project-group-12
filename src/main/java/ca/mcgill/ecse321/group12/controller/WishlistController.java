package ca.mcgill.ecse321.group12.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.mcgill.ecse321.group12.dto.WishlistRequestDto;
import ca.mcgill.ecse321.group12.dto.WishlistResponseDto;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.service.GameService;
import ca.mcgill.ecse321.group12.service.WishlistService;

@Controller
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private GameService gameService;

	/**
	 * @param wishlistId The primary key of the wishlist to find.
	 * @return The wishlist with the given ID.
	 * @author Julien Heng
	 */
	@GetMapping("/wishlist/{wishlistId}")
	@ResponseStatus(HttpStatus.OK)
	public WishlistResponseDto findWishlistById(@PathVariable int wishlistId) {
		Wishlist wishlist = wishlistService.findWishlistById(wishlistId);
		return new WishlistResponseDto(wishlist);
	}

	/**
	 * Modifies the wishlist (add, remove or clear) If no request parameters are passed,
	 * the gameId in the request body is added to the wishlist If a request parameter
	 * "remove" is passed with the value "all", all games are removed from the wishlist If
	 * a request parameter "remove" is passed with a gameId, the game with that gameId is
	 * removed from the wishlist
	 * @param wishlistId The primary key of the cart to find.
	 * @return The cart with the given ID.
	 */
	@PutMapping("/wishlist/{wishlistId}")
	@ResponseStatus(HttpStatus.OK)
	public WishlistResponseDto addGameToCart(@PathVariable int wishlistId,
			@RequestBody(required = false) WishlistRequestDto wishlist,
            @RequestParam(value = "remove") Optional<String> remove) {

		wishlistService.findWishlistById(wishlistId);

		// checks if the request parameter "remove" is passed
		if (!remove.isEmpty()) {

			// checks if the value of the request parameter "remove" is "all" or an
			// integer
			String param = remove.get();
			if (param.equals("all")) {
				// clears the cart
				Wishlist thisWishlist = wishlistService.clearwWishlist(wishlistId);
				return new WishlistResponseDto(thisWishlist);
			}
			else {
				// removes the game with the gameId passed in the request parameter
				// "remove"
				try {
					int gameId = Integer.parseInt(param);
					Wishlist thisWishlist = wishlistService.removeGame(wishlistId, gameId, gameService);
					return new WishlistResponseDto(thisWishlist);
				}
				catch (NumberFormatException e) {
					// if the gameId is not an integer, throws an exception
					throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid game ID provided.");
				}
			}
		}
		else {

			if (wishlist == null) {
				throw new CustomException(HttpStatus.BAD_REQUEST, "No game ID provided.");
			}

			// adds the game with the gameId in the request body to the cart
			Wishlist thisWishlist = wishlistService.addGameToWishlist(wishlistId, wishlist.getGameId(), gameService);
			return new WishlistResponseDto(thisWishlist);
		}
	}

}
