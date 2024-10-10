package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer>{

	UserRole findUserRoleById(int id);

}