package song.mall2.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.cart.dto.SaveCartDto;
import song.mall2.domain.cart.service.CartService;
import song.mall2.security.authentication.principal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public void getCart() {

    }

    @PostMapping("/add")
    public ResponseEntity<Object> postAddCart(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody SaveCartDto saveCartDto) {
        cartService.addCart(userPrincipal.getId(), saveCartDto.getProductId(), saveCartDto.getQuantity());

        return ResponseEntity.ok().build();
    }
}
