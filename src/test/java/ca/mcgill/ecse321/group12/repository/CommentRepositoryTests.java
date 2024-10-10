package ca.mcgill.ecse321.group12.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.model.Wishlist;
@SpringBootTest
public class CommentRepositoryTests {
	@Autowired
	private CommentRepository commentRepository;

	@AfterEach
	public void clearDatabase() {
		commentRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadComment() {
		// Create employee
		// public Comment(int aId, String aText, Review aReview);

		int id = 1;
		String text = "you suck";

        Category category = Category.Action;
        Console console = Console.XBox;
        int inventory = 1;
        float price = 19.99f;
        String name = "FIFA" ;
        String description = "FIFA is a football game.";
        GameStatus status = GameStatus.InCatalog;
        
		Game game = new Game(0, category, console, inventory, price, name, description, status);
		String name1 = "Kennedy Olsen";
		String email = "kennedy@gmail.com";
		String password = "i_love_muffins";
		String phoneNumber = "5141234567";
		Wishlist wishlist = new Wishlist(0);
		Cart cart = new Cart(0);
		Customer customer = new Customer(0, email, password, name1, phoneNumber, wishlist, cart);

		Review review = new Review(1, 2, 3, "text", game, customer);

		Comment comment = new Comment(id, text, review);
		// Save person
		comment = commentRepository.save(comment);

		// Read person from database
		Comment commentFromDb = commentRepository.findCommentById(id);

		// Assert correct response
		assertNotNull(comment);
		assertEquals(commentFromDb.getId(), id);
		assertEquals(commentFromDb.getText(), text);
		assertEquals(commentFromDb.getReview(), review);
	}
}