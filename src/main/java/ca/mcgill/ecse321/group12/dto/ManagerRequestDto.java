package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Manager;

public class ManagerRequestDto {

	private String password;

	private String email;

	private String name;

	private String phoneNumber;

	@SuppressWarnings("unused")
	private ManagerRequestDto() {
	}

	/**
	 * Constructor
	 * @author Amy Ding
	 * @param email The email for the manager being created
	 * @param password The password for the manager being created
	 * @param name The name for the manager being created
	 * @param phoneNumber The phone number for the manager being created
	 */
	public ManagerRequestDto(String email, String password, String name, String phoneNumber) {
		this.password = password;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Constructor
	 * @author Amy Ding
	 * @param model A valid manager
	 */
	public ManagerRequestDto(Manager model) {
		this.password = model.getPassword();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
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

}
