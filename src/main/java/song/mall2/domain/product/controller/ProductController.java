package song.mall2.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.product.dto.EditProductDto;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.dto.UpdateProductStockQuantityDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductDto> postSaveProduct(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @RequestBody SaveProductDto saveProductDto) {
        ProductDto productDto = productService.saveProduct(userPrincipal.getId(), saveProductDto);

        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long productId,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.ok(productService.getProduct(productId));
        }

        return ResponseEntity.ok(productService.getProduct(productId, userPrincipal.getId()));
    }

    @GetMapping("/{productId}/editForm")
    public ResponseEntity<EditProductDto> postEditProductForm(@PathVariable("productId") Long productId,
                                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        EditProductDto editForm = productService.getEditForm(productId, userPrincipal.getId());

        return ResponseEntity.ok(editForm);
    }

    @PatchMapping("/{productId}/edit")
    public ResponseEntity<ProductDto> postEditProduct(@PathVariable("productId") Long productId,
                                                      @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @RequestBody EditProductDto editProductDto) {
        ProductDto productDto = productService.editProduct(productId, userPrincipal.getId(), editProductDto);

        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/{productId}/updateStockQuantity")
    public ResponseEntity<Object> postUpdateStockQuantity(@PathVariable("productId") Long productId,
                                                          @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                          @RequestBody UpdateProductStockQuantityDto stockQuantityDto) {
        ProductDto productDto = productService.updateStockQuantity(productId, userPrincipal.getId(), stockQuantityDto.getStockQuantity());

//        return ResponseEntity.ok(productDto);
        return ResponseEntity.ok().build();
    }
}
