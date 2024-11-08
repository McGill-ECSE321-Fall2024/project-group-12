package ca.mcgill.ecse321.group12.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class CustomException extends RuntimeException {

	@NonNull
	private HttpStatus status;

	public CustomException(@NonNull HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	@NonNull
	public HttpStatus getStatus() {
		return status;
	}

}