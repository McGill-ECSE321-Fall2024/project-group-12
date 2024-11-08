package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Customer;

public class CustomerRequestDto {

	private String password;

	private String email;

	private String name;

	private String phoneNumber;

	@SuppressWarnings("unused")
	private CustomerRequestDto() {
	}

	public CustomerRequestDto(Customer model) {
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