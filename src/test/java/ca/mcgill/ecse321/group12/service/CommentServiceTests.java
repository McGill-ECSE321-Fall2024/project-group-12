package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.repository.CommentRepository;

@SpringBootTest
public class CommentServiceTests {

	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	@SuppressWarnings("null")

	/**
	 * Test creating a comment with valid arguments.
     * @author Carmin Vidé
	 */
	@Test

	public void testCreateValidComment() {
		// Arrange
		String text = "Awesome game!";
		Review review = new Review();
		Comment comment = new Comment();
		comment.setText(text);
		comment.setReview(review);

		when(commentRepository.save(any(Comment.class))).thenReturn(comment);

		// Act
		Comment createdComment = commentService.createComment(text, review);

		// Assert
		assertNotNull(createdComment);
		assertEquals(text, createdComment.getText());
		assertEquals(review, createdComment.getReview());
		verify(commentRepository, times(1)).save(any(Comment.class));
	}

	/**
	 * Test creating a comment with invalid text.
	 * @author Carmin Vidé
	 */
	@Test
	public void testCreateCommentWithInvalidText() {
		// Arrange
		String text = null;
		Review review = new Review();

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> commentService.createComment(text, review));
		assertEquals("Comment text cannot be empty.", e.getMessage());
	}

	/**
	 * Test retrieving a comment by its id.
	 * @author Carmin Vidé
	 */
	@Test
	public void testFindCommentById() {
		// Arrange
		int id = 42;
		Comment comment = new Comment();
		comment.setText("this game kinda sucks ngl");
		comment.setReview(new Review());
		when(commentRepository.findCommentById(id)).thenReturn(comment);

		// Act
		Comment foundComment = commentService.findCommentById(id);

		// Assert
		assertNotNull(comment);
		assertEquals(comment.getText(), foundComment.getText());
		assertEquals(comment.getReview(), foundComment.getReview());
	}

	/**
	 * Test retrieving a comment with an invalid id.
	 * @author Carmin Vidé
	 */
	@Test
	public void testFindCommentByInvalidId() {
		// Arrange
		int id = -1;
		when(commentRepository.findCommentById(id)).thenReturn(null);

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> commentService.findCommentById(id));
		assertEquals("There is no comment with ID " + id + ".", e.getMessage());
	}

	/**
	 * Test updating a comment by its id.
	 * @author Carmin Vidé
	 */
	@Test
	public void testUpdateCommentByValidArguments() {
		// Arrange
		int id = 42;
		Comment comment = new Comment();
		String text = "pooja what is this behaviour";
		Review review = new Review();

		comment.setId(id);
		comment.setText(text);
		comment.setReview(review);

		String newText = "but you're asking for it. you're dying for it!";

		when(commentRepository.findCommentById(id)).thenReturn(comment);
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);

		// Act
		Comment updatedComment = commentService.updateCommentById(id, newText);

		// Assert
		assertNotNull(updatedComment);
		assertEquals(newText, updatedComment.getText());
		assertEquals(review, updatedComment.getReview());
	}

	/**
	 * Test updating a comment with an invalid text.
	 * @author Carmin Vidé
	 */
	@Test
	public void testUpdateCommentByInvalidText() {
		// Arrange
		int id = 42;
		Comment comment = new Comment();
		String text = "pooja what is this behaviour";
		Review review = new Review();

		comment.setId(id);
		comment.setText(text);
		comment.setReview(review);

		String newText = null;

		when(commentRepository.findCommentById(id)).thenReturn(comment);

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> commentService.createComment(newText, review));
		assertEquals("Comment text cannot be empty.", e.getMessage());
	}

	/**
	 * Test deleting a comment by its id.
	 * @author Carmin Vidé
	 */
	@Test
	public void testDeleteCommentById() {
		// Arrange
		int id = 42;
		Comment comment = new Comment();
		comment.setText("this game kinda sucks ngl");
		comment.setReview(new Review());
		when(commentRepository.findCommentById(id)).thenReturn(comment);

		// Act
		commentService.deleteCommentById(id);

		// Assert
		verify(commentRepository, times(1)).delete(comment);
	}

}
