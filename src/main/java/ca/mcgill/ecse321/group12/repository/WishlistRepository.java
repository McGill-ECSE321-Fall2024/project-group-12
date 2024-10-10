package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer>{

	Wishlist findWishlistById(int id);

}