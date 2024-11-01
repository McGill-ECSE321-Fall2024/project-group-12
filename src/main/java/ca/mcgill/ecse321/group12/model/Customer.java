/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

// line 3 "../../../../../../ReindeerGames.ump"
@Entity
public class Customer extends UserRole {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Customer Associations
	@OneToOne
	private Wishlist wishlist;

	@OneToOne
	private Cart cart;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Customer() {
	}

	public Customer(int aId, String aEmail, String aPassword, String aName, String aPhoneNumber, Wishlist aWishlist,
			Cart aCart) {
		super(aId, aEmail, aPassword, aName, aPhoneNumber);
		if (!setWishlist(aWishlist)) {
			throw new RuntimeException(
					"Unable to create Customer due to aWishlist. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setCart(aCart)) {
			throw new RuntimeException(
					"Unable to create Customer due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public Wishlist getWishlist() {
		return wishlist;
	}

	/* Code from template association_GetOne */
	public Cart getCart() {
		return cart;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setWishlist(Wishlist aNewWishlist) {
		boolean wasSet = false;
		if (aNewWishlist != null) {
			wishlist = aNewWishlist;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setCart(Cart aNewCart) {
		boolean wasSet = false;
		if (aNewCart != null) {
			cart = aNewCart;
			wasSet = true;
		}
		return wasSet;
	}

	public void delete() {
		wishlist = null;
		cart = null;
		super.delete();
	}

}