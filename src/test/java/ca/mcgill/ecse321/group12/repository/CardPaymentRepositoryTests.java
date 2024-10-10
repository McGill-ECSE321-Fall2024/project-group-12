package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.CardPayment;

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
		String name = "Muffin Man";
		String emailAddress = "muffin.man@gmail.com";
		String password = "i_love_muffins";
		CardPayment payment = new CardPayment();
		muffinMan.setName(name);
		muffinMan.setEmailAddress(emailAddress);
		muffinMan.setPassword(password);

		// Save person
		muffinMan = personRepository.save(person);
		int id = muffinMan.getId();

		// Read person from database
		Person muffinManFromDb = personRepository.findPersonById(id);

		// Assert correct response
		assertNotNull(person);
		assertEquals(muffinManFromDb.getName(), name);
		assertEquals(muffinManFromDb.getEmailAddress(), emailAddress);
		assertEquals(muffinManFromDb.getPassword(), password);
	}
}