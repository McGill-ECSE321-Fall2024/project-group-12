package ca.mcgill.ecse321.group12.dto;

public class OrderRequestDto {

  // TEMPORARY: should be replaced by current auth'd user's cart
  private int cartId;
  
  @SuppressWarnings("unused")
  private OrderRequestDto() {}

  public int getCartId () {
    return cartId;
  }

  public void setCardId (int newId) {
    cartId = newId;
  }

}
