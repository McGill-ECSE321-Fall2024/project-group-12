/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;

// line 49 "../../../../../../ReindeerGames.ump"
@Entity
public class Wishlist {

	public Wishlist() {
	}
	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static Map<Integer, Wishlist> wishlistsById = new HashMap<Integer, Wishlist>();

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Wishlist Attributes
	@Id
	@GeneratedValue
	private int id;

	// Wishlist Associations
	@ManyToMany
	private List<Game> games = new ArrayList<>(); //

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Wishlist(int aId) {
		if (!setId(aId)) {
			throw new RuntimeException(
					"Cannot create due to duplicate id. See https://manual.umple.org?RE003ViolationofUniqueness.html");
		}
		games = new ArrayList<Game>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setId(int aId) {
		boolean wasSet = false;
		Integer anOldId = getId();
		if (anOldId != null && anOldId.equals(aId)) {
			return true;
		}
		if (hasWithId(aId)) {
			return wasSet;
		}
		id = aId;
		wasSet = true;
		if (anOldId != null) {
			wishlistsById.remove(anOldId);
		}
		wishlistsById.put(aId, this);
		return wasSet;
	}

	public int getId() {
		return id;
	}

	/* Code from template attribute_GetUnique */
	public static Wishlist getWithId(int aId) {
		return wishlistsById.get(aId);
	}

	/* Code from template attribute_HasUnique */
	public static boolean hasWithId(int aId) {
		return getWithId(aId) != null;
	}

	/* Code from template association_GetMany */
	public Game getGame(int index) {
		Game aGame = games.get(index);
		return aGame;
	}

	public List<Game> getGames() {
		List<Game> newGames = Collections.unmodifiableList(games);
		return newGames;
	}

	public int numberOfGames() {
		int number = games.size();
		return number;
	}

	public boolean hasGames() {
		boolean has = games.size() > 0;
		return has;
	}

	public int indexOfGame(Game aGame) {
		int index = games.indexOf(aGame);
		return index;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfGames() {
		return 0;
	}

	/* Code from template association_AddUnidirectionalMany */
	public boolean addGame(Game aGame) {
		boolean wasAdded = false;
		if (games.contains(aGame)) {
			return false;
		}
		games.add(aGame);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeGame(Game aGame) {
		boolean wasRemoved = false;
		if (games.contains(aGame)) {
			games.remove(aGame);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addGameAt(Game aGame, int index) {
		boolean wasAdded = false;
		if (addGame(aGame)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfGames()) {
				index = numberOfGames() - 1;
			}
			games.remove(aGame);
			games.add(index, aGame);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveGameAt(Game aGame, int index) {
		boolean wasAdded = false;
		if (games.contains(aGame)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfGames()) {
				index = numberOfGames() - 1;
			}
			games.remove(aGame);
			games.add(index, aGame);
			wasAdded = true;
		}
		else {
			wasAdded = addGameAt(aGame, index);
		}
		return wasAdded;
	}

	public void delete() {
		wishlistsById.remove(getId());
		games.clear();
	}

	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "]";
	}

}