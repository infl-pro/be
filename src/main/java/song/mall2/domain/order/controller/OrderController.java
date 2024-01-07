package song.mall2.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.order.dto.OrderFormDto;
import song.mall2.domain.order.dto.OrderProductListDto;
import song.mall2.domain.order.dto.OrdersDto;
import song.mall2.domain.order.service.OrderService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/form")
    public ResponseEntity<OrderFormDto> getOrderForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody List<CartIdDto> cartIdList) {
        OrderFormDto orderRequest = orderService.getOrderForm(userPrincipal.getId(), cartIdList);

        return ResponseEntity.ok(orderRequest);
    }

    @GetMapping
    public ResponseEntity<List<OrderProductListDto>> getOrderList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<OrderProductListDto> orderProductList = orderService.getOrderList(userPrincipal.getId());
//        List<OrderProductListDto> orderProductList = orderService.getOrderList(1L);

        return ResponseEntity.ok(orderProductList);
    }

    @GetMapping("/{ordersId}")
    public ResponseEntity<OrdersDto> getOrders(@PathVariable("ordersId") Long ordersId,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        OrdersDto orders = orderService.getOrders(userPrincipal.getId(), ordersId);
//        OrdersDto orders = orderService.getOrders(1L, ordersId);

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{ordersId}/cancel")
    public ResponseEntity<OrdersDto> postCancelOrders(@PathVariable("ordersId") Long ordersId,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        OrdersDto ordersDto = orderService.cancelOrders(ordersId, userPrincipal.getId());
//        OrdersDto ordersDto = orderService.cancelOrders(ordersId, 1L);

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/carttest")
    public ResponseEntity<OrderFormDto> getCarttestOrder() {
        List<CartIdDto> cartIdList = new ArrayList<>();
        cartIdList.add(new CartIdDto(1L));
        cartIdList.add(new CartIdDto(2L));
        OrderFormDto orderRequest = orderService.getOrderForm(1L, cartIdList);

        return ResponseEntity.ok(orderRequest);
    }
}
