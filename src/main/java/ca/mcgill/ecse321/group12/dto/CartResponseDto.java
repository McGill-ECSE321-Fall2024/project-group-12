package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Game;
import java.util.*;

public class CartResponseDto {

	private int id;

	private List<GameResponseDto> games;

	@SuppressWarnings("unused")
	private CartResponseDto() {
	}

	public CartResponseDto(Cart model) {
		this.id = model.getId();
		this.games = new ArrayList<GameResponseDto>();

		for (Game game : model.getGames()) {
			this.games.add(new GameResponseDto(game));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<GameResponseDto> getGames() {
		return games;
	}

	public void setGames(List<GameResponseDto> games) {
		this.games = games;
	}

}
