package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Comment;
import ca.mcgill.ecse321.group12.model.Review;

public class CommentRequestDto {

	private String text;

	private Review review;

	@SuppressWarnings("unused")
	private CommentRequestDto() {
	}

	/**
	 * Constructor
	 * @author Carmin Vidé
	 * @param text The text for the comment being created
	 * @param review The review for the comment being created
	 */
	public CommentRequestDto(String text, Review review) {
		this.text = text;
		this.review = review;
	}

	public String getText() {
		return text;
	}

	public Review getReview() {
		return review;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
