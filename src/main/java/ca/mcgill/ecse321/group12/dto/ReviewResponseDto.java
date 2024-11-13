package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Review;

public class ReviewResponseDto {

	private String review;

	private int rating;

	private int likeCount;

	private int id;

	@SuppressWarnings("unused")
	private ReviewResponseDto() {
	}

	public ReviewResponseDto(Review model) {
		this.review = model.getText();
		this.rating = model.getRating();
		this.likeCount = model.getLikeCount();
		this.id = model.getId();

	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return this.rating;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

}
