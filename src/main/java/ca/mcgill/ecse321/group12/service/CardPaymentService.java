package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.CardPayment;
import ca.mcgill.ecse321.group12.repository.CardPaymentRepository;
import jakarta.transaction.Transactional;

@Service
public class CardPaymentService {

	@Autowired
	private CardPaymentRepository repo;

	@Transactional
	public CardPayment createCardPayment(String nameOnCard, String cvc, String cardNumber, String billingAddress,
			boolean isSaved, String expiryDate) {

		if (nameOnCard == null || nameOnCard.trim().length() == 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Name on card cannot be empty.");
		}

		if (cvc == null || cvc.length() != 3) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "CVC must have length 3.");
		}

		if (cardNumber == null || cardNumber.length() != 19) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Card number must follow format XXXX XXXX XXXX XXXX.");
		}

		if (expiryDate == null || expiryDate.length() != 5) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Expiry date must follow format MM/YY.");
		}

		if (billingAddress == null || billingAddress.trim().length() == 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Billing address cannot be empty.");
		}
		// create a new Card Payment
		// TODO: validate credit card number?
		CardPayment cardPayment = new CardPayment();
		cardPayment.setNameOnCard(nameOnCard);
		cardPayment.setCvc(cvc);
		cardPayment.setCardNumber(cardNumber);
		cardPayment.setBillingAddress(billingAddress);
		cardPayment.setIsSaved(isSaved);
		cardPayment.setExpiryDate(expiryDate);

		return repo.save(cardPayment);

	}

}
