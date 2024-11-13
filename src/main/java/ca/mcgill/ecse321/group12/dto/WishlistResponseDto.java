package ca.mcgill.ecse321.group12.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Wishlist;

public class WishlistResponseDto {

    private int id;

	private List<GameResponseDto> games;

	@SuppressWarnings("unused")
	private WishlistResponseDto() {
	}

	public WishlistResponseDto(Wishlist model) {
		this.id = model.getId();
		this.games = new ArrayList<>();

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
