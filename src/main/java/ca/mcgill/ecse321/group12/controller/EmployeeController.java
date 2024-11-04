package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Return the employee with the given ID.
	 * @param eid The primary key of the employee to find.
	 * @return The empllyee with the given ID.
	 */
	@GetMapping("/employee/{eid}")
	public EmployeeResponseDto findEmployeeById(@PathVariable int eid) {
		Employee employee = employeeService.findEmployeeById(eid);
		return new EmployeeResponseDto(employee);
	}

	/**
	 * Get all employees
	 * @return All employees.
	 */
	@GetMapping("/employee/all")
	public Iterable<Employee> findAllEmployees() {
		Iterable<Employee> allEmployees = employeeService.findAllEmployees();
		return allEmployees;
	}

	/**
	 * Delete an employee.
	 * @param employee The employee to delete.
	 */
	@DeleteMapping("/employee/{eid}")
	public void deleteEmployeeById(@PathVariable int eid) {
		employeeService.deleteEmployeeById(eid);
	}

	/**
	 * Create a new employee.
	 * @param employee The employee to create.
	 * @return The created employee, including their ID.
	 */
	@PostMapping("/employee")
	public EmployeeResponseDto createPerson(@RequestBody EmployeeRequestDto employee) {
		Employee createdEmployee = employeeService.createEmployee(employee.getEmail(), employee.getPassword(),
				employee.getName(), employee.getPhoneNumber());
		return new EmployeeResponseDto(createdEmployee);
	}
}
