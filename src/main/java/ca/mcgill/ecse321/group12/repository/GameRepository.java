package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer>{

	Game findGameById(int id);

}
