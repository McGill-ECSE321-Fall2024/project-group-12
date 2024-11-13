package ca.mcgill.ecse321.group12.service;

import org.checkerframework.checker.lock.qual.MayReleaseLocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.group12.exception.CustomException;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired 
    private CustomerRepository customerRepository;


    /* 
     * Find review by its ID
     * @param reviewId the ID of the review
     * @return the review with the given ID
     */

    public Review findReviewById(int reviewId) {
        Review r = reviewRepository.findReviewById(reviewId);
        if (r == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no review with ID " + reviewId + ".");
        }
        return r;
    }

    /*
     * Create a review
     * @param likeCount 
     * @param rating
     * @param text
     * @return the created review
     */

    @Transactional
    public Review createReview(int likeCount, int rating, String text, int gameId, int customerId) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no game with ID " + gameId + ".");
        }
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + customerId + ".");
        }

        if (likeCount < 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Like count cannot be negative.");
        }
        if (rating < 0 || rating > 5) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Rating must be between 0 and 5.");
        }
        if (text == null || text.trim().length() == 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Review text cannot be empty.");
        }
        
        Review review = new Review();
        review.setLikeCount(likeCount);
        review.setRating(rating);
        review.setText(text);
        review.setGame(game);
        review.setCustomer(customer);
        reviewRepository.save(review);
        return review;
    }

    /*
     * Update a review
     * @param id
     * @param likeCount
     * @param rating
     * @param text
     * @return the updated review
     */
    @Transactional
    public Review updateReview(int id, int likeCount, int rating, String text, int gameId, int customerId) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no game with ID " + gameId + ".");
        }
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no customer with ID " + customerId + ".");
        }
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        if (likeCount < 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Like count cannot be negative.");
        }
        if (rating < 0 || rating > 5) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Rating must be between 0 and 5.");
        }
        if (text == null || text.trim().length() == 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Review text cannot be empty.");
        }
        review.setLikeCount(likeCount);
        review.setRating(rating);
        review.setText(text);
        review.setGame(game);
        review.setCustomer(customer);
        reviewRepository.save(review);
        return review;
    }
}
