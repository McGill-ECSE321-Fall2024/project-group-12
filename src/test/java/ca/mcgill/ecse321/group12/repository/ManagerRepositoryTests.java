package ca.mcgill.ecse321.group12.repository;

import ca.mcgill.ecse321.group12.model.Manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ManagerRepositoryTests {

	@Autowired
	private ManagerRepository managerRepository;

	@AfterEach
	public void clearDatabase() {
		managerRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadManager() {
		// Create manager
		String name = "Jane Doe";
		String phoneNumber = "0293729037";
		String email = "janedoe@hotmail.nk";
		String password = "1234";
		Manager jane = new Manager();
		jane.setEmail(email);
		jane.setPassword(password);
		jane.setName(name);
		jane.setPhoneNumber(phoneNumber);

		// Save manager
		jane = managerRepository.save(jane);
		int id = jane.getId();

		// Read person from database
		Manager janeFromDb = managerRepository.findManagerById(id);

		// Assert correct
		assertNotNull(jane);
		assertEquals(janeFromDb.getName(), name);
		assertEquals(janeFromDb.getEmail(), email);
		assertEquals(janeFromDb.getPassword(), password);
		assertEquals(janeFromDb.getPhoneNumber(), phoneNumber);
	}

}