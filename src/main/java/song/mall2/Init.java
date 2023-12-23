package song.mall2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import song.mall2.domain.cart.service.CartService;
import song.mall2.domain.order.dto.SaveOrderProductDto;
import song.mall2.domain.order.service.OrderService;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.service.ProductService;
import song.mall2.domain.user.dto.SignupDto;
import song.mall2.domain.user.service.UserService;

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
        private final UserService userService;
        private final ProductService productService;
        private final CartService cartService;
        private final OrderService orderService;

        public void setData() {
            Long userA = saveUser("a", "a", "address A");
            Long userB = saveUser("b", "b", "address B");

            userService.grantRole(userA, ROLE_SELLER.name());

            Long productA = saveProduct(userA, "productA", 10000, "This is productA", 100);
            Long productB = saveProduct(userA, "productB", 50000, "This is productB", 30);

            cartService.addCart(userA, productB, 10);

            Long order1Id = saveOrder(userA, productA, 10);
        }

        private Long saveUser(String username, String password, String address) {
            SignupDto signupDto = new SignupDto();
            signupDto.setUsername(username);
            signupDto.setPassword(password);
            signupDto.setAddress(address);

            return userService.saveUser(signupDto);
        }

        private Long saveProduct(Long userId, String name, Integer price, String description, Integer stockQuantity) {
            SaveProductDto saveProductDto = new SaveProductDto();
            saveProductDto.setName(name);
            saveProductDto.setPrice(price);
            saveProductDto.setDescription(description);
            saveProductDto.setStockQuantity(stockQuantity);

            return productService.saveProduct(userId, saveProductDto).getProductId();
        }


        private Long saveOrder(Long userId, Long productId, Integer quantity) {
            List<SaveOrderProductDto> saveOrderProductDtoList = new ArrayList<>();

            addOrderProductDto(saveOrderProductDtoList, productId, quantity);

            return orderService.saveOrder(userId, saveOrderProductDtoList).getOrderId();
        }

        private void addOrderProductDto(List<SaveOrderProductDto> saveOrderProductDtoList, Long productId, Integer quantity) {
            SaveOrderProductDto saveOrderProductDto = new SaveOrderProductDto();
            saveOrderProductDto.setProductId(productId);
            saveOrderProductDto.setQuantity(quantity);

            saveOrderProductDtoList.add(saveOrderProductDto);
        }
    }
}
