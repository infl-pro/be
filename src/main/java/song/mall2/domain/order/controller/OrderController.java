package song.mall2.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.order.dto.OrderFormDto;
import song.mall2.domain.orderproduct.dto.OrderProductListDto;
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
    @GetMapping("/form")
    public ResponseEntity<ResponseApi> getOrderForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                    @RequestBody List<CartIdDto> cartIdList) {
        OrderFormDto orderRequest = orderService.getOrderForm(userPrincipal.getId(), cartIdList);

        return ResponseEntity.ok(new ResponseApi(true, "주문 폼 생성", orderRequest));
    }

    @GetMapping
    public ResponseEntity<ResponseApi> getOrderList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<OrderProductListDto> orderProductList = orderService.getOrderList(userPrincipal.getId());

        return ResponseEntity.ok(new ResponseApi(true, "주문 내역 조회", orderProductList));
    }

    @GetMapping("/{ordersId}")
    public ResponseEntity<ResponseApi> getOrders(@PathVariable("ordersId") Long ordersId,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        OrdersDto orders = orderService.getOrders(userPrincipal.getId(), ordersId);

        return ResponseEntity.ok(new ResponseApi(true, "주문 상세 조회", orders));
    }

    @PostMapping("/{ordersId}/cancel")
    public ResponseEntity<ResponseApi> postCancelOrders(@PathVariable("ordersId") Long ordersId,
                                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        OrdersDto orders = orderService.cancelOrders(ordersId, userPrincipal.getId());

        return ResponseEntity.ok(new ResponseApi(true, "주문 취소 성공", orders));
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
