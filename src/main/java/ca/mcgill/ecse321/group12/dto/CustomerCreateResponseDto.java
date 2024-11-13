/**
 * same as CustomerResponseDto, but also provides an auth token.
 */
package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;

public class CustomerCreateResponseDto {

	private int id;

	private String email;

	private String name;

	private String phoneNumber;

	private Cart cart;

  private String token;

	@SuppressWarnings("unused")
	private CustomerCreateResponseDto() {
	}

	public CustomerCreateResponseDto(Customer model) {
		this.id = model.getId();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
		this.cart = model.getCart();
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
  
  public String getToken() {
    return token;
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

  public void setToken(String token) {
    this.token = token;
  }

	public Cart getCart() {
		return cart;
	}

}