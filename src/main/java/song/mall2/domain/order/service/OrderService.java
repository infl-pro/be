package song.mall2.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.dto.OrdersIdDto;
import song.mall2.domain.order.dto.SaveOrdersDto;
import song.mall2.domain.order.dto.SaveOrderProductDto;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersJpaRepository ordersRepository;
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;

    @Transactional
    public OrdersIdDto saveOrder(Long userId, SaveOrdersDto saveOrdersDto) {
        User user = getUserById(userId);
        Orders orders = Orders.create(user);

        List<SaveOrderProductDto> saveOrderProductDtoList = saveOrdersDto.getSaveOrderProductDtoList();

        Map<Long, Product> products = getProducts(getProductIdList(saveOrderProductDtoList));
        for (SaveOrderProductDto saveOrderProductDto : saveOrderProductDtoList) {
            Product product = products.get(saveOrderProductDto.getProductId());
            if (product == null) {
                throw new ProductNotFoundException();
            }

            Integer quantity = saveOrderProductDto.getQuantity();
            OrderProduct orderProduct = OrderProduct.create(orders, product, quantity);
            orders.addOrderProduct(orderProduct);

            product.decreaseStockQuantity(quantity);
        }

        Orders saveOrders = ordersRepository.save(orders);

        OrdersIdDto ordersIdDto = new OrdersIdDto();
        ordersIdDto.setOrderId(saveOrders.getId());

        return ordersIdDto;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private List<Long> getProductIdList(List<SaveOrderProductDto> saveOrderDto) {
        return saveOrderDto.stream()
                .map(SaveOrderProductDto::getProductId)
                .toList();
    }

    private Map<Long, Product> getProducts(List<Long> productIdList) {
        return productRepository.findAllById(productIdList)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }
}
