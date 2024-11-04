package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import java.util.*;

public class CartResponseDto {

	private int id;

	private List<Game> games;

	@SuppressWarnings("unused")
	private CartResponseDto() {
	}

	public CartResponseDto(Cart model) {
		this.id = model.getId();
		this.games = model.getGames();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}
