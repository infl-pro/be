package song.mall2.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public ResponseApi<ProductDto, String> postSaveProduct(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @Valid @ModelAttribute SaveProductDto saveProductDto) {
        ProductDto productDto = productService.saveProduct(userPrincipal.getId(), saveProductDto);

        return new ResponseApi<>(HttpStatus.CREATED.value(), "상품 등록", productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productId}")
    public ResponseApi<ProductDto, String> getProduct(@PathVariable("productId") Long productId,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return new ResponseApi<>(HttpStatus.OK.value(), "상품 조회", productService.getProduct(productId));
        }

        return new ResponseApi<>(HttpStatus.OK.value(), "상품 조회", productService.getProduct(productId, userPrincipal.getId()));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{productId}/edit")
    public ResponseApi<ProductDto, String> postEditProduct(@PathVariable("productId") Long productId,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @Valid @ModelAttribute SaveProductDto saveProductDto) {
        ProductDto productDto = productService.editProduct(productId, userPrincipal.getId(), saveProductDto);

        return new ResponseApi<>(HttpStatus.OK.value(), "상품 수정", productDto);
    }
}
