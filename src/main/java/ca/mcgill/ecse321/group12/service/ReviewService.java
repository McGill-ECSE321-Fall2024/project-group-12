package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review findReviewById(int reviewId) {
        Review r = reviewRepository.findReviewById(reviewId);
        if (r == null) {
            throw new IllegalArgumentException("There is no review with ID" + reviewId + ".");
        }
        return r;
    }

    @Transactional
    public Review createReview(int likeCount, int rating, String text) {
        if (likeCount < 0) {
            throw new IllegalArgumentException("Like count cannot be negative.");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        if (text == null || text.trim().length() == 0) {
            throw new IllegalArgumentException("Review text cannot be empty.");
        }
        Review review = new Review();
        review.setLikeCount(likeCount);
        review.setRating(rating);
        review.setText(text);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public Review updateReview(int id, int likeCount, int rating, String text) {
        Review review = reviewRepository.findReviewById(id);
        if (likeCount < 0) {
            throw new IllegalArgumentException("Like count cannot be negative.");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        if (text == null || text.trim().length() == 0) {
            throw new IllegalArgumentException("Review text cannot be empty.");
        }
        review.setLikeCount(likeCount);
        review.setRating(rating);
        review.setText(text);
        reviewRepository.save(review);
        return review;
    }
}
