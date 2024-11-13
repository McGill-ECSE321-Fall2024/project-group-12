package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Review;

public class ReviewRequestDto {

    private String review;

    private int rating;

    private int id;

    private int likeCount;

    private int gameId;

    private int customerId;

    @SuppressWarnings("unused")
    private ReviewRequestDto() {
    }

    public ReviewRequestDto(Review model) {
        this.id = model.getId();
        this.review = model.getText();
        this.rating = model.getRating();
        this.likeCount = model.getLikeCount();
    }
    
    public String getReview() {
        return this.review;
    }
    public int getRating() {
        return this.rating;
    }
    public int getId() {
        return this.id;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public int getGameId() {
        return this.gameId;
    }
    public int getCustomerId() {
        return this.customerId;
    }
    
    public void setReview(String review) {
        this.review = review;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
