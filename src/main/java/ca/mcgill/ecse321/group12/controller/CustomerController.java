package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.service.CartService;
import ca.mcgill.ecse321.group12.service.CustomerService;
import ca.mcgill.ecse321.group12.service.WishlistService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;

	@Autowired
	private WishlistService wishlistService;

	/**
	 * Return the customer with the given ID.
	 * @param customerId The primary key of the customer to find.
	 * @return The customer with the given ID.
	 */
	@GetMapping("/customers/{customerId}")
	public CustomerResponseDto findCustomerById(@PathVariable int customerId) {
		Customer customer = customerService.findCustomerById(customerId);
		return new CustomerResponseDto(customer);
	}

	/**
	 * Get all customers
	 * @return All customers.
	 */
	@GetMapping("/customers")
	public Iterable<Customer> findAllCustomers() {
		Iterable<Customer> allCustomers = customerService.findAllCustomers();
		return allCustomers;
	}

	/**
	 * Delete an customer.
	 * @param customer The customer to delete.
	 */
	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomerById(@PathVariable int eid) {
		customerService.deleteCustomerById(eid);
	}

	/**
	 * Create a new customer.
	 * @param customer The customer to create.
	 * @return The created customer, including their ID.
	 */
	@PostMapping("/customers")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerResponseDto createPerson(@RequestBody CustomerRequestDto customer) {
		Wishlist wishlist = wishlistService.createWishlist();
		Cart cart = cartService.createCart();
		Customer createdCustomer = customerService.createCustomer(customer.getEmail(), customer.getPassword(),
				customer.getName(), customer.getPhoneNumber(), wishlist, cart);
		return new CustomerResponseDto(createdCustomer);
	}

}
