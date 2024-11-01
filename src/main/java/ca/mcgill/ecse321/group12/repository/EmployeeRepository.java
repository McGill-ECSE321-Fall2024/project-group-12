package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	Employee findEmployeeById(int id);

}