/**
 * same as CustomerResponseDto, but also provides an auth token.
 */
package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Wishlist;

public class CustomerCreateResponseDto {

	private int id;

	private String email;

	private String name;

	private String phoneNumber;

	private CartResponseDto cart;

	private Wishlist wishlist;

	private String token;

	private String address;

	@SuppressWarnings("unused")
	private CustomerCreateResponseDto() {
	}

	public CustomerCreateResponseDto(Customer model) {
		this.id = model.getId();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
		this.cart = new CartResponseDto(model.getCart());
		this.wishlist = model.getWishlist();
		this.address = model.getAddress();
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

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CartResponseDto getCart() {
		return cart;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}