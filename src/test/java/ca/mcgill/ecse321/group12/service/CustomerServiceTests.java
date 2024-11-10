package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.exception.CustomException;

@SpringBootTest
public class CustomerServiceTests {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	@SuppressWarnings("null")

    /**
	 * Test creating a customer with valid arguments.
	 * @author Carmin Vidé
	 */
	@Test
	public void testCreateValidCustomer() {


		// create games
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();

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

        // create cart
        Cart cart = new Cart();
        cart.addGame(game1);
        cart.addGame(game2);

        // create wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.addGame(game3);


		// Arrange
		String name = "Cunégonde";
		String email = "cune@mail.mcgill.ca";
		String password = "yolo";
		String phoneNumber = "293092804";

		Customer customer = new Customer();

		customer.setEmail(email);
		customer.setPassword(password);
		customer.setName(name);
		customer.setPhoneNumber(phoneNumber);
        customer.setCart(cart);
        customer.setWishlist(wishlist);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		Customer createdCustomer = customerService.createCustomer(email, password, name, phoneNumber, wishlist, cart);

		// Assert
		assertNotNull(createdCustomer);
		assertEquals(name, createdCustomer.getName());
		assertEquals(email, createdCustomer.getEmail());
		assertEquals(password, createdCustomer.getPassword());
		assertEquals(phoneNumber, createdCustomer.getPhoneNumber());
        assertEquals(cart, createdCustomer.getCart());
        assertEquals(wishlist, createdCustomer.getWishlist());
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

    /**
	 * Test creating a customer with invalid email (email is already associated with a different account).
	 * @author Carmin Vidé
	 */
	@Test
	public void testCreateCustomerWithInvalidEmail() { 
														
		// Arrange
        // create games
        Game game1 = new Game();
        Game game2 = new Game();
        Game game3 = new Game();

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

        // create cart
        Cart cart = new Cart();
        cart.addGame(game1);
        cart.addGame(game2);

        // create wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.addGame(game3);


        // Arrange
        String name1 = "Cunégonde";
        String name2 = "Cunécule";
        String email = "cune@mail.mcgill.ca";
        String password1 = "yolo";
        String password2 = "yola";
        String phoneNumber1 = "293092804";
        String phoneNumber2 = "293092805";

        Customer customer = new Customer();

        customer.setEmail(email);
        customer.setPassword(password1);
        customer.setName(name1);
        customer.setPhoneNumber(phoneNumber1);
        customer.setCart(cart);
        customer.setWishlist(wishlist);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		customerService.createCustomer(email, password1, name1, phoneNumber1, wishlist, cart);

		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> customerService.createCustomer(email, password2, name2, phoneNumber2, wishlist, cart));
		assertEquals("Create customer failed. Customer with this email already exists in the system.", e.getMessage());
	}

    /**
	 * Test reading a customer by valid ID (customer exists in the system).
	 * @author Carmin Vidé
	 */    
	@Test
	public void testReadCustomerByValidId() {
		// Arrange
        // create games
        Game game1 = new Game();
        Game game2 = new Game();
        Game game3 = new Game();

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

        // create cart
        Cart cart = new Cart();
        cart.addGame(game1);
        cart.addGame(game2);

        // create wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.addGame(game3);
        

		int id = 42;
		Customer customer = new Customer();
		customer.setEmail("email@mail.mcgill.ca");
		customer.setName("goerger");
		customer.setPassword("123456");
		customer.setPhoneNumber("4898498498");
        customer.setCart(cart);
        customer.setWishlist(wishlist);
		when(customerRepository.findCustomerById(id)).thenReturn(customer);

		// Act
		Customer foundCustomer = customerService.findCustomerById(id);

		// Assert
		assertNotNull(customer);
		assertEquals(customer.getName(), foundCustomer.getName());
		assertEquals(customer.getEmail(), foundCustomer.getEmail());
		assertEquals(customer.getPassword(), foundCustomer.getPassword());
		assertEquals(customer.getPhoneNumber(), foundCustomer.getPhoneNumber());
        assertEquals(customer.getCart(), foundCustomer.getCart());
        assertEquals(customer.getWishlist(), foundCustomer.getWishlist());
	}


    /**
	 * Test reading a customer by invalid ID.
	 * @author Carmin Vidé
	 */  
	@Test
	public void testReadCustomerByInvalidId() {
		// Arrange
		int id = -1;
		when(customerRepository.findCustomerById(id)).thenReturn(null);

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> customerService.findCustomerById(id));
		assertEquals("There is no customer with ID " + id + ".", e.getMessage());
	}







/* 
    

	@Test
	public void testUpdateCustomerByValidArguments() {
		// Arrange
		int id = 42;
		Customer customer = new Customer();
		String email = "ea@mail.mcgill.ca";
		String name = "johnny";
		String password = "123456";
		String phoneNumber = "2041234567";

		customer.setId(id);
		customer.setEmail(email);
		customer.setName(name);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);

		String newEmail = "newemail@mail.mcgill.ca";
		String newName = "john";
		String newPassword = "123456";
		String newPhoneNumber = "2047654321";

		when(customerRepository.findCustomerById(id)).thenReturn(customer);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		customerService.createCustomer(email, password, name, phoneNumber);
		customerService.updateCustomerById(id, newEmail, newPassword, newName, newPhoneNumber);

		// Assert
		assertNotNull(customer);
		assertEquals(newEmail, customer.getEmail());
		assertEquals(newName, customer.getName());
		assertEquals(newPassword, customer.getPassword());
		assertEquals(newPhoneNumber, customer.getPhoneNumber());
	}

	@Test
	public void testUpdateCustomerByInvalidEmail() { // email is already associated with a
														// different account
		// Arrange
		int id = 42;
		Customer customer = new Customer();
		String name = "amy";
		String email = "hahaha@mail.mcgill.ca";
		String password = "12345678";
		String phoneNumber = "2041123455";

		String name2 = "jogn";
		String email2 = "lol@gmail.com";
		String password2 = "123";
		String phoneNumber2 = "123456";

		customer.setEmail(email);
		customer.setName(name);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		when(customerRepository.findCustomerById(id)).thenReturn(customer);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		customerService.createCustomer(email, password, name, phoneNumber);
		customerService.createCustomer(email2, password2, name2, phoneNumber2);

		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> customerService.updateCustomerById(id, email2, password, name, phoneNumber));
		assertEquals("Update customer failed. Customer with this email already exists in the system.", e.getMessage());
	}
*/
}
