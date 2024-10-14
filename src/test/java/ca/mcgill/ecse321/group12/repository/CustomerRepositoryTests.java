package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.model.Cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryTests {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private WishlistRepository wishlistRepository;
	@Autowired
	private CartRepository cartRepository;

	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
		wishlistRepository.deleteAll();
		cartRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		// Create customer
		String name = "Kennedy Olsen";
		String email = "k@gmail.com";
		String password = "i_love_muffins";
		String phoneNumber = "5141234567";
		Wishlist wishlist = new Wishlist();
		Cart cart = new Cart();
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		customer.setWishlist(wishlist);
		customer.setCart(cart);

		// Save wishlist and cart
		wishlist = wishlistRepository.save(wishlist);
		int wishlistId = wishlist.getId();
		cart = cartRepository.save(cart);
		int cartId = cart.getId();
		// Save customer
		customer = customerRepository.save(customer);
		int id = customer.getId();

		// Read customer from database
		Customer customerFromDb = customerRepository.findCustomerById(id);

		// Assert correct response
		assertNotNull(customer);
		assertEquals(customerFromDb.getName(), name);
		assertEquals(customerFromDb.getEmail(), email);
		assertEquals(customerFromDb.getPassword(), password);
		assertEquals(customerFromDb.getPhoneNumber(), phoneNumber);
		// check if wishlist is the same
		assertEquals(customerFromDb.getWishlist().getId(), wishlistId);
		assertEquals(customerFromDb.getCart().getId(), cartId);
	}
}