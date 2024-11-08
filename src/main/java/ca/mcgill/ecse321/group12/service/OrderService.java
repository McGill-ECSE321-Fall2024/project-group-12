package ca.mcgill.ecse321.group12.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	 * Creates a new order and adds it to the database
	 * @author James Madden
	 */
	@Transactional
	public Order createOrder(String deliveryAddress, List<Game> games, Customer customer, CardPayment cardPayment) {

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
		order.setPurchaseTotal(purchaseTotal);

		return repo.save(order);

	}

}
