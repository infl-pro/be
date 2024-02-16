package song.mall2.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.*;
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
    public ResponseApi<List<CartDto>, String> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<CartDto> cartList = cartService.getCartList(userPrincipal.getId());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 조회", cartList);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ResponseApi<CartDto, String> postAddCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @Valid @RequestBody SaveCartDto saveCartDto) {
        CartDto cartDto = cartService.addCart(userPrincipal.getId(), saveCartDto.getProductId(), saveCartDto.getQuantity());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 추가", cartDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public ResponseApi<Object, String> postDeleteCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @Valid @RequestBody List<CartIdDto> cartIdList) {
        cartService.deleteCart(userPrincipal.getId(), cartIdList);

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 삭제", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{cartId}")
    public ResponseApi<Object, String> deleteCart(@PathVariable Long cartId,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.deleteCart(userPrincipal.getId(), cartId);

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 삭제", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateQuantity")
    public ResponseApi<CartDto, String> postUpdateQuantity(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @Valid @RequestBody UpdateCartQuantity cartQuantity) {
        CartDto cartDto = cartService.updateCartQuantity(userPrincipal.getId(), cartQuantity.getCartId(), cartQuantity.getQuantity());

        return new ResponseApi<>(HttpStatus.OK.value(), "장바구니 수량 수정", cartDto);
    }
}
