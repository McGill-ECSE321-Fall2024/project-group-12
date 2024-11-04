package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.ReviewResponseDto;
import ca.mcgill.ecse321.group12.model.Review;
import ca.mcgill.ecse321.group12.service.ReviewService;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * Return the review with the given id
     * @param id the primary of the review
     * @return the review with the given id
     */

    @GetMapping(value = { "/review/{id}" })
    public ReviewResponseDto getReview(@PathVariable("id") int id) {
        Review review = reviewService.findReviewById(id);
        return new ReviewResponseDto(review);
    }


    /**
     * Create a review with the given parameters
     * @param likeCount the number of likes the review has
     * @param rating the rating set by author of the review
     * @param text the text contained in the review
     * @return the created review
     */
    @PostMapping(value = { "/review/{likeCount}/{rating}/{text}" })
    public ReviewResponseDto createReview(@PathVariable("likeCount") int likeCount, @PathVariable("rating") int rating,
    @PathVariable("text") String text) {
        // #1: create new review
        Review createdReview = reviewService.createReview(likeCount, rating, text);
        // #2: return the created review
        return new ReviewResponseDto(createdReview);
    }
}