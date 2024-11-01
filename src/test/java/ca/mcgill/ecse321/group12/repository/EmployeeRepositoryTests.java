package ca.mcgill.ecse321.group12.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.model.Employee;

@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadEmployee() {
		// Create employee
		// int aId, String aEmail, String aPassword, String aName, String aPhoneNumber

		int id = 1;
		String email = "marwan@gmail.com";
		String password = "i_love_circuits";
		String name = "Marwan";
		String phone = "12345678";
		Employee employee = new Employee();
		employee.setId(id);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setName(name);
		employee.setPhoneNumber(phone);
		// Save person
		employee = employeeRepository.save(employee);
		int id2 = employee.getId();
		// Read person from database
		Employee employeeFromDb = employeeRepository.findEmployeeById(id2);

		// Assert correct response
		assertNotNull(employee);
		assertNotNull(employeeFromDb);
		assertEquals(employeeFromDb.getId(), id2);
		assertEquals(employeeFromDb.getEmail(), email);
		assertEquals(employeeFromDb.getPassword(), password);
		assertEquals(employeeFromDb.getName(), name);
		assertEquals(employeeFromDb.getPhoneNumber(), phone);
	}

}