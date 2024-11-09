package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.repository.OrderRepository;
import ca.mcgill.ecse321.group12.repository.ReviewRepository;
import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrderServiceIntegrationTests {
  
  @Autowired
  private TestRestTemplate client;

  @Autowired
  private OrderRepository orderRepo;
  @Autowired
  private CustomerRepository customerRepo;
  @Autowired
  private CartRepository cartRepo;
  @Autowired
  private GameRepository gameRepo;

  // variables from the setup
  private CustomerResponseDto customer;
  private List<GameResponseDto> gameDtos;

  /**
   * In order to create an order, a customer, cart, and games must be present in the database.
   * Add those in here.
   * @author James Madden
   */
  @BeforeAll
  public void setup () {
    
    // create a new customer to use their cart for the tests
    Customer customer = new Customer();
    CustomerRequestDto customerRequest = new CustomerRequestDto(customer);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customers", customerRequest, CustomerResponseDto.class);
    // save the response
    this.customer = customerResponse.getBody();
    
    // create games for use in tests
    GameRequestDto game1 = new GameRequestDto(
      Category.Action, 
      Console.Switch, 
      10, 
      10f, 
      "Action Game",
      "Action-packed game filled with action", 
      GameStatus.InCatalog
    );
    GameRequestDto game2 = new GameRequestDto(
      Category.Puzzle, 
      Console.XBox, 
      0, 
      60f, 
      "XBox Puzzle",
      "Can you solve it?", 
      GameStatus.InCatalog
    );
    GameRequestDto game3 = new GameRequestDto(
      Category.Adventure, 
      Console.PC, 
      100, 
      80f, 
      "Adventure VI",
      "Explore the world!", 
      GameStatus.InCatalog
    );
    GameRequestDto game4 = new GameRequestDto(
      Category.Sports, 
      Console.PlayStation, 
      0, 
      100f, 
      "Not Available",
      "This game shouldn't show up when browsing", 
      GameStatus.PendingApproval
    );
    // and post them to the database
    GameResponseDto gameResp1 = client.postForEntity("/games", game1, GameResponseDto.class).getBody();
    GameResponseDto gameResp2 = client.postForEntity("/games", game2, GameResponseDto.class).getBody();
    GameResponseDto gameResp3 = client.postForEntity("/games", game3, GameResponseDto.class).getBody();
    GameResponseDto gameResp4 = client.postForEntity("/games", game4, GameResponseDto.class).getBody();

    // save the game request dtos for reference later
    gameDtos = new ArrayList<GameResponseDto>();
    gameDtos.add(gameResp1);
    gameDtos.add(gameResp2);
    gameDtos.add(gameResp3);
    gameDtos.add(gameResp4);

  }

  /**
   * delete all the data from the database after the tests are run
   * @author James Madden
   */
  @AfterAll
  public void clearDb () {
    orderRepo.deleteAll();
    customerRepo.deleteAll();
    cartRepo.deleteAll();
    gameRepo.deleteAll();
  }

  /**
   * first step of the use cases: browse games.
   * see that GET /games successfully returns the games created in setup, and filters out
   * the game marked as awaiting approval.
   */
  @Test
  @Order(1)
  public void testBrowseGames() {

    // load all the games available for purchase
    ResponseEntity<List<GameResponseDto>> response = client.exchange("/games?status=InCatalog", HttpMethod.GET, null, new ParameterizedTypeReference<List<GameResponseDto>>() {});
    List<GameResponseDto> games = response.getBody();

    // check the request was successful and the games were returned
    assertNotNull(games);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    // check all the properties of each game
    assertEquals(gameDtos.get(0).getId(), games.get(0).getId());
    assertEquals(gameDtos.get(0).getName(), games.get(0).getName());
    assertEquals(gameDtos.get(0).getCategory(), games.get(0).getCategory());
    assertEquals(gameDtos.get(0).getConsole(), games.get(0).getConsole());
    assertEquals(gameDtos.get(0).getInventory(), games.get(0).getInventory());
    assertEquals(gameDtos.get(0).getPrice(), games.get(0).getPrice());
    assertEquals(gameDtos.get(0).getDescription(), games.get(0).getDescription());
    assertEquals(gameDtos.get(0).getStatus(), games.get(0).getStatus());

    assertEquals(gameDtos.get(1).getId(), games.get(1).getId());
    assertEquals(gameDtos.get(1).getName(), games.get(1).getName());
    assertEquals(gameDtos.get(1).getCategory(), games.get(1).getCategory());
    assertEquals(gameDtos.get(1).getConsole(), games.get(1).getConsole());
    assertEquals(gameDtos.get(1).getInventory(), games.get(1).getInventory());
    assertEquals(gameDtos.get(1).getPrice(), games.get(1).getPrice());
    assertEquals(gameDtos.get(1).getDescription(), games.get(1).getDescription());
    assertEquals(gameDtos.get(1).getStatus(), games.get(1).getStatus());

    
    assertEquals(gameDtos.get(2).getId(), games.get(2).getId());
    assertEquals(gameDtos.get(2).getName(), games.get(2).getName());
    assertEquals(gameDtos.get(2).getCategory(), games.get(2).getCategory());
    assertEquals(gameDtos.get(2).getConsole(), games.get(2).getConsole());
    assertEquals(gameDtos.get(2).getInventory(), games.get(2).getInventory());
    assertEquals(gameDtos.get(2).getPrice(), games.get(2).getPrice());
    assertEquals(gameDtos.get(2).getDescription(), games.get(2).getDescription());
    assertEquals(gameDtos.get(2).getStatus(), games.get(2).getStatus());

    // and then check the returned list was only 3 long (since the fourth is PendingApproval)
    assertEquals(3, games.size());

  }

  /**
   * step two: add games to cart
   * now that we've browsed games, we can add some to the cart so we can make a purchase.
   * @author James Madden
   */
  @Test
  @Order(2)
  public void testAddToCart () {

    // get the ID of the customer's cart
    int cartId = customer.getCart().getId();

    // add the two available in stock games to the cart
    CartRequestDto req1 = new CartRequestDto();
    CartRequestDto req2 = new CartRequestDto();
    req1.setGameId(gameDtos.get(0).getId());
    req2.setGameId(gameDtos.get(2).getId());

    // PUT both games into the cart
    RequestEntity<CartRequestDto> reqEntity1 = RequestEntity.put("/cart/" + cartId)
      .accept(MediaType.APPLICATION_JSON)
      .body(req1);
    RequestEntity<CartRequestDto> reqEntity2 = RequestEntity.put("/cart/" + cartId)
      .accept(MediaType.APPLICATION_JSON)
      .body(req2);
    ResponseEntity<CartResponseDto> resp1 = client.exchange("/cart/" + cartId, HttpMethod.PUT, reqEntity1, CartResponseDto.class);
    ResponseEntity<CartResponseDto> resp2 = client.exchange("/cart/" + cartId, HttpMethod.PUT, reqEntity2, CartResponseDto.class);

    // make sure both PUTs were successful
    assertNotNull(resp1);
    assertNotNull(resp2);
    assertEquals(HttpStatus.OK, resp1.getStatusCode());
    assertEquals(HttpStatus.OK, resp2.getStatusCode());

    // get the latest values for cart from resp2
    CartResponseDto cart = resp2.getBody();
    assertNotNull(cart);
    List<GameResponseDto> games = cart.getGames();
    // check each entry in the cart is correct
    assertEquals(gameDtos.get(0).getId(), games.get(0).getId());
    assertEquals(gameDtos.get(0).getName(), games.get(0).getName());
    assertEquals(gameDtos.get(0).getCategory(), games.get(0).getCategory());
    assertEquals(gameDtos.get(0).getConsole(), games.get(0).getConsole());
    assertEquals(gameDtos.get(0).getInventory(), games.get(0).getInventory());
    assertEquals(gameDtos.get(0).getPrice(), games.get(0).getPrice());
    assertEquals(gameDtos.get(0).getDescription(), games.get(0).getDescription());
    assertEquals(gameDtos.get(0).getStatus(), games.get(0).getStatus());

    assertEquals(gameDtos.get(2).getId(), games.get(1).getId());
    assertEquals(gameDtos.get(2).getName(), games.get(1).getName());
    assertEquals(gameDtos.get(2).getCategory(), games.get(1).getCategory());
    assertEquals(gameDtos.get(2).getConsole(), games.get(1).getConsole());
    assertEquals(gameDtos.get(2).getInventory(), games.get(1).getInventory());
    assertEquals(gameDtos.get(2).getPrice(), games.get(1).getPrice());
    assertEquals(gameDtos.get(2).getDescription(), games.get(1).getDescription());
    assertEquals(gameDtos.get(2).getStatus(), games.get(1).getStatus());

    // cart should be 2 long
    assertEquals(2, games.size());

  }

  /**
   * step three: place the order
   */

}
