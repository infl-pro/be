package song.mall2.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.product.dto.EditProductDto;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import java.net.URI;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ResponseApi> postSaveProduct(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @RequestBody SaveProductDto saveProductDto) {
        ProductDto productDto = productService.saveProduct(userPrincipal.getId(), saveProductDto);

        return ResponseEntity.created(URI.create("/product/" + productDto.getProductId())).body(new ResponseApi(true, "상품 등록 성공", productDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseApi> getProduct(@PathVariable("productId") Long productId,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.ok(new ResponseApi(true, "상품 조회", productService.getProduct(productId)));
        }

        return ResponseEntity.ok(new ResponseApi(true, "상품 조회", productService.getProduct(productId, userPrincipal.getId())));
    }

    @PatchMapping("/{productId}/edit")
    public ResponseEntity<ResponseApi> postEditProduct(@PathVariable("productId") Long productId,
                                                      @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @RequestBody EditProductDto editProductDto) {
        ProductDto productDto = productService.editProduct(productId, userPrincipal.getId(), editProductDto);

        return ResponseEntity.ok(new ResponseApi(true, "상품 수정 성공", productDto));
    }
}
