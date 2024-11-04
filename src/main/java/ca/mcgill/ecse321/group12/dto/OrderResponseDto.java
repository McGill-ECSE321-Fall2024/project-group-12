package ca.mcgill.ecse321.group12.dto;

import java.util.Date;

import ca.mcgill.ecse321.group12.model.Order;
import ca.mcgill.ecse321.group12.model.Order.OrderStatus;

public class OrderResponseDto {

	// fields from the order
	private int id;

	private Date purchaseDate;

	private String deliveryAddress;

	private OrderStatus status;

	// associations?

	@SuppressWarnings("unused")
	private OrderResponseDto() {
	}

	public OrderResponseDto(Order order) {

		id = order.getId();
		purchaseDate = order.getPurchaseDate();
		deliveryAddress = order.getDeliveryAddress();
		status = order.getStatus();

	}

}
