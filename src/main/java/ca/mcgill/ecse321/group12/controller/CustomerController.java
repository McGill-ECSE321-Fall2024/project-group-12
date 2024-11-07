package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Return the customer with the given ID.
	 * @param eid The primary key of the customer to find.
	 * @return The empllyee with the given ID.
	 */
	@GetMapping("/customer/{eid}")
	public CustomerResponseDto findCustomerById(@PathVariable int eid) {
		Customer customer = customerService.findCustomerById(eid);
		return new CustomerResponseDto(customer);
	}

	/**
	 * Get all customers
	 * @return All customers.
	 */
	@GetMapping("/customer/all")
	public Iterable<Customer> findAllCustomers() {
		Iterable<Customer> allCustomers = customerService.findAllCustomers();
		return allCustomers;
	}

	/**
	 * Delete an customer.
	 * @param customer The customer to delete.
	 */
	@DeleteMapping("/customer/{eid}")
	public void deleteCustomerById(@PathVariable int eid) {
		customerService.deleteCustomerById(eid);
	}

	/**
	 * Create a new customer.
	 * @param customer The customer to create.
	 * @return The created customer, including their ID.
	 */
	@PostMapping("/customer")
	public CustomerResponseDto createPerson(@RequestBody CustomerRequestDto customer) {
		Customer createdCustomer = customerService.createCustomer(customer.getEmail(), customer.getPassword(),
				customer.getName(), customer.getPhoneNumber());
		return new CustomerResponseDto(createdCustomer);
	}
}
