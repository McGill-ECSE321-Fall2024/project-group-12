package ca.mcgill.ecse321.group12.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Review;

@SpringBootTest
public class CommentRepositoryTests {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ReviewRepository reviewRepository;

	@AfterEach
	public void clearDatabase() {
		commentRepository.deleteAll();
		reviewRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadComment() {

		// Create Comment
		String text = "you are beautiful";

        // Create Game
		Review review = new Review();
		review = reviewRepository.save(review);

		// Save Comment
		Comment comment = new Comment(0, text, review);
		comment = commentRepository.save(comment);

		// Read comment from database
		int id = comment.getId();
		Comment commentFromDb = commentRepository.findCommentById(id);

		// Assert correct response
		assertNotNull(comment);
		assertEquals(commentFromDb.getId(), id);
		assertEquals(commentFromDb.getText(), text);
		assertEquals(commentFromDb.getReview(), review);
	}
}