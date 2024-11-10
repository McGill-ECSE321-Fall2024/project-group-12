package ca.mcgill.ecse321.group12.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.OrderRequestDto;
import ca.mcgill.ecse321.group12.dto.OrderResponseDto;
import ca.mcgill.ecse321.group12.dto.OrderReturnRequestDto;
import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Order;
import ca.mcgill.ecse321.group12.service.CardPaymentService;
import ca.mcgill.ecse321.group12.service.CartService;
import ca.mcgill.ecse321.group12.service.CustomerService;
import ca.mcgill.ecse321.group12.service.GameService;
import ca.mcgill.ecse321.group12.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CardPaymentService cardPaymentService;

	@Autowired
	private GameService gameService;

	@Autowired
	private CustomerService customerService;

	/**
	 * Return an order by id
	 * @author James Madden
	 */
	@GetMapping("/orders/{id}")
	public OrderResponseDto getOrder(@PathVariable int id) {

		Order order = orderService.findOrderById(id);
		return new OrderResponseDto(order);

	}

	/**
	 * Create a new order, based on the games in the specified user's cart
	 *
	 * @author James Madden
	 * @param body
	 * @return
	 */
	@PostMapping("/orders")
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponseDto createOrder(@Validated @RequestParam(value = "discount") Optional<String> discount,
			@RequestBody(required = true) OrderRequestDto body) {

		// #1: get customer
		Customer customer = customerService.findCustomerById(body.getCustomerId());

		// #2: get cart from customer
		Cart cart = customer.getCart();

		// #2: create user payment info
		CardPayment payment = cardPaymentService.createCardPayment(body.getNameOnCard(), body.getCvc(),
				body.getCardNumber(), body.getBillingAddress(), body.getIsSaved(), body.getExpiryDate());

		List<Game> games = cart.getGames();
		// #3: check all games in cart are in stock. if they are, subtract 1 from stock.
		// If not, order FAILS
		gameService.reduceGamesInventory(games);

		// #4: create order object
		Order order = orderService.createOrder(body.getDeliveryAddress(), games, customer, payment, discount);

		// #5: empty user's cart
		cartService.clearCart(customerService.findCustomerById(cart.getId());

		return new OrderResponseDto(order);

	}

	/**
	 * Set an order's status to returned and bring back the inventory of all the games
	 * @author James Madden
	 */
	@PutMapping("/orders/{id}")
	public OrderResponseDto returnOrder(@Validated @RequestBody OrderReturnRequestDto body, @PathVariable int id) {

		// update the order status to returned
		Order order = orderService.updateStatus(id, body.getStatus());

		// update the inventory for each game
		gameService.returnGames(order.getGames());

		// return the modified order
		return new OrderResponseDto(order);

	}

}
