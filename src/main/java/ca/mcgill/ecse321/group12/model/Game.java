/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 37 "../../../../../../ReindeerGames.ump"
@Entity
public class Game {

	// ------------------------
	// ENUMERATIONS
	// ------------------------

	public enum Category {

		Action, Sports, Adventure, Puzzle

	}

	public enum Console {

		PlayStation, XBox, PC, Switch

	}

	public enum GameStatus {

		PendingApproval, InCatalog, PendingArchival, Archived, Rejected

	}

	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static Map<Integer, Game> gamesById = new HashMap<Integer, Game>();

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Attributes
	@Id
	@GeneratedValue
	private int id;

	private Category category;

	private Console console;

	private int inventory;

	private float price;

	private String name;

	private String description;

	private GameStatus status;

	private int year;
  
	private byte[] cover;
  
	private String coverType;

	private byte[] background;

	private String backgroundType;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Game() {
	}

	public Game(int aId, Category aCategory, Console aConsole, int aInventory, float aPrice, String aName,
			String aDescription, GameStatus aStatus, int aYear) {
		category = aCategory;
		console = aConsole;
		inventory = aInventory;
		price = aPrice;
		name = aName;
		description = aDescription;
		status = aStatus;
		year = aYear;
		if (!setId(aId)) {
			throw new RuntimeException(
					"Cannot create due to duplicate id. See https://manual.umple.org?RE003ViolationofUniqueness.html");
		}
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
			gamesById.remove(anOldId);
		}
		gamesById.put(aId, this);
		return wasSet;
	}

	public boolean setCategory(Category aCategory) {
		boolean wasSet = false;
		category = aCategory;
		wasSet = true;
		return wasSet;
	}

	public boolean setConsole(Console aConsole) {
		boolean wasSet = false;
		console = aConsole;
		wasSet = true;
		return wasSet;
	}

	public boolean setInventory(int aInventory) {
		boolean wasSet = false;
		inventory = aInventory;
		wasSet = true;
		return wasSet;
	}

	public boolean setPrice(float aPrice) {
		boolean wasSet = false;
		price = aPrice;
		wasSet = true;
		return wasSet;
	}

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public boolean setDescription(String aDescription) {
		boolean wasSet = false;
		description = aDescription;
		wasSet = true;
		return wasSet;
	}

	public boolean setStatus(GameStatus aStatus) {
		boolean wasSet = false;
		status = aStatus;
		wasSet = true;
		return wasSet;
	}

	public int getId() {
		return id;
	}

	/* Code from template attribute_GetUnique */
	public static Game getWithId(int aId) {
		return gamesById.get(aId);
	}

	/* Code from template attribute_HasUnique */
	public static boolean hasWithId(int aId) {
		return getWithId(aId) != null;
	}

	public Category getCategory() {
		return category;
	}

	public Console getConsole() {
		return console;
	}

	public int getInventory() {
		return inventory;
	}

	public float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void delete() {
		gamesById.remove(getId());
	}

	public int getYear() {
		return year;
	}

	public boolean setYear(int year) {
		this.year = year;
		return true;
	}

	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "," + "inventory" + ":" + getInventory() + "," + "price"
				+ ":" + getPrice() + "," + "name" + ":" + getName() + "," + "description" + ":" + getDescription() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "category" + "="
				+ (getCategory() != null
						? !getCategory().equals(this) ? getCategory().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "console" + "="
				+ (getConsole() != null
						? !getConsole().equals(this) ? getConsole().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "status" + "="
				+ (getStatus() != null
						? !getStatus().equals(this) ? getStatus().toString().replaceAll("  ", "    ") : "this"
						: "null");
	}

	public boolean setCover(byte[] cover) {
		this.cover = cover;
		return true;
	}

	public byte[] getCover() {
		return this.cover;
	}

	public boolean setBackground(byte[] background) {
		this.background = background;
		return true;
	}

	public byte[] getBackground() {
		return this.background;
	}

	public boolean setCoverType(String coverType) {
		this.coverType = coverType;
		return true;
	}

	public String getCoverType() {
		return this.coverType;
	}

	public boolean setBackgroundType(String backgroundType) {
		this.backgroundType = backgroundType;
		return true;
	}

	public String getBackgroundType() {
		return this.backgroundType;
	}

}