package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
