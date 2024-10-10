package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.CardPayment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardPaymentRepositoryTests {
	@Autowired
	private CardPaymentRepository cardPaymentRepository;

	@AfterEach
	public void clearDatabase() {
		cardPaymentRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadPerson() {
		// Create person
		String name = "Sophia Li";
		String cvc = "800";
		String cardNumber = "4520214999881022";
    String billingAddress = "2366 Main Mall, Vancouver, BC V6T 1Z4";
    boolean isSaved = false;
    Date expiryDate = new Date();
		CardPayment payment = new CardPayment(0, name, cvc, cardNumber, billingAddress, false, null);

		// Save person
		payment = cardPaymentRepository.save(payment);
		int id = payment.getId();

		// Read person from database
		CardPayment paymentFromDb = cardPaymentRepository.findCardPaymentById(id);

		// Assert correct response
		assertNotNull(payment);
		assertEquals(paymentFromDb.getNameOnCard(), name);
		assertEquals(paymentFromDb.getCvc(), cvc);
		assertEquals(paymentFromDb.getCardNumber(), cardNumber);
		assertEquals(paymentFromDb.getBillingAddress(), billingAddress);
		assertEquals(paymentFromDb.getIsSaved(), isSaved);
		assertEquals(paymentFromDb.getExpiryDate(), expiryDate);
	}
}