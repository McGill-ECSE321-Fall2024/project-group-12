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

	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		// Create customer
		String name = "Kennedy Olsen";
		String email = "kennedy@gmail.com";
		String password = "i_love_muffins";
		String phoneNumber = "5141234567";
		Wishlist wishlist = new Wishlist(0);
		Cart cart = new Cart(0);
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setPhoneNumber(phoneNumber);
		customer.setWishlist(wishlist);
		customer.setCart(cart);

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
		assertEquals(customerFromDb.getWishlist(), wishlist);
		assertEquals(customerFromDb.getCart(), cart);
	}
}