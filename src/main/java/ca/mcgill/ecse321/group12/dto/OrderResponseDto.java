package ca.mcgill.ecse321.group12.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.group12.model.Order;
import ca.mcgill.ecse321.group12.model.Order.OrderStatus;
import ca.mcgill.ecse321.group12.model.Game;

public class OrderResponseDto {

	// fields from the order
	private int id;

	private Date purchaseDate;

	private String deliveryAddress;

	private OrderStatus status;

	private List<GameResponseDto> games;

	// associations?

	@SuppressWarnings("unused")
	private OrderResponseDto() {
	}

	public OrderResponseDto(Order order) {

		id = order.getId();
		purchaseDate = order.getPurchaseDate();
		deliveryAddress = order.getDeliveryAddress();
		status = order.getStatus();
		games = new ArrayList<GameResponseDto>();

		for (Game game : order.getGames()) {
			this.games.add(new GameResponseDto(game));
		}

	}

	public int getId() {
		return id;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public List<GameResponseDto> getGames() {
		return games;
	}

	public void setId(int newId) {
		id = newId;
	}

	public void setPurchaseDate(Date newPurchaseDate) {
		purchaseDate = newPurchaseDate;
	}

	public void setDeliveryAddress(String newDeliveryAddress) {
		deliveryAddress = newDeliveryAddress;
	}

	public void setStatus(OrderStatus newStatus) {
		status = newStatus;
	}

	public void setGames(List<GameResponseDto> newGames) {
		games = newGames;
	}

}
