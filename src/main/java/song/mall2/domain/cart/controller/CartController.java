package song.mall2.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.CartDto;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.dto.SaveCartDto;
import song.mall2.domain.cart.dto.UpdateCartQuantity;
import song.mall2.domain.cart.service.CartService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<CartDto> cartList = cartService.getCartList(userPrincipal.getId());

        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> postAddCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody SaveCartDto saveCartDto) {
        CartDto cartDto = cartService.addCart(userPrincipal.getId(), saveCartDto.getProductId(), saveCartDto.getQuantity());

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> postDeleteCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody List<CartIdDto> cartIdDtoList) {
        cartService.deleteCart(userPrincipal.getId(), cartIdDtoList);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateQuantity")
    public ResponseEntity<CartDto> postUpdateQuantity(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody UpdateCartQuantity cartQuantity) {
        CartDto cartDto = cartService.updateCartQuantity(userPrincipal.getId(), cartQuantity.getCartId(), cartQuantity.getQuantity());

        return ResponseEntity.ok(cartDto);
    }
}
