/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;

// line 26 "../../../../../../ReindeerGames.ump"
@Entity
public class Employee extends UserRole {

	/**
	 * set permissions for user auth
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"), new SimpleGrantedAuthority("ROLE_USER"));
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	public Employee() {
	}

	// ------------------------
	// CONSTRUCTOR
	// ------------------------
	public Employee(int aId, String aEmail, String aPassword, String aName, String aPhoneNumber) {
		super(aId, aEmail, aPassword, aName, aPhoneNumber);

	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void delete() {
		super.delete();
	}

}