package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

import ca.mcgill.ecse321.group12.dto.ReviewRequestDto;
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

	@GetMapping("/reviews/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReviewResponseDto getReview(@PathVariable int id) {
		Review review = reviewService.findReviewById(id);
		return new ReviewResponseDto(review);
	}

	/**
	 * Create a review
	 * @param review the review to create
	 * @return the created review
	 */
	@PostMapping("/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public ReviewResponseDto createReview(@RequestBody ReviewRequestDto review) {
		// #1: create new review
		Review createdReview = reviewService.createReview(review.getLikeCount(), review.getRating(), review.getReview(),
				review.getGameId(), review.getCustomerId());
		// #2: return the created review
		return new ReviewResponseDto(createdReview);
	}

	/**
	 * Update the review with the given id
	 * @param id the primary key of the review
	 * @return the updated review
	 */
	@PutMapping("/reviews/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReviewResponseDto updateReview(@PathVariable int id, @RequestBody ReviewRequestDto review) {
		// #1: update the review
		Review updatedReview = reviewService.updateReview(id, review.getLikeCount(), review.getRating(),
				review.getReview(), review.getGameId(), review.getCustomerId());
		// #2: return the updated review
		return new ReviewResponseDto(updatedReview);
	}

}