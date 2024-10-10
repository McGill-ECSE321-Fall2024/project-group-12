package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.Review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewRepositoryTests {
	@Autowired
	private ReviewRepository reviewRepository;

	@AfterEach
	public void clearDatabase() {
		reviewRepository.deleteAll();
	}
	@Test
	public void testPersistAndLoadReview() {
		    // Create a review
        int likeCount = 15;
        int rating = 3;
        String text = "Love this game. Makes me feel like I am in a different world. Graphics are pretty good. Pleasing to the eye.";
        Review aReview = new Review();
        aReview.setLikeCount(likeCount);
        aReview.setRating(rating);
        aReview.setText(text);
  
		// Save review
        aReview = reviewRepository.save(aReview);
        int id = aReview.getId();

        // Read review from database
        Review reviewFromDb = reviewRepository.findReviewById(id);

        // Assert correct response 
        assertNotNull(aReview);
        assertEquals(reviewFromDb.getLikeCount(), likeCount);
        assertEquals(reviewFromDb.getRating(), rating);
        assertEquals(reviewFromDb.getText(), text);
	}
}