package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;

@SpringBootTest
public class ManagerServiceTests {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerService managerService;

    /**
	 * Test to create an manager
	 * @author Amy Ding
	 * @return void
	 */
    @SuppressWarnings("null")
	@Test
	public void testCreateManagerSuccessfully() {
		// Arrange
		String name = "manager";
		String email = "manager@mail.mcgill.ca";
		String password = "manage123";
		String phoneNumber = "0987654321";
		Manager manager = new Manager();

		manager.setEmail(email);
		manager.setPassword(password);
		manager.setName(name);
		manager.setPhoneNumber(phoneNumber);

		when(managerRepository.save(any(Manager.class))).thenReturn(manager);

		// Act
		Manager createdManager = managerService.createManager(email, password, name, phoneNumber);

		// Assert
		assertNotNull(createdManager);
		assertEquals(name, createdManager.getName());
		assertEquals(email, createdManager.getEmail());
		assertEquals(password, createdManager.getPassword());
		assertEquals(phoneNumber, createdManager.getPhoneNumber());
		verify(managerRepository, times(1)).save(any(Manager.class));
	}

    /**
	 * Test to create manager when there is already a manager account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testCreateManagerWhenExistingManager() {
		// Arrange
		String email = "anothermanager@mail.mcgill.ca";
		String name = "Manager_again";
		String password = "manager123";
		String phoneNumber = "1234567890";

        when(managerRepository.count()).thenReturn(1L); // simulate existence of manager
		// Act & Assert

		CustomException e = assertThrows(CustomException.class,
				() -> managerService.createManager(email, password, name, phoneNumber));
		assertEquals("Create manager failed. Manager already exists", e.getMessage());
		verify(managerRepository, times(1)).count();
	}

    /**
	 * Test to get an manager account that doesn't exist
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testReadManagerWhenNoManager() {
		// Arrange
		when(managerRepository.findAll()).thenReturn(null);
		
        // Act & Assert
        CustomException e = assertThrows(CustomException.class,
				() -> managerService.findManager());
		assertEquals("Get manager failed. No manager account exists", e.getMessage());
	}

    /**
	 * Test to get an manager account that exists
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testReadManagerSuccessfully() {
		// Arrange
		Manager manager = new Manager();
        String email = "manager@mcgill.ca";
		String name = "Manager_again";
		String password = "manager123";
		String phoneNumber = "1234567890";

		manager.setEmail(email);
		manager.setName(name);
		manager.setPassword(password);
		manager.setPhoneNumber(phoneNumber);
        List<Manager> managers = Arrays.asList(manager);
        
		when(managerRepository.findAll()).thenReturn(managers);
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);
        when(managerRepository.count()).thenReturn(1L); // simulate existing manager
		// Act
		Manager foundManager = managerService.findManager();

		// Assert
		assertNotNull(foundManager);
		assertEquals(manager.getName(), foundManager.getName());
		assertEquals(manager.getEmail(), foundManager.getEmail());
		assertEquals(manager.getPassword(), foundManager.getPassword());
		assertEquals(manager.getPhoneNumber(), foundManager.getPhoneNumber());
	}

    /**
	 * Test to update an manager account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testUpdateManagerSuccessfully() {
        // Arrange
		Manager manager = new Manager();
        String email = "manageramy@mcgill.ca";
		String name = "Manager_again";
		String password = "manager123";
		String phoneNumber = "1234567890";

        String email2 = "updatedmanageremail@mcgill.ca";
        String name2 = "barry";
        String password2 = "passwordNEW1";
        String phoneNumber2 = "000000000";

		manager.setEmail(email);
		manager.setName(name);
		manager.setPassword(password);
		manager.setPhoneNumber(phoneNumber);
        List<Manager> managers = Arrays.asList(manager);
        
		when(managerRepository.findAll()).thenReturn(managers);
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);
        when(managerRepository.count()).thenReturn(1L); // simulate existing manager
		// Act
		Manager updatedManager = managerService.updateManager(email2, password2, name2, phoneNumber2);

		// Assert
		assertNotNull(updatedManager);
		assertEquals(name2, updatedManager.getName());
		assertEquals(email2, updatedManager.getEmail());
		assertEquals(password2, updatedManager.getPassword());
		assertEquals(phoneNumber2, updatedManager.getPhoneNumber());
	}
}
