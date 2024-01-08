package song.mall2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import song.mall2.domain.account.service.AccountService;
import song.mall2.domain.cart.service.CartService;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.product.service.ProductService;
import song.mall2.domain.account.dto.UserSignupDto;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.service.UserService;

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
        private final AccountService accountService;
        private final UserService userService;
        private final ProductService productService;
        private final CartService cartService;
        private final OrdersJpaRepository ordersRepository;
        private final UserJpaRepository userRepository;
        private final ProductJpaRepository productRepository;
        private final OrderProductJpaRepository orderProductRepository;
        private final PaymentJpaRepository paymentRepository;

        public void setData() {
            Long userA = saveUser("a", "a", "nameA", "test1@email.com");
            Long userB = saveUser("b", "b", "nameB", "test1@email.com");

            accountService.grantRole(userA, ROLE_SELLER.name());

            Long productA = saveProduct(userA, "productA", 10, "This is productA", "/file/downloadFile/spring.png", 1000);
            Long productB = saveProduct(userA, "productB", 5, "This is productB", "/file/downloadFile/security.png", 300);

            cartService.addCart(userA, productA, 20);
            cartService.addCart(userA, productB, 40);

            Long ordersId = saveOrder(userA, productA, 10);
        }

        private Long saveUser(String username, String password, String name, String email) {
            UserSignupDto userSignupDto = new UserSignupDto();
            userSignupDto.setUsername(username);
            userSignupDto.setPassword(password);
            userSignupDto.setName(name);
            userSignupDto.setEmail(email);

            return accountService.saveUser(userSignupDto);
        }

        private Long saveProduct(Long userId, String name, Integer price, String description, String thumbnailUrl, Integer stockQuantity) {
            SaveProductDto saveProductDto = new SaveProductDto();
            saveProductDto.setName(name);
            saveProductDto.setPrice(price);
            saveProductDto.setDescription(description);
            saveProductDto.setThumbnailUrl(thumbnailUrl);
            saveProductDto.setStockQuantity(stockQuantity);
            saveProductDto.setCategoryName(Product.Category.A.name());

            return productService.saveProduct(userId, saveProductDto).getProductId();
        }


        private Long saveOrder(Long userId, Long productId, Integer quantity) {
            User userA = userRepository.findById(userId).get();
            Orders orders = Orders.create(userA);

            List<OrderProduct> orderProductList = new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            OrderProduct orderProduct = OrderProduct.create(orders, product, userA, quantity);
            orderProductList.add(orderProduct);

            Orders saveOrders = ordersRepository.save(orders);
            List<OrderProduct> saveOrderProductList = orderProductRepository.saveAll(orderProductList);

            Payment payment = Payment.of(userA, orders, "testPayment", "PAID", orderProduct.getAmount(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(LocalDateTime.now()), null, null);
            Payment savePayment = paymentRepository.save(payment);

            return saveOrders.getId();
        }
    }
}
