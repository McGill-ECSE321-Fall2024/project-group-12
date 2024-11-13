package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.AuthRequestDto;
import ca.mcgill.ecse321.group12.dto.AuthResponseDto;
import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.UserRole;
import ca.mcgill.ecse321.group12.service.AuthService;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  /**
   * attempt to sign in
   * @author James Madden
   */
  @PostMapping("/auth/signin")
  public AuthResponseDto signIn(@RequestBody AuthRequestDto body) {
    
    // find the user from the email
    UserRole user = authService.getUserFromEmail(body.getEmail());

    // encrypt the password sent, and see if they match
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (!encoder.matches(body.getPassword(), user.getPassword())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, "password is incorrect.");
    }

    // if the password is correct, we can return a token!
    String token = authService.generateAuthToken(user);
    
    // return the new token
    AuthResponseDto resp = new AuthResponseDto();
    resp.setToken(token);
    resp.setUserType(user.getUserType());
   
    return resp;

  }

}
