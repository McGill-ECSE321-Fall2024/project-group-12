package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.group12.repository.CommentRepository;
import ca.mcgill.ecse321.group12.dto.CommentRequestDto;
import ca.mcgill.ecse321.group12.dto.CommentResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerCreateResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.model.Review;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CommentServiceIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private CommentRepository commentRepository;

	private final String text = "Awesome game!";

	private int commentId;

	private Review review;

	private String customerAuth;

	@BeforeAll
	public void setup() {

		// create a customer
		CustomerRequestDto customerRequest = new CustomerRequestDto("customer@email.com", "password123", "Customer",
				"778 000 0000", "1234 Street");
		ResponseEntity<CustomerCreateResponseDto> customerResponse = client.postForEntity("/customers", customerRequest,
				CustomerCreateResponseDto.class);
		// store the customer's authentication token
		CustomerCreateResponseDto body = customerResponse.getBody();
		assertNotNull(body);
		customerAuth = "Bearer " + body.getToken();

	}

	@AfterTestClass
	public void clearDatabase() {
		commentRepository.deleteAll();
	}

	/**
	 * Test creating a comment with valid arguments.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(1)
	public void testCreateCommentWithValidText() {
		// Arrange
		CommentRequestDto request = new CommentRequestDto(text, review);

		// Act
		RequestEntity<CommentRequestDto> req = RequestEntity.post("/comments")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<CommentResponseDto> response = client.exchange(req, CommentResponseDto.class);

		// Assert

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		CommentResponseDto createdComment = response.getBody();
		assertNotNull(createdComment);
		assertEquals(text, createdComment.getText());
		// assertNotNull(createdComment.getReview());
		assertNotNull(createdComment.getId());
		assertTrue(createdComment.getId() > 0, "Response should have a positive ID.");
		this.commentId = createdComment.getId();
		this.review = createdComment.getReview();
	}

	/**
	 * Test creating a comment with invalid text.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(2)
	public void testCreatingCommentWithInvalidText() {
		// Arrange
		CommentRequestDto request = new CommentRequestDto(null, review);

		// Act
		RequestEntity<CommentRequestDto> req = RequestEntity.post("/comments")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(request);
		ResponseEntity<CommentResponseDto> response = client.exchange(req, CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Test retrieving a comment by its id.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(3)
	public void testFindCommentById() {
		// Arrange
		String url = "/comments/" + this.commentId;
		// Act
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CommentResponseDto> response = client.exchange(req, CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CommentResponseDto createdComment = response.getBody();
		assertNotNull(createdComment);
		assertEquals(text, createdComment.getText());
		assertNotNull(createdComment.getId());
		assertTrue(createdComment.getId() > 0, "Response should have a positive ID.");
		this.commentId = createdComment.getId();
	}

	/**
	 * Test retrieving a comment with an invalid id.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(4)
	public void testFindCommentByIdWithInvalidId() {
		// Arrange
		String url = "/comments/-1";

		// Act
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CommentResponseDto> response = client.exchange(req, CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test updating a comment with valid arguments.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(5)
	public void testUpdateComment() {
		// Arrange
		String url = "/comments/" + this.commentId;
		CommentRequestDto body = new CommentRequestDto(text, review);
		RequestEntity<CommentRequestDto> request = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);

		// Act
		ResponseEntity<CommentResponseDto> response = client.exchange(url, HttpMethod.PUT, request,
				CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CommentResponseDto updatedComment = response.getBody();
		assertNotNull(updatedComment);
		assertEquals(text, updatedComment.getText());
		// assertNotNull(updatedComment.getReview());
		assertNotNull(updatedComment.getId());
		assertTrue(updatedComment.getId() > 0, "Response should have a positive ID.");
		this.commentId = updatedComment.getId();
	}

	/**
	 * Test getting all comments.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(6)
	public void testFindAllComments() {
		// Arrange
		String url = "/comments";
		// Act
		// ResponseEntity<List<CommentResponseDto>> response = client.getForEntity(url,
		// CommentResponseDto.class);
		ResponseEntity<List<CommentResponseDto>> response = client.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CommentResponseDto>>() {
				});
		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Iterable<CommentResponseDto> comments = response.getBody();
		assertNotNull(comments);
		List<CommentResponseDto> commentList = (List<CommentResponseDto>) comments;
		CommentResponseDto firstComment = commentList.get(0);
		assertNotNull(firstComment);
		assertEquals(text, firstComment.getText());
		assertNotNull(firstComment.getId());
		assertTrue(firstComment.getId() > 0, "Response should have a positive ID.");

	}

	/**
	 * Test deleting a comment with valid id.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(7)
	public void testDeleteComment() {
		// Arrange
		String url = "/comments/" + commentId;

		// Act
		RequestEntity<Void> req = RequestEntity.delete(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CommentResponseDto> response = client.exchange(req, CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Test deleting a comment with invalid id.
	 * @author Carmin Vidé
	 */
	@Test
	@Order(8)
	public void testDeleteCommentWithInvalidId() {
		// Arrange
		String url = "/comments/0";

		// Act
		RequestEntity<Void> req = RequestEntity.delete(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<String> response = client.exchange(req, String.class);
		String error = response.getBody();

		// Assert
		assertNotNull(response);
		assertNotNull(error);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}