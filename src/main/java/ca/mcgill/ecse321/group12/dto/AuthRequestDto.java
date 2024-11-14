package ca.mcgill.ecse321.group12.dto;

public class AuthRequestDto {

	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

}
