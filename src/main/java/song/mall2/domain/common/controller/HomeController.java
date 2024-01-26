package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.domain.product.service.ProductService;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/productList")
    public ResponseApi<PageDto, String> getHome(@PageableDefault(size = 6, page = 0, sort = "id", direction = DESC) Pageable pageable,
                                        @RequestParam(value = "searchCategory", required = false) String searchCategory,
                                        @RequestParam(value = "searchValue", required = false) String searchValue) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 6, pageable.getSort());
        if (searchValue == null && searchCategory == null) {
            return new ResponseApi<>(HttpStatus.OK.value(), "상품 목록 조회", productService.findProductList(pageRequest));
        }
        if (searchCategory == null) {
            return new ResponseApi<>(HttpStatus.OK.value(), "상품 목록 조회", productService.findProductListBySearch(pageRequest, searchValue));
        }
        if (searchValue == null) {
            return new ResponseApi<>(HttpStatus.OK.value(), "상품 목록 조회", productService.findProductListByCategory(pageRequest, searchCategory));
        }

        return new ResponseApi<>(HttpStatus.OK.value(), "상품 목록 조회", productService.findProductListBySearchAndCategory(pageRequest, searchValue, searchCategory));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/refreshToken")
    public ResponseApi<JwtService.TokenDto.AccessTokenDto, String> postRefreshToken(@CookieValue("refreshToken") String refreshToken) {
        JwtService.TokenDto.AccessTokenDto tokenDto = jwtService.reissueToken(refreshToken);

        return new ResponseApi<>(HttpStatus.CREATED.value(), "토큰 재발급", tokenDto);
    }
}
