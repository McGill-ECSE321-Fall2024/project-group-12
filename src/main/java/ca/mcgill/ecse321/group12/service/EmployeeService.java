package ca.mcgill.ecse321.group12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.model.Employee; 
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    public Employee findEmployeeById(int id) {
       Employee emp = employeeRepo.findEmployeeById(id);
        if (emp == null) {
            throw new IllegalArgumentException("There is no employee with ID " + id + ".");
        }
        return emp;
    }

    @Transactional 
    public Employee createEmployee(String email, String password, String name, String phoneNumber) {
        Employee employeeToCreate = new Employee();
        employeeToCreate.setEmail(email);
        employeeToCreate.setPassword(password);
        employeeToCreate.setName(name);
        employeeToCreate.setPhoneNumber(phoneNumber);
        return employeeRepo.save(employeeToCreate);
    }

    @Transactional 
    public void deleteEmployeeById(int id) {
        Employee employeeToDelete = employeeRepo.findEmployeeById(id);
        employeeRepo.delete(employeeToDelete);
    }
    
    @Transactional
    public Iterable<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

}