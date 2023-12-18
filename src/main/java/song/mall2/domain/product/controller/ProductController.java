package song.mall2.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import song.mall2.domain.product.dto.ProductIdDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.security.authentication.principal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductIdDto> postSave(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 SaveProductDto saveProductDto) {
        ProductIdDto productId = productService.save(userPrincipal.getId(), saveProductDto);

        return ResponseEntity.ok(productId);
    }
}