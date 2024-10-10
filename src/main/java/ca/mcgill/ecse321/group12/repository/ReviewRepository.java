package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	Review getWithId(int aId);  
}