package ca.mcgill.ecse321.group12.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	 * @author Amy Ding
	 * @param eid The primary key of the employee to find.
	 * @return The empllyee with the given ID.
	 */
	@GetMapping("/employees/{eid}")
	public EmployeeResponseDto findEmployeeById(@PathVariable int eid) {
		Employee employee = employeeService.findEmployeeById(eid);
		return new EmployeeResponseDto(employee);
	}

	/**
	 * Get all employees
	 * @author Amy Ding
	 * @return All employees.
	 */
	@GetMapping("/employees")
	public Iterable<Employee> findAllEmployees() {
		Iterable<Employee> allEmployees = employeeService.findAllEmployees();
		return allEmployees;
	}

	/**
	 * Create a new employee.
	 * @author Amy Ding
	 * @param employee The employee to create.
	 * @return The created employee, including their ID.
	 */
	@PostMapping("/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeResponseDto createEmployee(@RequestBody EmployeeRequestDto employee) {
		Employee createdEmployee = employeeService.createEmployee(employee.getEmail(), employee.getPassword(),
				employee.getName(), employee.getPhoneNumber());
		return new EmployeeResponseDto(createdEmployee);
	}

	/**
	 * Update an employee.
	 * @author Amy Ding
	 * @param employee The employee to update.
	 * @return The updated employee, including their ID.
	 */
	@PutMapping("/employees/{eid}")
	public EmployeeResponseDto updateEmployee(@PathVariable int eid, 
			@RequestParam(value = "action", required = false) String action,
			@RequestBody(required = false) EmployeeRequestDto employee) {
		if (action != null && !action.isEmpty()) {
			if (action.equals("activate")) {
				return new EmployeeResponseDto(employeeService.activateEmployeeById(eid));
			} else if (action.equals("deactivate")) {
				return new EmployeeResponseDto(employeeService.deactivateEmployeeById(eid));
			}
		}
		if (employee != null) {
			Employee updatedEmployee = employeeService.updateEmployeeById(eid, employee.getEmail(), employee.getPassword(),
			employee.getName(), employee.getPhoneNumber());
			return new EmployeeResponseDto(updatedEmployee);
		}
		return new EmployeeResponseDto(new Employee());
	}

	/**
	 * Deactivate an employee.
	 * @author Amy Ding
	 * @param employee The employee to deactivate.
	 */
	@PutMapping("/employees/{eid}/deactivate")
	public EmployeeResponseDto deactivateEmployeeById(@PathVariable int eid) {
		Employee employee = employeeService.deactivateEmployeeById(eid);
		return new EmployeeResponseDto(employee);
	}

	/**
	 * Activate an employee.
	 * @author Amy Ding
	 * @param employee The employee to activate.
	 */
	@PutMapping("/employees/{eid}/activate")
	public EmployeeResponseDto activateEmployeeById(@PathVariable int eid) {
		Employee employee = employeeService.activateEmployeeById(eid);
		return new EmployeeResponseDto(employee);
	}


}