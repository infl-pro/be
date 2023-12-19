package song.mall2.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import song.mall2.domain.order.dto.OrdersIdDto;
import song.mall2.domain.order.dto.SaveOrdersDto;
import song.mall2.domain.order.service.OrderService;
import song.mall2.security.authentication.principal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrdersIdDto> postSaveOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody SaveOrdersDto saveOrdersDto) {
        OrdersIdDto ordersIdDto = orderService.saveOrders(userPrincipal.getId(), saveOrdersDto.getSaveOrderProductDtoList());

        return ResponseEntity.ok(ordersIdDto);
    }
}
