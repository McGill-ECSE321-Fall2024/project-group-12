package ca.mcgill.ecse321.group12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.CommentRequestDto;
import ca.mcgill.ecse321.group12.dto.CommentResponseDto;
import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * Return the comment with the given ID.
	 * @param commentId The primary key of the comment to find.
	 * @return The comment with the given ID.
	 * @author Carmin Vidé
	 */
	@GetMapping("/comments/{commentId}")
	public CommentResponseDto findCommentById(@PathVariable int commentId) {
		Comment comment = commentService.findCommentById(commentId);
		return new CommentResponseDto(comment);
	}

	/**
	 * return the comments on a specific review
	 * @param reviewId the review to find comments for
	 * @return the comments on the review
	 * @author James Madden
	 */
	@GetMapping("/reviews/{reviewId}/comments")
	public List<CommentResponseDto> findCommentsByReviewId(@PathVariable int reviewId) {
		// get comments and convert them to DTOs
		return commentService.findCommentsByReviewId(reviewId)
			.stream()
			.map(comment -> new CommentResponseDto(comment))
			.toList();
	}

	/**
	 * Get all comments
	 * @return All comments.
	 * @author Carmin Vidé
	 */
	@GetMapping("/comments")
	public Iterable<Comment> findAllComments() {
		Iterable<Comment> allComments = commentService.findAllComments();
		return allComments;
	}

	/**
	 * Delete an comment.
	 * @param commentId The id of the comment to delete.
	 * @author Carmin Vidé
	 */
	@DeleteMapping("/comments/{commentId}")
	public void deleteCommentById(@PathVariable int commentId) {
		commentService.deleteCommentById(commentId);
	}

	/**
	 * Create a new comment.
	 * @param comment The comment to create.
	 * @return The created comment, including their ID.
	 * @author Carmin Vidé
	 */
	@PostMapping("/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentResponseDto createComment(@RequestBody CommentRequestDto comment) {
		Comment createdComment = commentService.createComment(comment.getText(), comment.getReview());
		return new CommentResponseDto(createdComment);
	}

	/**
	 * Update an comment.
	 * @param comment The comment to update.
	 * @param eid The primary key for the comment to be updated.
	 * @return The updated comment, including their ID.
	 * @author Carmin Vidé
	 */
	@PutMapping("/comments/{eid}")
	public CommentResponseDto updateComment(@PathVariable int eid, @RequestBody CommentRequestDto comment) {
		Comment updatedComment = commentService.updateCommentById(eid, comment.getText());
		return new CommentResponseDto(updatedComment);
	}

}