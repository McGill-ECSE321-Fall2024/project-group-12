package ca.mcgill.ecse321.group12.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

	Order findOrderById(int id);
	List<Order> findByCustomer(int customerId);

}