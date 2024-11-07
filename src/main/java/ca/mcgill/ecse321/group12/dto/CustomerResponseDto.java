package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Customer;

public class CustomerResponseDto {

	private int id;

	private String email;

	private String name;

	private String phoneNumber;

	@SuppressWarnings("unused")
	private CustomerResponseDto() {
	}
	
	public CustomerResponseDto(Customer model) {
		this.id = model.getId();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
	}

	public int getId() {
		return id;
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

	public void setId(int id) {
		this.id = id;
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