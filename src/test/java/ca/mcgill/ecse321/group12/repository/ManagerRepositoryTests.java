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
	public void testPersistAndLoadPerson() {
		// Create manager
		String name = "Kim Jung Deux";
		String phoneNumber = "0293729037";
		String email = "kimmyjung2@hotmail.nk";
		String password = "iaminlovewithtrump";
		Manager kimmyJung2 = new Manager(0, email, password, name, phoneNumber);

		// Save manager
		kimmyJung2 = managerRepository.save(kimmyJung2);
		int id = kimmyJung2.getId();

		// Read person from database
		Manager muffinManFromDb = managerRepository.findManagerById(id);

		// Assert correct response
		assertNotNull(kimmyJung2);
		assertEquals(muffinManFromDb.getName(), name);
		assertEquals(muffinManFromDb.getEmail(), email);
		assertEquals(muffinManFromDb.getPassword(), password);
	}
}