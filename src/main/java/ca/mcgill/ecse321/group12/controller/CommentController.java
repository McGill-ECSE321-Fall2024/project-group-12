package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	 */
	@GetMapping("/comments/{commentId}")
	public CommentResponseDto findCommentById(@PathVariable int commentId) {
		Comment comment = commentService.findCommentById(commentId);
		return new CommentResponseDto(comment);
	}

	/**
	 * Get all comments
	 * @return All comments.
	 */
	@GetMapping("/comments")
	public Iterable<Comment> findAllComments() {
		Iterable<Comment> allComments = commentService.findAllComments();
		return allComments;
	}

	/**
	 * Delete an comment.
	 * @param commentId The id of the comment to delete.
	 */
	@DeleteMapping("/comments/{commentId}")
	public void deleteCommentById(@PathVariable int commentId) {
		commentService.deleteCommentById(commentId);
	}

	/**
	 * Create a new comment.
	 * @param comment The comment to create.
	 * @return The created comment, including their ID.
	 */
	@PostMapping("/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentResponseDto createComment(@RequestBody CommentRequestDto comment) {
		Comment createdComment = commentService.createComment(comment.getText());
		return new CommentResponseDto(createdComment);
	}

}