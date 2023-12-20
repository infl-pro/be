package song.mall2.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.CartDto;
import song.mall2.domain.cart.dto.SaveCartDto;
import song.mall2.domain.cart.service.CartService;
import song.mall2.security.authentication.principal.UserPrincipal;

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
    public ResponseEntity<Object> postAddCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody SaveCartDto saveCartDto) {
        cartService.addCart(userPrincipal.getId(), saveCartDto.getProductId(), saveCartDto.getQuantity());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{cartId}")
    public ResponseEntity<Object> postDeleteCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @PathVariable("cartId") Long cartId) {
        cartService.deleteCart(userPrincipal.getId(), cartId);

        return ResponseEntity.ok().build();
    }
}
