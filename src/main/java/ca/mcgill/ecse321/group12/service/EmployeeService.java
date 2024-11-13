package ca.mcgill.ecse321.group12.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.group12.exception.CustomException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	/**
	 * Return the employee with the given ID.
	 * @author Amy Ding
	 * @param id The primary key of the employee to find.
	 * @return The employee with the given ID.
	 */
	public Employee findEmployeeById(int id) {
		Employee emp = employeeRepo.findEmployeeById(id);
		if (emp == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no employee with ID " + id + ".");
		}
		return emp;
	}

	/**
	 * Create a new employee.
	 * @author Amy Ding
	 * @param email The email of the new employee.
	 * @param password The password of the new employee.
	 * @param name The name of the new employee.
	 * @param phoneNumber The phoneNumber of the new employee.
	 * @return The newly created employee.
	 */
	@Transactional
	public Employee createEmployee(String email, String password, String name, String phoneNumber) {
		Employee employeeToCreate = new Employee();
		employeeToCreate.setEmail(email);
		employeeToCreate.setPassword(password);
		employeeToCreate.setName(name);
		employeeToCreate.setPhoneNumber(phoneNumber);
		employeeToCreate.setActive(true);
		// employeeToCreate.set
		Employee savedEmployee = employeeRepo.save(employeeToCreate);

		if (savedEmployee.getEmail() == null) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Create employee failed. Employee with this email already exists in the system.");
		}
		return savedEmployee;
	}

	/**
	 * Find all employees
	 * @author Amy Ding
	 * @return A list of all employees
	 */
	@Transactional
	public Iterable<Employee> findAllEmployees() {
		return employeeRepo.findAll();
	}

	/**
	 * Update employee with the given ID
	 * @author Amy Ding
	 * @return A list of all employees
	 */
	@Transactional
	public Employee updateEmployeeById(int id, String newEmail, String password, String name, String phoneNumber) {
		Employee employeeToUpdate = employeeRepo.findEmployeeById(id);
		if (!employeeToUpdate.getActive()) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Update employee failed. Employee account is deactivated. Please reactivate employee account to update employee information.");
		}
		String previousEmail = employeeToUpdate.getEmail();
		employeeToUpdate.setEmail(newEmail);
		employeeToUpdate.setName(name);
		employeeToUpdate.setPassword(password);
		employeeToUpdate.setPhoneNumber(phoneNumber);
		if (!previousEmail.equals(newEmail) && employeeToUpdate.getEmail().equals(previousEmail)) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					"Update employee failed. Employee with this email already exists in the system.");
		}
		return employeeToUpdate;
	}

	/**
	 * Deactivate employee with the given ID
	 * @author Amy Ding
	 * @param id Id of the employee account you want to deactivate
	 * @return The deactivated employee account
	 */
	public Employee deactivateEmployeeById(int id) {
		Employee employeeToDeactivate = employeeRepo.findEmployeeById(id);
		if (employeeToDeactivate == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no employee with ID " + id + ".");
		}
		if (!employeeToDeactivate.getActive()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Employee account is already deactivated");
		}
		employeeToDeactivate.setActive(false);
		return employeeRepo.save(employeeToDeactivate);
	}

	/**
	 * Enable employee with the given ID
	 * @author Amy Ding
	 * @param id Id of the employee account you want to enable
	 * @return The enabled employee account
	 */
	public Employee activateEmployeeById(int id) {
		Employee employeeToActivate = employeeRepo.findEmployeeById(id);
		if (employeeToActivate == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, "There is no employee with ID " + id + ".");
		}
		if (employeeToActivate.getActive()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Employee account is already activated");
		}
		employeeToActivate.setActive(true);
		return employeeRepo.save(employeeToActivate);
	}

}