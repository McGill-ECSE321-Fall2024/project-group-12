package ca.mcgill.ecse321.group12.service;

/*
import java.util.ArrayList;
import java.util.List;
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.repository.CommentRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;

	/**
	 * Return the comment with the given ID.
	 * @param id The primary key of the comment to find.
	 * @return The comment with the given ID.
	 * @author Carmin Vidé
	 */
	public Comment findCommentById(int id) {
		Comment com = commentRepo.findCommentById(id);
		if (com == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no comment with ID " + id + ".");
		}
		return com;
	}

	/**
	 * Create a new comment.
	 * @param text The text of the new comment.
	 * @param review The review of the new comment.
	 * @return The newly created comment.
	 * @author Carmin Vidé
	 */
	@Transactional
	public Comment createComment(String text, Review review) {
		if (text == null || text.trim().length() == 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Comment text cannot be empty.");
		}
		Comment commentToCreate = new Comment();
		commentToCreate.setText(text);
		commentToCreate.setReview(review);
		return commentRepo.save(commentToCreate);
	}

	/**
	 * Delete the comment with the given ID.
	 * @param id The primary key of the comment to delete.
	 * @author Carmin Vidé
	 */
	@Transactional
	public void deleteCommentById(int id) {
		Comment commentToDelete = commentRepo.findCommentById(id);
		// throw a CustomException if no error is found
		if (commentToDelete == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no comment with ID " + id + ".");
		}
		commentRepo.delete(commentToDelete);
	}

	/**
	 * Find all comments
	 * @return A list of all comments
	 * @author Carmin Vidé
	 */
	@Transactional
	public Iterable<Comment> findAllComments() {
		return commentRepo.findAll();
	}

	/*
	 * /** Find comments by review
	 *
	 * @param review The review to search for
	 *
	 * @return A list of comments with the given review
	 */
	/*
	 * @Transactional public Iterable<Comment> findCommentsByReview(Review review) {
	 * List<Comment> comments = new ArrayList<Comment>(); for (Comment comment :
	 * commentRepo.findAll()) { if (comment.getReview().equals(review)) {
	 * ((ArrayList<Comment>) comments).add(comment); } } return comments; }
	 */

	/**
	 * Update the text of a comment.
	 * @param id The primary key of the comment to update.
	 * @param text The new text of the comment.
	 * @return The updated comment.
	 * @Author Carmin Vidé
	 */
	@Transactional
	public Comment updateCommentById(int id, String text) {
		Comment commentToUpdate = commentRepo.findCommentById(id);
		if (text == null || text.trim().length() == 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Comment text cannot be empty.");
		}
		commentToUpdate.setText(text);
		return commentRepo.save(commentToUpdate);
	}

}