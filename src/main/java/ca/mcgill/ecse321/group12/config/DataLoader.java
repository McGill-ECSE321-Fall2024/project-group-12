package ca.mcgill.ecse321.group12.config;

import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ManagerRepository repo;

    public DataLoader(ManagerRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        // add a manager to the database if there's not one already
        if (repo.count() == 0) {
          Manager manager = new Manager();
          manager.setEmail("email@reindeer.com");
          manager.setPassword("$2a$10$zv75cty/d72KxyH9Z9AcQOjlvbLv3E1bDhhP5rVTvZ7TB760zXwZ."); // "password"
          manager.setName("Manager");
          manager.setAddress("688 Rue Sherbrooke");
          manager.setPhoneNumber("(555) 555-5555");

          repo.save(manager);
        }
    }
}