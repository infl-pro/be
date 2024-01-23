package song.mall2.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.favorite.dto.FavoriteDto;
import song.mall2.domain.favorite.service.FavoriteService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{productId}")
    public ResponseApi<FavoriteDto> postFavorite(@PathVariable("productId") Long productId,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        FavoriteDto favorite = favoriteService.addFavorite(userPrincipal.getId(), productId);

        return new ResponseApi<>(HttpStatus.OK.value(), "좋아요 저장", favorite);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{productId}")
    public ResponseApi<Object> deleteFavorite(@PathVariable("productId") Long productId,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        favoriteService.deleteFavorite(userPrincipal.getId(), productId);

        return new ResponseApi<>(HttpStatus.OK.value(), "좋아요 삭제", null);
    }
}
