package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.CardPayment;

public interface CardPaymentRepository extends CrudRepository<CardPayment, Integer>{

	CardPayment findCardPaymentById(int id);

}