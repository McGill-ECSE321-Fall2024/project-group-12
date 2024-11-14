package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Review;
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
		String email = "cunelaplusbelle@mail.mcgill.ca";
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
	 * Test creating a customer with invalid email (email is already associated with a
	 * different account).
	 * @author Carmin Vidé
	 */
	@Test
	public void testCreateCustomerWithInvalidEmail() {
		// Arrange
		String name = "Cunégonde";
		String email = null;
		String password = "12345678";
		String phoneNumber = "902332908";
		Cart cart = new Cart();
		Wishlist wishlist = new Wishlist();

		Customer customer = new Customer();

		customer.setEmail(email);
		customer.setPassword(password);
		customer.setName(name);
		customer.setPhoneNumber(phoneNumber);
		customer.setCart(cart);
		customer.setWishlist(wishlist);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> customerService.createCustomer(email, password, name, phoneNumber, wishlist, cart));

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

	/**
	 * Test updating a customer by valid arguments (customer exists in the system).
	 * @author Carmin Vidé
	 */
	@Test
	public void testUpdateCustomerByValidArguments() {
		// Arrange
		int id = 42;
		Customer customer = new Customer();
		String email = "ceun@mail.mcgill.ca";
		String name = "Ceunïa";
		String password = "hI13J";
		String phoneNumber = "173890443";
		Cart cart = new Cart();
		Wishlist wishlist = new Wishlist();

		customer.setId(id);
		customer.setEmail(email);
		customer.setName(name);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		customer.setCart(cart);
		customer.setWishlist(wishlist);

		String newEmail = "newCeun@mail.mcgill.ca";
		String newName = "Qeunïa";
		String newPassword = "hduçéh!'çh";
		String newPhoneNumber = "2892465320";
		Cart newCart = new Cart();
		Wishlist newWishlist = new Wishlist();

		when(customerRepository.findCustomerById(id)).thenReturn(customer);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		customerService.createCustomer(email, password, name, phoneNumber, wishlist, cart);
		customerService.updateCustomerById(id, newEmail, newPassword, newName, newPhoneNumber, newWishlist, newCart);

		// Assert
		assertNotNull(customer);
		assertEquals(newEmail, customer.getEmail());
		assertEquals(newName, customer.getName());
		assertEquals(newPassword, customer.getPassword());
		assertEquals(newPhoneNumber, customer.getPhoneNumber());
		assertEquals(newCart, customer.getCart());
	}

	/**
	 * Test updating a customer by invalid email (email is already associated with a
	 * different account).
	 * @author Carmin Vidé
	 */
	@Test
	public void testUpdateCustomerByInvalidEmail() {

		// Arrange
		int id = 42;
		Customer customer = new Customer();
		String name = "Cuivêtre";
		String email = "Cuivre@mail.mcgill.ca";
		String password = "OHHITHERE";
		String phoneNumber = "870426921";
		Cart cart = new Cart();
		Wishlist wishlist = new Wishlist();

		customer.setEmail(email);
		customer.setName(name);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		customer.setCart(cart);
		customer.setWishlist(wishlist);
		when(customerRepository.findCustomerById(id)).thenReturn(customer);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		// Act
		customerService.createCustomer(email, password, name, phoneNumber, wishlist, cart);

		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> customerService.updateCustomerById(id, null, password, name, phoneNumber, wishlist, cart));
		assertEquals("Update customer failed. Customer with this email already exists in the system.", e.getMessage());
	}

	/**
	 * Test finding all customers
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testFindAllCustomers() {
		// Arrange
		Customer customer = new Customer();
		String address = "my house";
		String email = "lmao@gmail.fr";
		String name = "help";
		String password = "OHHITHERE123";
		String phoneNumber = "870426921123";
		Cart cart = new Cart();
		Wishlist wishlist = new Wishlist();
		customer.setCart(cart);
		customer.setEmail(email);
		customer.setAddress(address);
		customer.setName(name);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		customer.setWishlist(wishlist);

		List<Customer> customers = Arrays.asList(customer);

		when(customerRepository.findAll()).thenReturn(customers);

		// Act
		List<Customer> foundCustomers = (List<Customer>) customerService.findAllCustomers();
		// Assert
		assertEquals(email, foundCustomers.get(0).getEmail());
		assertEquals(name, foundCustomers.get(0).getName());
		assertEquals(cart, foundCustomers.get(0).getCart());
		assertEquals(address, foundCustomers.get(0).getAddress());
		assertEquals(password, foundCustomers.get(0).getPassword());
		assertEquals(phoneNumber, foundCustomers.get(0).getPhoneNumber());
		assertEquals(wishlist, foundCustomers.get(0).getWishlist());
	}

}
