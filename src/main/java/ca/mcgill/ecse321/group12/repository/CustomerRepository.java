package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	Customer findCustomerById(int id);

}