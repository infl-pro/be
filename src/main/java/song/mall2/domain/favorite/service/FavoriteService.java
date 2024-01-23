package song.mall2.domain.favorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.favorite.dto.FavoriteDto;
import song.mall2.domain.favorite.entity.Favorite;
import song.mall2.domain.favorite.repository.FavoriteJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final FavoriteJpaRepository favoriteRepository;

    @Transactional
    public FavoriteDto addFavorite(Long userId, Long productId) {
        User user = getUser(userId);
        Product product = getProduct(productId);
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndProductId(user.getId(), product.getId());
        if (optionalFavorite.isPresent()) {
            Favorite favorite = optionalFavorite.get();

            return new FavoriteDto(favorite.getId(), favorite.getUser().getId(), favorite.getUser().getUsername(),
                    favorite.getProduct().getId(), favorite.getProduct().getName());
        }

        Favorite favorite = favoriteRepository.save(Favorite.create(user, product));

        return new FavoriteDto(favorite.getId(), favorite.getUser().getId(), favorite.getUser().getUsername(),
                favorite.getProduct().getId(), favorite.getProduct().getName());
    }

    @Transactional
    public void deleteFavorite(Long userId, Long productId) {
        User user = getUser(userId);
        Product product = getProduct(productId);
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndProductId(user.getId(), product.getId());
        if (optionalFavorite.isPresent()) {
            Favorite favorite = optionalFavorite.get();
            favoriteRepository.delete(favorite);
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }
}
