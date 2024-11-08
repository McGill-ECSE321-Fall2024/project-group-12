package ca.mcgill.ecse321.group12.dto;

public class OrderRequestDto {

	// TEMPORARY: should be replaced by current auth'd user's cart
	private int customerId;

	// needs order properties that won't be inferred from cart
	private String deliveryAddress;

	// needs card payment info to create the associated card payment
	private String nameOnCard;

	private String cvc;

	private String cardNumber;

	private String billingAddress;

	private boolean isSaved;

	private String expiryDate;

	@SuppressWarnings("unused")
	private OrderRequestDto() {
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public String getCvc() {
		return cvc;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public boolean getIsSaved() {
		return isSaved;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setCustomerId(int newId) {
		customerId = newId;
	}

	public void setDeliveryAddress(String newDeliveryAddress) {
		deliveryAddress = newDeliveryAddress;
	}

	public void setNameOnCard(String newNameOnCard) {
		nameOnCard = newNameOnCard;
	}

	public void setCvc(String newCvc) {
		cvc = newCvc;
	}

	public void setCardNumber(String newCardNumber) {
		cardNumber = newCardNumber;
	}

	public void setBillingAddress(String newBillingAddress) {
		billingAddress = newBillingAddress;
	}

	public void setIsSaved(boolean newIsSaved) {
		isSaved = newIsSaved;
	}

	public void setExpiryDate(String newExpiryDate) {
		expiryDate = newExpiryDate;
	}

}
