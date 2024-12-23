/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;

// line 21 "../../../../../../ReindeerGames.ump"
@Entity
public class Manager extends UserRole {

	private UserType userType = UserType.MANAGER;

	public UserType getUserType() {
		return userType;
	}

	/**
	 * set permissions for user auth
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"), new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
				new SimpleGrantedAuthority("ROLE_USER"));
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Manager() {
	}

	public Manager(int aId, String aEmail, String aPassword, String aName, String aPhoneNumber) {
		super(aId, aEmail, aPassword, aName, aPhoneNumber);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void delete() {
		super.delete();
	}

}