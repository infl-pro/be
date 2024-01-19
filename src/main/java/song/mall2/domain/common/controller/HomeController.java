package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.dto.ProductListDto;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.domain.product.dto.ProductPageDto;
import song.mall2.domain.product.service.ProductService;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final JwtService jwtService;

    @GetMapping("/productList")
    public ResponseEntity<ProductPageDto> getHome(@PageableDefault(size = 6, page = 0, sort = "id", direction = DESC) Pageable pageable,
                                                  @RequestParam(value = "searchCategory", required = false) String searchCategory,
                                                  @RequestParam(value = "searchValue", required = false) String searchValue) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 6, pageable.getSort());
        if (searchValue == null && searchCategory == null) {
            return ResponseEntity.ok(productService.findProductList(pageRequest));
        }
        if (searchCategory == null) {
            return ResponseEntity.ok(productService.findProductListBySearch(pageRequest, searchValue));
        }
        if (searchValue == null) {
            return ResponseEntity.ok(productService.findProductListByCategory(pageRequest, searchCategory));
        }

        return ResponseEntity.ok(productService.findProductListBySearchAndCategory(pageRequest, searchCategory, searchValue));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtService.TokenDto> postRefreshToken(@RequestHeader("Authorization") String accessToken,
                                                                @CookieValue("refreshToken") String refreshToken) {
        JwtService.TokenDto tokenDto = jwtService.reissueToken(accessToken, refreshToken);

        return ResponseEntity.ok(tokenDto);
    }
}
