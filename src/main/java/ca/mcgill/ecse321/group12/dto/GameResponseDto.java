package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;

public class GameResponseDto {

	private Category category;

	private Console console;

	private int inventory;

	private float price;

	private String name;

	private String description;

	private GameStatus status;

	private int id;

	private int year;

	@SuppressWarnings("unused")
	private GameResponseDto() {
	}

	public GameResponseDto(Game model) {
		this.category = model.getCategory();
		this.console = model.getConsole();
		this.inventory = model.getInventory();
		this.price = model.getPrice();
		this.name = model.getName();
		this.description = model.getDescription();
		this.status = model.getStatus();
		this.id = model.getId();
		this.year = model.getYear();
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

	public int getId() {
		return this.id;
	}

	public int getYear() {
		return this.year;
	}
}
