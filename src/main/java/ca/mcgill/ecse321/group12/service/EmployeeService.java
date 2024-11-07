package ca.mcgill.ecse321.group12.service;

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
		return employeeRepo.save(employeeToCreate);
	}

	/**
	 * Delete the employee with the given ID.
	 * @param id The primary key of the employee to delete.
	 */
	@Transactional
	public void deleteEmployeeById(int id) {
		Employee employeeToDelete = employeeRepo.findEmployeeById(id);
		employeeRepo.delete(employeeToDelete);
	}

	/**
	 * Find all employees
	 * @return A list of all employees
	 */
	@Transactional
	public Iterable<Employee> findAllEmployees() {
		return employeeRepo.findAll();
	}

	/**
	 * Update employee with the given ID
	 * @return A list of all employees
	 */
	@Transactional
	public Employee updateEmployeeById(int id, String email, String password, String name, String phoneNumber) {
		Employee employeeToUpdate = employeeRepo.findEmployeeById(id);
		employeeToUpdate.setEmail(email);
		employeeToUpdate.setName(name);
		employeeToUpdate.setPassword(password);
		employeeToUpdate.setPhoneNumber(phoneNumber);
		return employeeToUpdate;
	}

}