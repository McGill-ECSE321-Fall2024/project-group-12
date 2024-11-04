package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.repository.CartRepository;

@SpringBootTest
public class CartServiceTests {

	@Mock
	private CartRepository repo;

	@InjectMocks
	private CartService service;

	@Test
	public void testCreateValidCart() {
		// Arrange
		when(repo.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
		int id = 1;

		// Act
		Cart createdCart = service.createCart(id);

		// Assert
		assertNotNull(createdCart);
		verify(repo, times(1)).save(createdCart);
	}

	@Test
	public void testFindCartByValidId() {
		// Arrange
		int id = 1;
		Cart thisCart = new Cart();
		when(repo.findCartById(id)).thenReturn(thisCart);

		// Act
		Cart cart = service.findCartById(id);

		// Assert
		assertNotNull(cart);
	}

	@Test
	public void testFindCartByInvalidId() {
		// Arrange
		int id = 42;
		when(repo.findCartById(id)).thenReturn(null);

		// Act

		// Assert
		CustomException e = assertThrows(CustomException.class, () -> service.findCartById(id));
		assertEquals("There is no cart with ID " + id + ".", e.getMessage());
	}

}
