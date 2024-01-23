package song.mall2.domain.orderproduct.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.orderproduct.service.OrderProductService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/orderProduct")
@RequiredArgsConstructor
public class OrderProductController {
    private final OrderProductService orderProductService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productId}")
    public ResponseApi<PageDto> getOrderProductList(@PathVariable("productId") Long productId,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                    @PageableDefault(page = 0) Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 5);
        PageDto orderProductList = orderProductService.getOrderProductList(productId, userPrincipal.getId(), pageRequest);

        return new ResponseApi<>(HttpStatus.OK.value(), "주문된 상품 조회", orderProductList);
    }
}
