package ca.mcgill.ecse321.group12.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Wishlist;

@SpringBootTest
public class WishlistRepositoryTests {
	@Autowired
	private WishlistRepository wishlistRepository;

	@AfterEach
	public void clearDatabase() {
		wishlistRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadWishlist() {
		// Create employee
        // int aId, String aEmail, String aPassword, String aName, String aPhoneNumber
        
        int id = 0;
		Wishlist wishlist = new Wishlist(id);

		// Save person
		wishlist = wishlistRepository.save(wishlist);

		// Read person from database
		Wishlist wishlistFromDb = wishlistRepository.findWishlistById(id);

		// Assert correct response
		assertNotNull(wishlist);
		assertEquals(wishlistFromDb.getId(), id);
	}
}