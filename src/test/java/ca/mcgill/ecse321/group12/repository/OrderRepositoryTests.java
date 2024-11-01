package ca.mcgill.ecse321.group12.repository;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Order;

@SpringBootTest
public class OrderRepositoryTests {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private CardPaymentRepository cardPaymentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	

	@AfterEach
	public void clearDatabase() {
		orderRepository.deleteAll();
		gameRepository.deleteAll();
		cardPaymentRepository.deleteAll();
		customerRepository.deleteAll();
	}

	@Test
  @Transactional
	public void testPersistAndLoadOrder() {
		// Create Order
    Date purchaseDate = new Date();
    float purchaseTotal = 99.99f;
    String deliveryAddress = "home";

		// Create games
		Category category = Category.Action;
		Console console = Console.XBox;
		int inventory = 1;
		float price = 19.99f;
		String name = "FIFA" ;
		String description = "FIFA is a football game.";
		GameStatus status = GameStatus.InCatalog;
		Game game = new Game();
		game.setCategory(category);
		game.setConsole(console);
		game.setInventory(inventory);
		game.setPrice(price);
		game.setName(name);
		game.setDescription(description);
		game.setStatus(status);
		game = gameRepository.save(game);
		Game[] games = { game };

		// Create customer
		Customer customer = new Customer();
		customer = customerRepository.save(customer);

		// Create cardPayment
    CardPayment cardPayment = new CardPayment();
		cardPayment = cardPaymentRepository.save(cardPayment);

		Order order = new Order();
		order.setPurchaseDate(purchaseDate);
		order.setPurchaseTotal(purchaseTotal);
		order.setDeliveryAddress(deliveryAddress);
		order.setGames(games);
		order.setCustomer(customer);
		order.setCardPayment(cardPayment);

		// Save order
		order = orderRepository.save(order);
		int id = order.getId();

		// Read order from database
		Order orderFromDb = orderRepository.findOrderById(id);

		// Assert correct response
		assertNotNull(order);
		assertEquals(orderFromDb.getPurchaseDate().getTime(), purchaseDate.getTime());
		assertEquals(orderFromDb.getPurchaseTotal(), purchaseTotal);
		assertEquals(orderFromDb.getDeliveryAddress(), deliveryAddress);
		assertEquals(orderFromDb.getCustomer().getId(), customer.getId());
		assertEquals(orderFromDb.getGame(0).getId(), game.getId());
		assertEquals(orderFromDb.getCardPayment().getId(), cardPayment.getId());
	}
}