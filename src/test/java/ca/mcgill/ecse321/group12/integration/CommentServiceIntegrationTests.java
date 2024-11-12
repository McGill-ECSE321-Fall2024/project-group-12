package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.group12.repository.CommentRepository;
import ca.mcgill.ecse321.group12.dto.CommentRequestDto;
import ca.mcgill.ecse321.group12.dto.CommentResponseDto;
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

	@BeforeAll
	@AfterTestClass
	public void clearDatabase() {
		commentRepository.deleteAll();
        //review = reviewRepository.save(new Review());
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
        ResponseEntity<CommentResponseDto> response = client.postForEntity("/comments", request, CommentResponseDto.class);

        // Assert

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CommentResponseDto createdComment = response.getBody();
        assertNotNull(createdComment);
        assertEquals(text, createdComment.getText());
        //assertNotNull(createdComment.getReview());
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
        ResponseEntity<CommentResponseDto> response = client.postForEntity("/comments", request, CommentResponseDto.class);

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
        CommentRequestDto request = new CommentRequestDto(text, review);
        // Act
        ResponseEntity<CommentResponseDto> response = client.postForEntity("/comments", request,
                CommentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CommentResponseDto createdComment = response.getBody();
        assertNotNull(createdComment);
        assertEquals(text, createdComment.getText());
        //assertNotNull(createdComment.getReview());
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
		String url = "/comments/0";

		// Act
		ResponseEntity<CommentResponseDto> response = client.getForEntity(url, CommentResponseDto.class);

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
		RequestEntity<CommentRequestDto> request = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON).body(body);

		// Act
		ResponseEntity<CommentResponseDto> response = client.exchange(url, HttpMethod.PUT, request, CommentResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CommentResponseDto updatedComment = response.getBody();
        assertNotNull(updatedComment);
        assertEquals(text, updatedComment.getText());
        //assertNotNull(updatedComment.getReview());
        assertNotNull(updatedComment.getId());
		assertTrue(updatedComment.getId() > 0, "Response should have a positive ID.");

		this.commentId = updatedComment.getId();

	}
    

    /**
     * Test deleting a comment with valid id.
     * @author Carmin Vidé
     */
    @Test
    @Order(6)
    public void testDeleteComment() {
        // Arrange
        String url = "/comments/" + commentId;

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    /**
     * Test deleting a comment with invalid id.
     * @author Carmin Vidé
     */
    @Test
    @Order(7)
    public void testDeleteCommentWithInvalidId() {
        // Arrange
        String url = "/comments/0";

        // Act
        ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, null, String.class);
		String error = response.getBody();

        // Assert
        assertNotNull(response);
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}