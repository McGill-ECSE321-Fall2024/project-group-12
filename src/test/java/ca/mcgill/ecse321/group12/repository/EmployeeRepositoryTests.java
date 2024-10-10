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
		Employee employee = new Employee(id, email, password, name, phone);

		// Save person
		employee = employeeRepository.save(employee);

		// Read person from database
		Employee employeeFromDb = employeeRepository.findEmployeeById(id);

		// Assert correct response
		assertNotNull(employee);
		assertEquals(employeeFromDb.getId(), id);
		assertEquals(employeeFromDb.getEmail(), email);
		assertEquals(employeeFromDb.getPassword(), password);
        assertEquals(employeeFromDb.getName(), name);
        assertEquals(employeeFromDb.getPhoneNumber(), phone);
	}
}