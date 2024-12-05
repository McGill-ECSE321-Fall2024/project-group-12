package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * Return the customer with the given ID.
	 * @param id The primary key of the customer to find.
	 * @return The customer with the given ID.
	 * @author Carmin Vidé
	 */
	public Customer findCustomerById(int id) {
		Customer cus = customerRepo.findCustomerById(id);
		if (cus == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + id + ".");
		}
		return cus;
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
	 * @author Carmin Vidé
	 */
	@Transactional
	public Customer createCustomer(String email, String password, String name, String phoneNumber, Wishlist wishlist,
			Cart cart, String address) {
		Customer customerToCreate = new Customer();
		customerToCreate.setEmail(email);
		customerToCreate.setPassword(password);
		customerToCreate.setName(name);
		customerToCreate.setPhoneNumber(phoneNumber);
		customerToCreate.setCart(cart);
		customerToCreate.setWishlist(wishlist);
		customerToCreate.setAddress(address);
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
	 * @author Carmin Vidé
	 */
	@Transactional
	public void deleteCustomerById(int id) {
		Customer customerToDelete = customerRepo.findCustomerById(id);
		// throw a CustomException if no error is found
		if (customerToDelete == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + id + ".");
		}
		customerRepo.delete(customerToDelete);
	}

	/**
	 * Find all customers
	 * @return A list of all customers
	 * @author Carmin Vidé
	 */
	@Transactional
	public Iterable<Customer> findAllCustomers() {
		return customerRepo.findAll();
	}

	/**
	 * Update customer with the given ID
	 * @param id The primary key of the customer to update.
	 * @param newEmail The new email of the customer.
	 * @param password The new password of the customer.
	 * @param name The new name of the customer.
	 * @param phoneNumber The new phone number of the customer.
	 * @param cart The new cart of the customer.
	 * @param wishlist The new wishlist of the customer.
	 * @return Updated customer
	 * @author Carmin Vidé
	 */
	@Transactional
	public Customer updateCustomerById(int id, String newEmail, String name, String phoneNumber, Wishlist wishlist,
			Cart cart, String address) {
		Customer customerToUpdate = customerRepo.findCustomerById(id);
		String previousEmail = customerToUpdate.getEmail();
		customerToUpdate.setEmail(newEmail);
		customerToUpdate.setName(name);
		customerToUpdate.setPhoneNumber(phoneNumber);
		customerToUpdate.setCart(cart);
		customerToUpdate.setWishlist(wishlist);
		customerToUpdate.setAddress(address);
		if (!previousEmail.equals(newEmail) && customerToUpdate.getEmail().equals(previousEmail)) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Update customer failed. Customer with this email already exists in the system.");
		}
		return customerToUpdate;
	}

	@Transactional
	public Customer updateCustomerAuth(int id, String email, String password, String newPassword) {

		Customer customerToUpdate = customerRepo.findCustomerById(id);
		if (customerToUpdate == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + id + ".");
		}
		if (!customerToUpdate.getEmail().equals(email)) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"The email provided does not match the email of the customer.");
		}
		if (!(BCrypt.checkpw(password, customerToUpdate.getPassword()))) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"The password provided does not match the password of the customer.");
		}
		customerToUpdate.setPassword(newPassword);
		return customerRepo.save(customerToUpdate);
	}

}