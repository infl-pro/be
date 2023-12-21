package song.mall2.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.exception.notfound.exceptions.OrderProductNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductJpaRepository orderProductRepository;

    public List<OrderProductDto> getOrderProductListByProduct(Long userId, Long productId) {
        List<OrderProduct> orderProductList = orderProductRepository.findByProductId(productId, userId);

        return orderProductList.stream()
                .map(orderProduct -> new OrderProductDto(orderProduct.getId(), orderProduct.getQuantity(), orderProduct.getStatus().name(),
                        orderProduct.getProduct().getId(), orderProduct.getProduct().getName(),
                        orderProduct.getUser().getId(), orderProduct.getUser().getUsername()))
                .toList();
    }

    @Transactional
    public void updateOrderProductStatus(Long userId, Long orderProductId, String statusName) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(OrderProductNotFoundException::new);

        orderProduct.updateStatus(userId, statusName);
    }

}
