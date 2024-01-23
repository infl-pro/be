package song.mall2.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.CartDto;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.dto.SaveCartDto;
import song.mall2.domain.cart.dto.UpdateCartQuantity;
import song.mall2.domain.cart.service.CartService;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseApi<List<CartDto>> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<CartDto> cartList = cartService.getCartList(userPrincipal.getId());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 조회", cartList);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ResponseApi<CartDto> postAddCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody SaveCartDto saveCartDto) {
        CartDto cartDto = cartService.addCart(userPrincipal.getId(), saveCartDto.getProductId(), saveCartDto.getQuantity());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 추가", cartDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public ResponseApi<Object> postDeleteCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody List<CartIdDto> cartIdDtoList) {
        cartService.deleteCart(userPrincipal.getId(), cartIdDtoList);

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 삭제", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateQuantity")
    public ResponseApi<CartDto> postUpdateQuantity(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @RequestBody UpdateCartQuantity cartQuantity) {
        CartDto cartDto = cartService.updateCartQuantity(userPrincipal.getId(), cartQuantity.getCartId(), cartQuantity.getQuantity());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 수량 수정", cartDto);
    }
}
