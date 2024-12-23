package ca.mcgill.ecse321.group12.exception;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ca.mcgill.ecse321.group12.dto.ErrorDto;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorDto> handleCustomException(CustomException e) {
		return new ResponseEntity<ErrorDto>(new ErrorDto(e.getMessage()), e.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ArrayList<String> errorMessages = new ArrayList<String>();
		for (ObjectError err : ex.getAllErrors()) {
			errorMessages.add(err.getDefaultMessage());
		}
		ErrorDto responseBody = new ErrorDto(errorMessages);
		return new ResponseEntity<ErrorDto>(responseBody, HttpStatus.BAD_REQUEST);
	}

}
