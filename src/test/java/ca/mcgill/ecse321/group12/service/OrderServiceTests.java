package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
   * Test that an order can be successfully created
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
   * test that if a game is out of stock, the order will not be placed.
   * @author James Madden
   */
  @Test
  public void testFailToPlaceOrderOutOfStock () {

    // create a game
    Game game = new Game();
    game.setName("Game 1");
    game.setPrice(80);
    game.setInventory(0);
    game.setStatus(GameStatus.InCatalog);

    // create a cart
    Cart cart = new Cart();
    cart.addGame(game);

    // create a customer
    Customer customer = new Customer();
    customer.setCart(cart);

    // create a card payment
    CardPayment cardPayment = new CardPayment();

    // fields for the order
    String deliveryAddress = "680 Rue Sherbrooke";
    int purchaseTotal = 80;
    Date purchaseDate = new Date();
    OrderStatus orderStatus = OrderStatus.Delivered;
    List<Game> games = new ArrayList<Game>();
    games.add(game);

    // finally, create an order
    Order order = new Order();
    order.addGame(game);
    order.setCustomer(customer);
    order.setDeliveryAddress(deliveryAddress);
    order.setPurchaseTotal(purchaseTotal);
    order.setPurchaseDate(purchaseDate);
    order.setStatus(orderStatus);
    
    // check that an error is thrown
    CustomException e = assertThrows(CustomException.class, () -> service.createOrder(deliveryAddress, games, customer, cardPayment));
		assertEquals("Game " + game.getName() + " is out of stock.", e.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());



  }

}
