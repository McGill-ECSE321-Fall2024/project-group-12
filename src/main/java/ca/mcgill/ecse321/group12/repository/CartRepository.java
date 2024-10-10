package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer>{

	Cart findCardPaymentById(int id);

}