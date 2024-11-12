package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Comment;
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
	 */
	public Comment findCommentById(int id) {
		Comment emp = commentRepo.findCommentById(id);
		if (emp == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no comment with ID " + id + ".");
		}
		return emp;
	}

	/**
	 * Create a new comment.
	 * @param text The text of the new comment.
	 * @return The newly created comment.
	 */
	@Transactional
	public Comment createComment(String text) {
		Comment commentToCreate = new Comment();
		commentToCreate.setText(text);
		return commentRepo.save(commentToCreate);
	}

	/**
	 * Delete the comment with the given ID.
	 * @param id The primary key of the comment to delete.
	 */
	@Transactional
	public void deleteCommentById(int id) {
		Comment commentToDelete = commentRepo.findCommentById(id);
		commentRepo.delete(commentToDelete);
	}

	/**
	 * Find all comments
	 * @return A list of all comments
	 */
	@Transactional
	public Iterable<Comment> findAllComments() {
		return commentRepo.findAll();
	}

}