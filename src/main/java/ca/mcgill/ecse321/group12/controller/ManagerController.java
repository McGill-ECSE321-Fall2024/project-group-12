package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ca.mcgill.ecse321.group12.dto.ManagerRequestDto;
import ca.mcgill.ecse321.group12.dto.ManagerResponseDto;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.service.ManagerService;

@RestController
public class ManagerController {

   @Autowired
   private ManagerService managerService;

   	/**
	 * Return the manager, if exists.
	 * @author Amy Ding
	 * @return The manager account, if it exists
	 */
	@GetMapping("/manager")
	public ManagerResponseDto findManager() {
		Manager manager = managerService.findManager();
		return new ManagerResponseDto(manager);
	}

    /**
	 * Create a new manager.
	 * @author Amy Ding
	 * @param manager The manager to create.
	 * @return The created manager.
	 */
	@PostMapping("/manager")
	@ResponseStatus(HttpStatus.CREATED)
	public ManagerResponseDto createManager(@RequestBody ManagerRequestDto manager) {
		Manager createdManager = managerService.createManager(manager.getEmail(), manager.getPassword(),
				manager.getName(), manager.getPhoneNumber());
		return new ManagerResponseDto(createdManager);
	}

    /**
	 * Update a manager.
	 * @author Amy Ding
	 * @param manager The manager to update.
	 * @return The updated manager account.
	 */
	@PutMapping("/manager")
	public ManagerResponseDto updateManager(@RequestBody ManagerRequestDto manager) {
		if (manager != null) {
			Manager updatedManager = managerService.updateManager(manager.getEmail(),
					manager.getPassword(), manager.getName(), manager.getPhoneNumber());
			return new ManagerResponseDto(updatedManager);
		}
		return new ManagerResponseDto(new Manager());
	}
}
