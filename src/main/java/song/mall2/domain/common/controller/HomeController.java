package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.dto.AccessTokenResponseDto;
import song.mall2.domain.common.dto.ProductPageDto;
import song.mall2.domain.common.dto.RefreshRequestDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.security.utils.jwt.JwtUtils;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("/home")
    public ResponseEntity<Page<ProductPageDto>> getHome(@PageableDefault(size = 10, page = 0, sort = "id", direction = DESC) Pageable pageable,
                                                        @RequestParam(value = "searchCategory", required = false) String searchCategory,
                                                        @RequestParam(value = "searchValue", required = false) String searchValue) {
        if (searchValue == null && searchCategory == null) {
            return ResponseEntity.ok(productService.findProductList(pageable));
        }
        if (searchCategory == null) {
            return ResponseEntity.ok(productService.findProductListBySearch(pageable, searchValue));
        }
        if (searchValue == null) {
            return ResponseEntity.ok(productService.findProductListByCategory(pageable, searchCategory));
        }

        return ResponseEntity.ok(productService.findProductListBySearchAndCategory(pageable, searchCategory, searchValue));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AccessTokenResponseDto> postRefreshToken(@RequestBody RefreshRequestDto refreshRequestDto) {
        String jwt = JwtUtils.refreshJwt(refreshRequestDto.getRefreshToken());

        return ResponseEntity.ok(new AccessTokenResponseDto(jwt));
    }
}
