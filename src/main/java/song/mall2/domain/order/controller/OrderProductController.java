package song.mall2.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.dto.UpdateOrderProductStatusDto;
import song.mall2.domain.order.service.OrderProductService;
import song.mall2.security.authentication.principal.UserPrincipal;

import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/orderProduct")
@RequiredArgsConstructor
public class OrderProductController {
    private final OrderProductService orderProductService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<OrderProductDto>> getOrderProductList(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                     @PathVariable("productId") Long productId) {
        List<OrderProductDto> orderProductList = orderProductService.getOrderProductListByProduct(userPrincipal.getId(), productId);

        return ResponseEntity.ok(orderProductList);
    }

    @PostMapping("/{orderProductId}/update")
    public ResponseEntity<Object> updateOrderProductStatus(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @PathVariable("orderProductId") Long orderProductId,
                                                           @RequestBody UpdateOrderProductStatusDto updateOrderProductStatusDto) {
        orderProductService.updateOrderProductStatus(userPrincipal.getId(), orderProductId, updateOrderProductStatusDto.getStatusName());

        return ResponseEntity.ok().build();
    }
}
