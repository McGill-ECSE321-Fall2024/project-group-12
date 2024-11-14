package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

public class GameRequestDto {

	private Category category;

	private Console console;

	private int inventory;

	private float price;

	private String name;

	private String description;

	private GameStatus status;

	@SuppressWarnings("unused")
	private GameRequestDto() {
	}

	public GameRequestDto(Category category, Console console, int inventory, float price, String name,
			String description, GameStatus status) {
		this.category = category;
		this.console = console;
		this.inventory = inventory;
		this.price = price;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public Category getCategory() {
		return this.category;
	}

	public Console getConsole() {
		return this.console;
	}

	public int getInventory() {
		return this.inventory;
	}

	public float getPrice() {
		return this.price;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public GameStatus getStatus() {
		return this.status;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void getConsole(Console console) {
		this.console = console;
	}

	public void getInventory(int inventory) {
		this.inventory = inventory;
	}

	public void getPrice(float price) {
		this.price = price;
	}

	public void getName(String name) {
		this.name = name;
	}

	public void getDescription(String description) {
		this.description = description;
	}

	public void getStatus(GameStatus status) {
		this.status = status;
	}

}
