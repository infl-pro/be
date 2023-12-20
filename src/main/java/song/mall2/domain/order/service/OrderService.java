package song.mall2.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.dto.OrderDto;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.dto.OrdersIdDto;
import song.mall2.domain.order.dto.SaveOrderProductDto;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.OrderProductNotFoundException;
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
    private final OrderProductJpaRepository orderProductRepository;
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;

    @Transactional
    public OrdersIdDto saveOrders(Long userId, List<SaveOrderProductDto> saveOrderProductDtoList) {
        User user = getUserById(userId);
        Orders orders = Orders.create(user);

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

    public List<OrderDto> getOrderList(Long userId) {
        return ordersRepository.findAllByUserId(userId)
                .stream()
                .map(orders -> new OrderDto(orders.getId()))
                .toList();
    }

    public List<OrderProductDto> getOrderProductList(Long userId, Long ordersId) {
        return orderProductRepository.findByOrdersId(ordersId)
                .stream()
                .map(orderProduct -> new OrderProductDto(orderProduct.getId(), orderProduct.getQuantity(), orderProduct.getStatus().name(),
                        orderProduct.getProduct().getId(), orderProduct.getProduct().getName()))
                .toList();
    }

    public OrderProductDto getOrderProduct(Long userId, Long orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(OrderProductNotFoundException::new);

        return new OrderProductDto(orderProduct.getId(), orderProduct.getQuantity(), orderProduct.getStatus().name(),
                orderProduct.getProduct().getId(), orderProduct.getProduct().getName());
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
