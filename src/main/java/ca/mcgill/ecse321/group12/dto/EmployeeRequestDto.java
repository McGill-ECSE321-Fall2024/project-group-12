package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Employee;

// if api call needs a body then we need a corresponding request
public class EmployeeRequestDto {

	private String password;

	private String email;

	private String name;

	private String phoneNumber;

	private boolean active;

	@SuppressWarnings("unused")
  public EmployeeRequestDto() {
	}

	/**
	 * Constructor
	 * @author Amy Ding
	 * @param email The email for the employee being created
	 * @param password The password for the employee being created
	 * @param name The name for the employee being created
	 * @param phoneNumber The phone number for the employee being created
	 * @param active Whether the employee account is activated or not
	 */
	public EmployeeRequestDto(String email, String password, String name, String phoneNumber, boolean active) {
		this.password = password;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.active = active;
	}

	/**
	 * Constructor
	 * @author Amy Ding
	 * @param model A valid employee
	 */
	public EmployeeRequestDto(Employee model) {
		this.password = model.getPassword();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
		this.active = model.getActive();
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean getActive() {
		return active;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}