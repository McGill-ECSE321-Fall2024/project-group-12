package ca.mcgill.ecse321.group12.dto;

public class ChangePasswordDto {

	private String email;

	private String oldPassword;

	private String newPassword;

	@SuppressWarnings("unused")
	private ChangePasswordDto() {
	}

	public ChangePasswordDto(String email, String oldPassword, String newPassword) {
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

}
