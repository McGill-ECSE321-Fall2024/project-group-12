package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Order.OrderStatus;

public class OrderReturnRequestDto {

	// the status to set the order to (only returned will work, anything else is not
	// allowed)
	private OrderStatus status;

	@SuppressWarnings("unused")
	private OrderReturnRequestDto() {
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus newStatus) {
		status = newStatus;
	}

}
