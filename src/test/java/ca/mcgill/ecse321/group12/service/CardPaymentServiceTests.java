package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.repository.CardPaymentRepository;

@SpringBootTest
public class CardPaymentServiceTests {

	@Mock
	private CardPaymentRepository repo;

	@InjectMocks
	private CardPaymentService service;

	@Test
	public void testCreateValidCardPayment() {
		// Arrange

		int id = 1;
		String nameOnCard = "John Doe";
		String cvc = "123";
		String cardNumber = "1234 5678 9012 3456";
		String billingAddress = "1234 Rue Sherbrooke";
		boolean isSaved = true;
		String expiryDate = "12/23";

		CardPayment cardPayment = new CardPayment(id, nameOnCard, cvc, cardNumber, billingAddress, isSaved, expiryDate);

		when(repo.save(any(CardPayment.class))).thenReturn(cardPayment);

		CardPayment created = service.createCardPayment(nameOnCard, cvc, cardNumber, billingAddress, isSaved,
				expiryDate);

		// Assert
		assertNotNull(created);
		assertEquals(nameOnCard, created.getNameOnCard());
		assertEquals(cvc, created.getCvc());
		assertEquals(cardNumber, created.getCardNumber());
		assertEquals(billingAddress, created.getBillingAddress());
		assertEquals(isSaved, created.getIsSaved());
		assertEquals(expiryDate, created.getExpiryDate());
		verify(repo, times(1)).save(any(CardPayment.class));

	}

	@Test
	public void testCreateCardPaymentByInvalidCardNumber() {
		// Arrange
		String nameOnCard = "John Doe";
		String cvc = "123";
		String cardNumber = "1234 5678 9012";
		String billingAddress = "1234 Rue Sherbrooke";
		boolean isSaved = true;
		String expiryDate = "12/23";
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> service.createCardPayment(nameOnCard, cvc, cardNumber, billingAddress, isSaved, expiryDate));
		assertEquals("Card number must follow format XXXX XXXX XXXX XXXX.", e.getMessage());
	}

	@Test
	public void testCreateCardPaymentByInvalidName() {
		// Arrange
		String nameOnCard = "";
		String cvc = "123";
		String cardNumber = "1234 5678 9012 3456";
		String billingAddress = "1234 Rue Sherbrooke";
		boolean isSaved = true;
		String expiryDate = "12/23";
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> service.createCardPayment(nameOnCard, cvc, cardNumber, billingAddress, isSaved, expiryDate));
		assertEquals("Name on card cannot be empty.", e.getMessage());
	}

	@Test
	public void testCreateCardPaymentByInvalidExpiryDate() {
		// Arrange
		String nameOnCard = "John ";
		String cvc = "123";
		String cardNumber = "1234 5678 9012 3456";
		String billingAddress = "1234 Rue Sherbrooke";
		boolean isSaved = true;
		String expiryDate = "12/23/0000";
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> service.createCardPayment(nameOnCard, cvc, cardNumber, billingAddress, isSaved, expiryDate));
		assertEquals("Expiry date must follow format MM/YY.", e.getMessage());
	}

	@Test
	public void testCreateCardPaymentByInvalidBillingAddress() {
		// Arrange
		String nameOnCard = "John ";
		String cvc = "123";
		String cardNumber = "1234 5678 9012 3456";
		String billingAddress = "";
		boolean isSaved = true;
		String expiryDate = "12/23";
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> service.createCardPayment(nameOnCard, cvc, cardNumber, billingAddress, isSaved, expiryDate));
		assertEquals("Billing address cannot be empty.", e.getMessage());
	}

}
