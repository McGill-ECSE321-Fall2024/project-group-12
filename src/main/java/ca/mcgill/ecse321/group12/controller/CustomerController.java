package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerCreateResponseDto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.service.CartService;
import ca.mcgill.ecse321.group12.service.CustomerService;
import ca.mcgill.ecse321.group12.service.WishlistService;
import ca.mcgill.ecse321.group12.service.AuthService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private AuthService authService;

	/**
	 * Return the customer with the given ID.
	 * @param customerId The primary key of the customer to find.
	 * @return The customer with the given ID.
	 * @author Carmin Vidé
	 */
	@GetMapping("/customers/{customerId}")
	public CustomerResponseDto findCustomerById(@PathVariable int customerId) {
		Customer customer = customerService.findCustomerById(customerId);
		return new CustomerResponseDto(customer);
	}

	/**
	 * Get all customers
	 * @return All customers.
	 * @author Carmin Vidé
	 */
	@GetMapping("/customers")
	public Iterable<Customer> findAllCustomers() {
		Iterable<Customer> allCustomers = customerService.findAllCustomers();
		return allCustomers;
	}

	/**
	 * Delete a customer.
	 * @param customerId The primary key of the customer to delete.
	 * @author Carmin Vidé
	 */
	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomerById(@PathVariable int customerId) {
		customerService.deleteCustomerById(customerId);
	}

	/**
	 * Create a new customer.
	 * @param customer The customer to create.
	 * @return The created customer, including their ID and an auth token.
	 * @author Carmin Vidé
	 */
	@PostMapping("/customers")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerCreateResponseDto createPerson(@RequestBody CustomerRequestDto customer) {
		Wishlist wishlist = wishlistService.createWishlist();
		Cart cart = cartService.createCart();

		// encrypt the password for security
		String encryptedPassword = new BCryptPasswordEncoder().encode(customer.getPassword());

		Customer createdCustomer = customerService.createCustomer(customer.getEmail(), encryptedPassword,
				customer.getName(), customer.getPhoneNumber(), wishlist, cart);
		CustomerCreateResponseDto response = new CustomerCreateResponseDto(createdCustomer);

		// get an auth token for this customer
		String token = authService.generateAuthToken(createdCustomer);
		response.setToken(token);

		return response;
	}

	/**
	 * Update a customer.
	 * @param customer The customer to update.
	 * @param customerId The primary key for the customer to be updated.
	 * @return The updated customer, including their ID.
	 * @author Carmin Vidé
	 */
	@PutMapping("/customers/{customerId}")
	@CrossOrigin(origins = "http://localhost:5173")
	public CustomerResponseDto updateCustomer(@PathVariable int customerId, 
	@RequestParam(value = "newPassword", required = false) String newPassword,
	@RequestBody(required = false) CustomerRequestDto customer) {
		// update password only
		if (newPassword != null && !newPassword.isEmpty()) {
			// load customer
			Customer existingCustomer = customerService.findCustomerById(customerId);

			// get their current attributes except for password
			String email = existingCustomer.getEmail();
			String name = existingCustomer.getName();
			String phoneNumber = existingCustomer.getPhoneNumber();
			Wishlist wishlist = existingCustomer.getWishlist();
			Cart cart = existingCustomer.getCart();

			// provide current password to the updated one below
			Customer updatedCustomer = customerService.updateCustomerById(customerId, email,
					newPassword, name, phoneNumber, wishlist,
					cart);
			return new CustomerResponseDto(updatedCustomer);
		}
		
		// update email, name, phone number
		if (customer != null) {
			// load customer
			Customer existingCustomer = customerService.findCustomerById(customerId);

			// get their current password
			String password = existingCustomer.getPassword();

			// provide current password to the updated one below
			Customer updatedCustomer = customerService.updateCustomerById(customerId, customer.getEmail(),
					password, customer.getName(), customer.getPhoneNumber(), customer.getWishlist(),
					customer.getCart());
			return new CustomerResponseDto(updatedCustomer);
		}
		return new CustomerResponseDto(new Customer());
	}
}