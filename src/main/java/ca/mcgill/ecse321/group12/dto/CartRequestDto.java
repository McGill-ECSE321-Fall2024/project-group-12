package ca.mcgill.ecse321.group12.dto;

public class CartRequestDto {

	private int id;

	private int gameId;

	private CartRequestDto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}
