package ca.mcgill.ecse321.group12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.group12.dto.OrderRequestDto;
import ca.mcgill.ecse321.group12.dto.OrderResponseDto;
import ca.mcgill.ecse321.group12.service.OrderService;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/order")
  public OrderResponseDto createOrder (@Validated @RequestBody OrderRequestDto body) {

    // #1: get cart.
    // #2: verify user payment info
    // #3: check all games in cart are in stock. if they are, subtract 1 from stock. If not, order FAILS
    // #4: empty user's cart
    // #5: create order object

    return new OrderResponseDto(1);

  }

}
