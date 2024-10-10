package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    
    Manager findManagerById(int id);

}
