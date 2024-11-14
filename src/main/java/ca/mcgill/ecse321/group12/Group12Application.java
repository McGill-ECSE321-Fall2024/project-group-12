package ca.mcgill.ecse321.group12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class Group12Application {

	public static void main(String[] args) {
		SpringApplication.run(Group12Application.class, args);
	}

}
