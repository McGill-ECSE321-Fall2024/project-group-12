package ca.mcgill.ecse321.group12.dto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.model.Customer;

public class CustomerRequestDto {

	private String password;

	private String email;

	private String name;

	private String phoneNumber;

	private Cart cart;

	private Wishlist wishlist;

	@SuppressWarnings("unused")
	private CustomerRequestDto() {
	}

	public CustomerRequestDto(Customer model) {
		this.password = model.getPassword();
		this.email = model.getEmail();
		this.name = model.getName();
		this.phoneNumber = model.getPhoneNumber();
		this.cart = model.getCart();
		this.wishlist = model.getWishlist();
	}

	/**
	 * Constructor
	 * @author Carmin Vidé
	 * @param email The email for the customer being created
	 * @param password The password for the customer being created
	 * @param name The name for the customer being created
	 * @param phoneNumber The phone number for the customer being created
	 * no cart or wishlist is passed in as they are created empty when a customer is created
	 */
	public CustomerRequestDto(String email, String password, String name, String phoneNumber) {
		this.password = password;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
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

	public Cart getCart() {
		return cart;
	}

	public Wishlist getWishlist() {
		return wishlist;
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

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

}
