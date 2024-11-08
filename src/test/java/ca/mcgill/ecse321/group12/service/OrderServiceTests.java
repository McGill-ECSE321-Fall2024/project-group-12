package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Order;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Order.OrderStatus;
import ca.mcgill.ecse321.group12.repository.OrderRepository;

@SpringBootTest
public class OrderServiceTests {
  
  @Mock
	private OrderRepository orderRepo;

	@InjectMocks
	private OrderService service;

  /**
   * Test that an order can be successfully created.
   * Error for games being out of stock is tested seperately in integration and GameService tests.
   * @author James Madden
   */
  @Test
  public void testCreateValidOrder () {

    when(orderRepo.save(any(Order.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

    // create games
    Game game1 = new Game();
    Game game2 = new Game();
    Game game3 = new Game();

    // only set the fields on game relevant to an order, the rest are tested elsewhere
    game1.setInventory(100);
    game1.setPrice(20);
    game1.setStatus(GameStatus.InCatalog);
    game1.setName("Game 1");

    game2.setPrice(50);
    game2.setStatus(GameStatus.InCatalog);
    game2.setInventory(10);
    game2.setName("Game 2");

    game3.setInventory(50);
    game3.setPrice(30);
    game3.setStatus(GameStatus.InCatalog);
    game3.setName("Game 3");

    // create a cart
    Cart cart = new Cart();
    cart.addGame(game1);
    cart.addGame(game2);
    cart.addGame(game3);

    // create a customer
    Customer customer = new Customer();
    customer.setCart(cart);

    // create a card payment
    CardPayment cardPayment = new CardPayment();

    // fields for the order
    String deliveryAddress = "680 Rue Sherbrooke";
    int purchaseTotal = 100;
    Date purchaseDate = new Date();
    OrderStatus orderStatus = OrderStatus.Delivered;
    List<Game> games = new ArrayList<Game>();
    games.add(game1);
    games.add(game2);
    games.add(game3);

    // finally, create an order
    Order order = new Order();
    order.addGame(game1);
    order.addGame(game2);
    order.addGame(game3);
    order.setCustomer(customer);
    order.setDeliveryAddress(deliveryAddress);
    order.setPurchaseTotal(purchaseTotal);
    order.setPurchaseDate(purchaseDate);
    order.setStatus(orderStatus);
    
    Order createdOrder = service.createOrder(deliveryAddress, games, customer, cardPayment);

    // check the fields match
    assertNotNull(createdOrder);
    assertEquals(customer, createdOrder.getCustomer());
    assertEquals(deliveryAddress, createdOrder.getDeliveryAddress());
    assertEquals(purchaseTotal, createdOrder.getPurchaseTotal());
    assertEquals(purchaseDate, createdOrder.getPurchaseDate());
    assertEquals(orderStatus, createdOrder.getStatus());
    assertEquals(games, createdOrder.getGames());
    assertEquals(cardPayment, createdOrder.getCardPayment());

  }

  /**
   * test if an order can be retrieved by its ID.
   * @author James Madden
   */
  @Test
  public void testFindOrderByValidId () {

    // create an order
    Order order = new Order();
    order.setDeliveryAddress("3480 Rue University");
    order.setPurchaseDate(new Date());
    order.setPurchaseTotal(62);
    order.setStatus(OrderStatus.Delivered);

    Customer customer = new Customer();
    order.setCustomer(customer);

    Game game = new Game();
    order.addGame(game);

    int id = order.getId();
    when(orderRepo.findOrderById(id)).thenReturn(order);

    // find the order
    Order retrievedOrder = service.findOrderById(id);

    // check that the properties were preserved
    assertNotNull(retrievedOrder);
    assertEquals(order.getDeliveryAddress(), retrievedOrder.getDeliveryAddress());
    assertEquals(order.getPurchaseDate(), retrievedOrder.getPurchaseDate());
    assertEquals(order.getPurchaseTotal(), retrievedOrder.getPurchaseTotal());
    assertEquals(order.getStatus(), retrievedOrder.getStatus());
    assertEquals(order.getCustomer(), retrievedOrder.getCustomer());
    assertEquals(order.getGames(), retrievedOrder.getGames());

  }

  /**
   * test that getting an order fails when invalid ID is provided
   * @author James Madden
   */
  @Test
  public void testFindOrderByInvalidId () {

    int id = 0;
    when(orderRepo.findOrderById(id)).thenReturn(null);

    CustomException error = assertThrows(CustomException.class, () -> service.findOrderById(id));
    assertEquals("There is no order with ID " + id + ".", error.getMessage());

  }

}
