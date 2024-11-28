package ca.mcgill.ecse321.group12.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;
import jakarta.transaction.Transactional;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepo;

	/**
	 * Create a new manager.
	 * @author Amy Ding
	 * @param email The email of the new manager.
	 * @param password The password of the new manager.
	 * @param name The name of the new manager.
	 * @param phoneNumber The phoneNumber of the new manager.
	 * @return The newly created manager.
	 */
	@Transactional
	public Manager createManager(String email, String password, String name, String phoneNumber) {
		if (managerRepo.count() > 0) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Create manager failed. Manager already exists");
		}
		Manager managerToCreate = new Manager();
		managerToCreate.setEmail(email);
		managerToCreate.setPassword(password);
		managerToCreate.setName(name);
		managerToCreate.setPhoneNumber(phoneNumber);

		Manager managerToSave = managerRepo.save(managerToCreate);

		return managerToSave;
	}

	/**
	 * Return the manager, if exists
	 * @author Amy Ding
	 * @return The manager account.
	 */
	@Transactional
	public Manager findManager() {
		if (managerRepo.count() < 1) {
			throw new CustomException(HttpStatus.NOT_FOUND, "Get manager failed. No manager account exists");
		}
		// Convert Iterable to List and get first element
		List<Manager> managers = StreamSupport.stream(managerRepo.findAll().spliterator(), false)
			.collect(Collectors.toList());

		return managers.get(0);
		// Manager manager = managerRepo.findAll();
		// return manager;
	}

	/**
	 * Update manager account
	 * @author Amy Ding
	 * @return The updated manager account
	 */
	@Transactional
	public Manager updateManager(String email, String password, String name, String phoneNumber) {
		List<Manager> managers = StreamSupport.stream(managerRepo.findAll().spliterator(), false)
			.collect(Collectors.toList());

		Manager manager = managers.get(0);
		manager.setEmail(email);
		manager.setName(name);
		manager.setPassword(password);
		manager.setPhoneNumber(phoneNumber);

		managerRepo.save(manager);
		return manager;
	}

}
