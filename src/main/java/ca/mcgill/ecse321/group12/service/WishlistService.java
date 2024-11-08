package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.repository.WishlistRepository;
import jakarta.transaction.Transactional;

@Service
public class WishlistService {

	@Autowired
	private WishlistRepository wishlistRepo;

	/**
	 * Return the wishlist with the given ID.
	 * @param id The primary key of the wishlist to find.
	 * @return The wishlist with the given ID.
	 */
	public Wishlist findWishlistById(int id) {
		Wishlist emp = wishlistRepo.findWishlistById(id);
		if (emp == null) {
			throw new IllegalArgumentException("There is no wishlist with ID " + id + ".");
		}
		return emp;
	}

	/**
	 * Create a new wishlist.
	 * @return The newly created wishlist.
	 */
	@Transactional
	public Wishlist createWishlist() {
		Wishlist wishlistToCreate = new Wishlist();
		return wishlistRepo.save(wishlistToCreate);
	}
}
