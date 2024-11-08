package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	public Customer findCustomerById(int customerId) {
		Customer c = repo.findCustomerById(customerId);
		if (c == null) {
			throw new IllegalArgumentException("There is no customer with ID " + customerId + ".");
		}
		return c;
	}

}