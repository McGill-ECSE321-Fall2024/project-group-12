package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

import ca.mcgill.ecse321.group12.dto.ReviewRequestDto;
import ca.mcgill.ecse321.group12.dto.ReviewResponseDto;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.repository.ReviewRepository;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewServiceIntegrationTests {


    @Autowired
	private TestRestTemplate client;

	@Autowired
	private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
	private CustomerRepository customerRepository;


    private final int VALID_LIKE_COUNT = 10;

    private final int VALID_RATING = 5;

    private final String VALID_TEXT = "This is a test review for integration testing";

    private final int VALID_LIKE_COUNT_2 = 12;

    private final int VALID_RATING_2 = 4;

    private final String VALID_TEXT_2 = "This is a test review for integration testing to update a review";
    
    private CustomerResponseDto customer;

    private GameResponseDto gameDto;

    private int validId;;

    /**
     * In order to create a review, a customer, and a game must be present in the database/ 
     * Adding these to the database here. 
     * @author Kennedy Olsen
     */
    @BeforeAll
    public void setup() {
        // create a customer to associate with the review
        Customer customer = new Customer();
        CustomerRequestDto customerRequest = new CustomerRequestDto(customer);
        ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerRequest, CustomerResponseDto.class);
        // save the response
        this.customer = customerResponse.getBody();

        // create a game to associate with the review
        GameRequestDto gameRequest = new GameRequestDto(Category.Action, Console.Switch, 10, 10f, "Action Game", "A game full of fun, fear, and excitement", GameStatus.InCatalog);
        // post to the database
        GameResponseDto gameDto = client.postForEntity("/games", gameRequest, GameResponseDto.class).getBody();


    }

    /**
     * delete all data from database after the tests are run 
     * @author Kennedy Olsen
     */
    @AfterAll
    public void clearDb() {
        reviewRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    /**
     * Use case: create a review on a game customer has purchased using valid feilds
     * @author Kennedy Olsen
     */
    @Test
    @Order(1)
    public void testCreateReviewWithValidData() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto(VALID_TEXT, VALID_RATING, VALID_LIKE_COUNT, gameDto.getId(), customer.getId());
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.CREATED, reviewResponse.getStatusCode());
        ReviewResponseDto review = reviewResponse.getBody();
        assertNotNull(review);
        assertEquals(VALID_LIKE_COUNT, review.getLikeCount());
        assertEquals(VALID_RATING, review.getText());
        assertEquals(VALID_TEXT, review.getReview());
        assertNotNull(review.getId());
        this.validId = review.getId();
    }

    /**
     * Tries to create a review on a game with an invalid text. 
     * @author Kennedy Olsen
     */
    @Test
    @Order(2)
    public void testCreateReviewWithInvalidText() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto("", VALID_RATING, VALID_LIKE_COUNT, gameDto.getId(), customer.getId());
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.BAD_REQUEST, reviewResponse.getStatusCode());
    }

    /**
     * Tries to create a review on a game with an invalid rating. 
     * Rating must be between 0 and 5. 
     * This test tries to create a review with a rating of 6. 
     * This should return a bad request status code. 
     * @author Kennedy Olsen
     */
    @Test
    @Order(3)
    public void testCreateReviewWithInvalidRating() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto(VALID_TEXT, 6, VALID_LIKE_COUNT, gameDto.getId(), customer.getId());
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.BAD_REQUEST, reviewResponse.getStatusCode());
    }

    /**
     * Tries to create a review on a game with an invalid like count. 
     * Like count must be greater than or equal to 0. 
     * This test tries to create a review with a like count of -1. 
     * This should return a bad request status code. 
     * @author Kennedy Olsen
     */
    @Test@Order(4)
    public void testCreateReviewWithInvalidLikeCount() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto(VALID_TEXT, VALID_RATING, -1, gameDto.getId(), customer.getId());
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.BAD_REQUEST, reviewResponse.getStatusCode());
    }

    /**
     * Tries to create a review with a game that does not exist.
     * This should return a not found status code.
     * @author Kennedy Olsen
     */

    @Test
    @Order(5)
    public void testCreateReviewWithInvalidGame() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto(VALID_TEXT, VALID_RATING, VALID_LIKE_COUNT, -1, customer.getId());
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.NOT_FOUND, reviewResponse.getStatusCode());
    }

    /**
     * Tries to create a review with a customer that does not exist.
     * This should return a not found status code. 
     * @author Kennedy Olsen
     */
    @Test
    @Order(6)
    public void testCreateReviewWithInvalidCustomer() {
        // arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto(VALID_TEXT, VALID_RATING, VALID_LIKE_COUNT, gameDto.getId(), -1);
        // act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        // assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.NOT_FOUND, reviewResponse.getStatusCode());
    }

    /**
     * Tries to read a review with an existing Id
     * @author Kennedy Olsen
     */
    @Test
    @Order(7)
    public void testReadReviewWithValidId() {
        // arrange
        String url = "/review/" + this.validId;

        //act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.getForEntity(url, ReviewResponseDto.class);

        //assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.OK, reviewResponse.getStatusCode());
        ReviewResponseDto review = reviewResponse.getBody();
        assertNotNull(review);
        assertEquals(VALID_LIKE_COUNT, review.getLikeCount());
        assertEquals(VALID_RATING, review.getText());
        assertEquals(VALID_TEXT, review.getReview());
        assertEquals(this.validId, review.getId());
    }

    /**
     * Tries to read a review with an invalid Id
     * This should return a not found status code. 
     * The id used in this test is -1. 
     * This id does not exist in the database. 
     * @author Kennedy Olsen
     */
    @Test
    @Order(8)
    public void testReadReviewWithInvalidId() {
        // arrange
    
        String url = "/review/-1";

        //act
        ResponseEntity<ReviewResponseDto> reviewResponse = client.getForEntity(url, ReviewResponseDto.class);

        //assert
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.NOT_FOUND, reviewResponse.getStatusCode());
    }

    /**
     * Tries to update a review with valid data.
     * This test updates the review created in the first test.
     * @author Kennedy Olsen
     */
    @Test
    @Order(9)
    public void testUpdateReviewWithValidData() {
        // arrange
        String url = "/review/" + this.validId;
        ReviewRequestDto body = new ReviewRequestDto(VALID_TEXT_2, VALID_RATING_2, VALID_LIKE_COUNT_2, gameDto.getId(), customer.getId());
        RequestEntity<ReviewRequestDto> request = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON).body(body);

        // act
        ResponseEntity<ReviewResponseDto> response = client.exchange(url, HttpMethod.PUT, request, ReviewResponseDto.class);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto review = response.getBody();
        assertNotNull(review);
        assertEquals(VALID_LIKE_COUNT_2, review.getLikeCount());
        assertEquals(VALID_RATING_2, review.getText());
        assertEquals(VALID_TEXT_2, review.getReview());
        assertNotNull(review.getId());
        
        this.validId = review.getId();
    }

    /**
     * Tries to update a review with an invalid text.
     * @author Kennedy Olsen
     */
    @Test
    @Order(10)
    public void testUpdateReviewWithInvalidText() {
        // arrange
        String url = "/review/" + this.validId;
        ReviewRequestDto body = new ReviewRequestDto("", VALID_RATING_2, VALID_LIKE_COUNT_2, gameDto.getId(), customer.getId());
        RequestEntity<ReviewRequestDto> request = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON).body(body);

        // act
        ResponseEntity<ReviewResponseDto> response = client.exchange(url, HttpMethod.PUT, request, ReviewResponseDto.class);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Tries to update a review with an invalid rating.
     * @author Kennedy Olsen
     */
    @Test
    @Order(11)
    public void testUpdateReviewWithInvalidRating() {
        // arrange
        String url = "/review/" + this.validId;
        ReviewRequestDto body = new ReviewRequestDto(VALID_TEXT_2, 6, VALID_LIKE_COUNT_2, gameDto.getId(), customer.getId());
        RequestEntity<ReviewRequestDto> request = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON).body(body);

        // act
        ResponseEntity<ReviewResponseDto> response = client.exchange(url, HttpMethod.PUT, request, ReviewResponseDto.class);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Tries to update a review with an invalid like count.
     * @author Kennedy Olsen
     */
    @Test
    @Order(12)
    public void testUpdateReviewWithInvalidLikeCount() {
        // arrange
        String url = "/review/" + this.validId;
        ReviewRequestDto body = new ReviewRequestDto(VALID_TEXT_2, VALID_RATING_2, -1, gameDto.getId(), customer.getId());
        RequestEntity<ReviewRequestDto> request = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON).body(body);

        // act
        ResponseEntity<ReviewResponseDto> response = client.exchange(url, HttpMethod.PUT, request, ReviewResponseDto.class);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
