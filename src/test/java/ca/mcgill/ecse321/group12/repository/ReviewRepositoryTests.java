package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.Review;

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
        int text = "Love this game. Makes me feel like I am in a different world. Graphics are pretty good. Pleasing to the eye.";
        Review aReview = new Review();
        aReview.setLikeCount(likeCount);
        aReview.setRating(rating);
        aReview.setText(text);
  
		// Save review
        aReview = reviewRepository.save(review);
        int id = aReview.getId();

        // Read review from database
        Review reviewFromDb = reviewRepository.findReviewByI

		// Read person from database
		Person muffinManFromDb = personRepository.getWithId(id);

        // Assert correct response 
        assertNotNull(review);
        assertEquals(reviewFromDb.getLikeCount(), likeCount);
        assertEquals(reviewFromDb.getRating(), rating);
        assertEquals(reviewFromDb.getText(), text);
	}
}