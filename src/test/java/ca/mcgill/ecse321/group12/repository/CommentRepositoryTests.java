package ca.mcgill.ecse321.group12.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Review;

@SpringBootTest
public class CommentRepositoryTests {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private GameRepository gameRepository;

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
		Category category = Category.Action;
		Console console = Console.XBox;
		int inventory = 1;
		float price = 19.99f;
		String gameName = "FIFA";
		String description = "FIFA is a football game.";
		GameStatus status = GameStatus.InCatalog;
		Game game = new Game(0, category, console, inventory, price, gameName, description, status);
		game = gameRepository.save(game);

		// Create customer
		Customer customer = new Customer();
		customer = customerRepository.save(customer);

		// Create a review
		int likeCount = 15;
		int rating = 3;
		String reviewText = "Love this game. Makes me feel like I am in a different world. Graphics are pretty good. Pleasing to the eye.";
		Review aReview = new Review(0, likeCount, rating, reviewText, game, customer);
		aReview = reviewRepository.save(aReview);

		// Save Comment
		Comment comment = new Comment(0, text, aReview);
		comment = commentRepository.save(comment);

		// Read comment from database
		int id = comment.getId();
		Comment commentFromDb = commentRepository.findCommentById(id);

		// Assert correct response
		assertNotNull(comment);
		assertEquals(commentFromDb.getId(), id);
		assertEquals(commentFromDb.getText(), text);
		assertEquals(commentFromDb.getReview().getId(), aReview.getId());
		assertEquals(commentFromDb.getReview().getLikeCount(), aReview.getLikeCount());
		assertEquals(commentFromDb.getReview().getRating(), aReview.getRating());
		assertEquals(commentFromDb.getReview().getText(), aReview.getText());

	}

}