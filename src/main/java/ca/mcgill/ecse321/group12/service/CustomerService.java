package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Cart;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * Return the customer with the given ID.
	 * @param id The primary key of the customer to find.
	 * @return The customer with the given ID.
	 */
	public Customer findCustomerById(int id) {
		Customer emp = customerRepo.findCustomerById(id);
		if (emp == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + id + ".");
		}
		return emp;
	}

	/**
	 * Create a new customer.
	 * @param email The email of the new customer.
	 * @param password The password of the new customer.
	 * @param name The name of the new customer.
	 * @param phoneNumber The phoneNumber of the new customer.
	 * @param cart The cart of the new customer.
	 * @param wishlist The wishlist of the new customer.
	 * @return The newly created customer.
	 */
	@Transactional
	public Customer createCustomer(String email, String password, String name, String phoneNumber, Wishlist wishlist, Cart cart) {
		Customer customerToCreate = new Customer();
		customerToCreate.setEmail(email);
		customerToCreate.setPassword(password);
		customerToCreate.setName(name);
		customerToCreate.setPhoneNumber(phoneNumber);
		customerToCreate.setCart(cart);
		customerToCreate.setWishlist(wishlist);
		Customer savedCustomer = customerRepo.save(customerToCreate);
		if (savedCustomer.getEmail() == null) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Create customer failed. Customer with this email already exists in the system.");
		}
		return savedCustomer;
	}

	/**
	 * Delete the customer with the given ID.
	 * @param id The primary key of the customer to delete.
	 */
	@Transactional
	public void deleteCustomerById(int id) {
		Customer customerToDelete = customerRepo.findCustomerById(id);
		customerRepo.delete(customerToDelete);
	}

	/**
	 * Find all customers
	 * @return A list of all customers
	 */
	@Transactional
	public Iterable<Customer> findAllCustomers() {
		return customerRepo.findAll();
	}

	/**
	 * Update customer with the given ID
	 * @return Updated customer
	 */
	@Transactional
	public Customer updateCustomerById(int id, String newEmail, String password, String name, String phoneNumber, Wishlist wishlist, Cart cart) {
		Customer customerToUpdate = customerRepo.findCustomerById(id);
		String previousEmail = customerToUpdate.getEmail();
		customerToUpdate.setEmail(newEmail);
		customerToUpdate.setName(name);
		customerToUpdate.setPassword(password);
		customerToUpdate.setPhoneNumber(phoneNumber);
		customerToUpdate.setCart(cart);
		customerToUpdate.setWishlist(wishlist);
		if (!previousEmail.equals(newEmail) && customerToUpdate.getEmail().equals(previousEmail)) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Update customer failed. Customer with this email already exists in the system.");
		}
		return customerToUpdate;
	}

}