package ca.mcgill.ecse321.group12.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Order;
import ca.mcgill.ecse321.group12.model.Order.OrderStatus;
import ca.mcgill.ecse321.group12.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;

	/**
	 * find and return an order from ID. Throw a CustomException if not found
	 * @author James Madden
	 */
	public Order findOrderById(int id) {

		Order order = repo.findOrderById(id);

		if (order == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no order with ID " + id + ".");
		}

		return order;

	}

	/**
	 * Creates a new order and adds it to the database
	 * @author James Madden
	 */
	@Transactional
	public Order createOrder(String deliveryAddress, List<Game> games, Customer customer, CardPayment cardPayment,
			Optional<String> discount) {

		// create the order
		Order order = new Order();
		order.setDeliveryAddress(deliveryAddress);
		order.setGames(games.toArray(new Game[0]));
		order.setCustomer(customer);
		order.setCardPayment(cardPayment);
		// order can be assumed to be delivered immediately
		order.setStatus(OrderStatus.Delivered);
		order.setPurchaseDate(new Date()); // current time

		// calculate purchase total
		float purchaseTotal = 0;
		for (Game game : games) {
			purchaseTotal += game.getPrice();
		}

		// apply discount if present
		if (!discount.isEmpty()) {
			float discountAmount = Float.parseFloat(discount.get());
			purchaseTotal *= 1f - discountAmount / 100f;
		}

		order.setPurchaseTotal(purchaseTotal);

		return repo.save(order);

	}

	/**
	 * Updates an order's status. Only allowed if the new status is returned.
	 * @author James Madden
	 */
	@Transactional
	public Order updateStatus(int id, OrderStatus status) {

		// make sure the status is being set to Returned
		if (status != OrderStatus.Returned) {
			throw new CustomException(HttpStatus.NOT_FOUND, "Order status cannot be updated to " + status + ".");
		}

		// find the order and set the status
		Order order = findOrderById(id);

		order.setStatus(status);

		return repo.save(order);

	}

}
