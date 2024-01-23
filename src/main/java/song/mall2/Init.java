package song.mall2;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.favorite.entity.Favorite;
import song.mall2.domain.favorite.repository.FavoriteJpaRepository;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.orderproduct.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.review.entity.Review;
import song.mall2.domain.review.repository.ReviewJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.repository.UserRoleJpaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static song.mall2.domain.user.entity.UserRole.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Init {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.setData();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final UserRoleJpaRepository userRoleRepository;
        private final ProductJpaRepository productRepository;
        private final CartJpaRepository cartRepository;
        private final OrdersJpaRepository ordersRepository;
        private final OrderProductJpaRepository orderProductRepository;
        private final PaymentJpaRepository paymentRepository;
        private final ReviewJpaRepository reviewRepository;
        private final FavoriteJpaRepository favoriteRepository;

        @Transactional
        public void setData() {
            User userA = saveUser("a", "a", "nameA", "test1@email.com");
            grantRoleUser(userA);
//            grantRoleSeller(userA);
            User lmood = saveUser("lmood", "lmood", "엘무드", null);
            User lkmost = saveUser("lkmost", "lkmost", "라이크 더 모스트", null);
            User matinkim = saveUser("mtkim", "mtkim", "마뗑킴", null);
            User eightseconds = saveUser("eightseconds", "eightseconds", "8seconds", null);

            Product lmoodP1 = saveProduct(lmood, "화란 니트 인디핑크", 100, "엘무드 니트 인디핑크", "/file/downloadFile/lmood1-1.jpg", 100, Product.Category.TOP);
            Product lmoodP2 = saveProduct(lmood, "후드 크림", 300, "엘무드 후드 크림", "/file/downloadFile/lmood2-1.jpg", 200, Product.Category.TOP);
            Product lmoodP3 = saveProduct(lmood, "맨투맨 드라이 라벤더", 200, "엘무드 맨투맨 드라이 라벤더", "/file/downloadFile/lmood3-1.jpg", 100, Product.Category.TOP);
            Product lmoodP4 = saveProduct(lmood, "화란 니트 칸초", 100, "엘무드 니트 칸초", "/file/downloadFile/lmood4-1.jpg", 100, Product.Category.TOP);
            Product lmoodP5 = saveProduct(lmood, "화란 가디건 블랙", 250, "엘무드 가디건 블랙", "/file/downloadFile/lmood5-1.jpg", 150, Product.Category.OUTER);


            addCart(userA, lmoodP1, 10);
            addCart(userA, lmoodP2, 10);

            Orders orders = saveOrder(userA, lmoodP1, 10);

            saveReview(orders, userA);
            Favorite userAFavorite1 = addFavorite(userA, lmoodP1);
            Favorite userAFavorite2 = addFavorite(userA, lmoodP2);
            Favorite userAFavorite3 = addFavorite(userA, lmoodP3);
        }

        private Favorite addFavorite(User user, Product product) {
            Favorite favorite = Favorite.create(user, product);

            return favoriteRepository.save(favorite);
        }

        private User saveUser(String username, String password, String name, String email) {
            User user = User.create(username, passwordEncoder.encode(password), name, email);

            return userRepository.save(user);
        }

        private void grantRoleUser(User user) {
            UserRole userRole = UserRole.create(user, ROLE_USER.name());
            userRoleRepository.save(userRole);
        }

        private Product saveProduct(User userId, String name, Integer price, String description, String thumbnailUrl, Integer stockQuantity, Product.Category category) {
            Product product = Product.create(userId, name, price, description, thumbnailUrl, stockQuantity, category.name());

            return productRepository.save(product);
        }

        private Cart addCart(User user, Product product, Integer quantity) {
            Cart cart = Cart.create(user, product, quantity);

            return cartRepository.save(cart);
        }

        private Orders saveOrder(User user, Product product, Integer quantity) {
            Payment payment = Payment.of(user, "testPayment", "PAID", product.getPrice() * quantity, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(LocalDateTime.now()), null, null,
                    "korea seoul");
            Payment savePayment = paymentRepository.save(payment);

            Orders orders = Orders.create(user, savePayment);

            List<OrderProduct> orderProductList = new ArrayList<>();
            OrderProduct orderProduct = OrderProduct.create(orders, product, user, quantity);
            orderProductList.add(orderProduct);

            Orders saveOrders = ordersRepository.save(orders);
            List<OrderProduct> saveOrderProductList = orderProductRepository.saveAll(orderProductList);

            return saveOrders;
        }

        private void saveReview(Orders orders, User userA) {
            List<OrderProduct> orderProductList = orderProductRepository.findAllByOrdersId(orders.getId());
            for (OrderProduct orderProduct : orderProductList) {
                Review review = Review.create(userA, orderProduct, "testReview", 5);
                Review saveReview = reviewRepository.save(review);
            }
        }
    }
}
