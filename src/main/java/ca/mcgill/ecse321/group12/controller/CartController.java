package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.service.CartService;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * Return the cart with the given ID.
     *
     * @param cartId The primary key of the cart to find.
     * @return The cart with the given ID.
     */
    @GetMapping("/cart/{cartId}")
    public CartResponseDto findCartById(@PathVariable int cartId) {
        Cart cart = cartService.findCartById(cartId);
        return new CartResponseDto(cart);
    }
}
