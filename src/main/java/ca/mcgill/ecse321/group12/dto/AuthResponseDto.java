package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.UserRole.UserType;

public class AuthResponseDto {

	private String token;

	private int id;

	private UserType userType;

	public String getToken() {
		return token;
	}

	public int getId() {
		return id;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setToken(String newToken) {
		token = newToken;
	}

	public void setId(int newId) {
		id = newId;
	}

	public void setUserType(UserType newUserType) {
		userType = newUserType;
	}

}
