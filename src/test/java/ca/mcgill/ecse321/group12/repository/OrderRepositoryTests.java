package ca.mcgill.ecse321.group12.repository;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Order;

@SpringBootTest
public class OrderRepositoryTests {
	@Autowired
	private OrderRepository orderRepository;

	@AfterEach
	public void clearDatabase() {
		orderRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadPerson() {
		// Create Order
        Date purchaseDate = new Date(10000);
        float purchaseTotal = 99.99f;
        String deliveryAddress = "home";

        Game[] games = new Game[1];
		games[0] = new Game();
        Customer customer = new Customer();
        CardPayment cardPayment = new CardPayment();

		Order order = new Order(0, purchaseDate, purchaseTotal, deliveryAddress, customer, cardPayment, games);

		// Save person
		order = orderRepository.save(order);
		int id = order.getId();

		// Read person from database
		Order orderFromDb = orderRepository.findOrderById(id);

		// Assert correct response
		assertNotNull(order);
		assertEquals(orderFromDb.getPurchaseDate(), purchaseDate);
		assertEquals(orderFromDb.getPurchaseTotal(), purchaseTotal);
		assertEquals(orderFromDb.getDeliveryAddress(), deliveryAddress);
		assertEquals(orderFromDb.getCustomer(), customer);
		assertEquals(orderFromDb.getGames(), games);
		assertEquals(orderFromDb.getCardPayment(), cardPayment);
	}
}