package ca.mcgill.ecse321.group12.dto;

public class WishlistRequestDto {
    private int id;

	private int gameId;

	public WishlistRequestDto() {
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
