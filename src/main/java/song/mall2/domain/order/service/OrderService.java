package song.mall2.domain.order.service;

import com.siot.IamportRestClient.response.CancellationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.dto.OrderFormDto;
import song.mall2.domain.order.dto.OrderProductListDto;
import song.mall2.domain.order.dto.OrdersDto;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.orderproduct.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.portone.service.PortoneService;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.invalid.exceptions.InvalidUserException;
import song.mall2.exception.notfound.exceptions.OrdersNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserJpaRepository userRepository;
    private final OrdersJpaRepository ordersRepository;
    private final OrderProductJpaRepository orderProductRepository;
    private final PaymentJpaRepository paymentRepository;
    private final CartJpaRepository cartJpaRepository;
    private final PortoneService portoneService;

    @Value("${portone.store-id}")
    private String storeId;
    @Value("${portone.channel-key}")
    private String channelKey;

    @Transactional
    public OrderFormDto getOrderForm(Long userId, List<Long> cartIdList) {
        User user = getUser(userId);

        List<Cart> cartList = getCartList(user.getId(), cartIdList);

        List<OrderFormDto.Products> productsList = getProductsList(cartList);

        return new OrderFormDto(storeId, channelKey, getTotalAmount(cartList), user.getId(), productsList, cartList.stream().map(Cart::getId).toList());
    }

    @Transactional
    public List<OrderProductListDto> getOrderList(Long userId) {
        User user = getUser(userId);
        return orderProductRepository.findAllByUserId(user.getId()).stream()
                .map(orderProduct -> new OrderProductListDto(orderProduct.getProduct().getId(), orderProduct.getProduct().getName(),
                        orderProduct.getProduct().getThumbnailUrl(), orderProduct.getOrders().getCreateAt(), orderProduct.getOrders().getId(),
                        orderProduct.getQuantity(), orderProduct.getAmount(), orderProduct.getStatus().name()))
                .toList();
    }

    @Transactional
    public OrdersDto getOrders(Long userId, Long ordersId) {
        User user = getUser(userId);
        Orders orders = ordersRepository.findByIdAndUserId(ordersId, user.getId())
                .orElseThrow(() -> new OrdersNotFoundException("주문을 찾을 수 없습니다."));

        List<OrderProductDto> orderProductDtoList = getOrderProductDtoList(orders);

        return new OrdersDto(orders.getId(), orders.getCreateAt(), orderProductDtoList, orders.getPayment().getTotalAmount(), orders.getPayment().getStatus());
    }

    @Transactional
    public OrdersDto cancelOrders(Long ordersId, Long userId) {
        User user = getUser(userId);
        Orders orders = ordersRepository.findById(ordersId)
                .orElseThrow(() -> new OrdersNotFoundException("주문을 찾을 수 없습니다."));
        if (!orders.isBuyer(user.getId())) {
            throw new InvalidUserException("접근 권한이 없습니다.");
        }

        List<OrderProductDto> orderProductDtoList = cancelOrderProductList(orders);

        Payment payment = orders.getPayment();
        CancellationResponse cancellationResponse = portoneService.cancel(payment.getPaymentId());

        payment.cancel(cancellationResponse.getCancellation().getCancelledAt());
        Payment cancelledPayment = paymentRepository.save(payment);

        return new OrdersDto(orders.getId(), orders.getCreateAt(), orderProductDtoList,
                cancelledPayment.getTotalAmount(), cancelledPayment.getStatus());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private List<Cart> getCartList(Long userId, List<Long> cartIdList) {
        return cartJpaRepository.findAllByIdAndUserId(cartIdList, userId);
    }

    private List<OrderFormDto.Products> getProductsList(List<Cart> cartList) {
        List<OrderFormDto.Products> productsList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            productsList.add(new OrderFormDto.Products(product.getId().toString(), product.getName(), product.getPrice(), cart.getQuantity()));
        }
        return productsList;
    }

    private Integer getTotalAmount(List<Cart> cartList) {
        return cartList.stream()
                .mapToInt(Cart::getAmount)
                .sum();
    }

    private List<OrderProductDto> getOrderProductDtoList(Orders orders) {
        return orderProductRepository.findAllByOrdersId(orders.getId())
                .stream()
                .map(orderProduct -> new OrderProductDto(orderProduct.getId(), orderProduct.getProduct().getName(), orderProduct.getProduct().getThumbnailUrl(),
                        orderProduct.getStatus().name(), orderProduct.getAmount(), orderProduct.getQuantity()))
                .toList();
    }

    private List<OrderProductDto> cancelOrderProductList(Orders orders) {
        List<OrderProduct> orderProductList = orderProductRepository.findAllByOrdersId(orders.getId());
        orderProductList.forEach(orderProduct -> {
            if (!orderProduct.getStatus().equals(OrderProduct.Status.PAID)) {
                throw new InvalidRequestException("결제를 취소할 수 없습니다.");
            }
            orderProduct.cancel();
        });

        return orderProductList.stream()
                .map(orderProduct -> new OrderProductDto(orderProduct.getProduct().getId(), orderProduct.getProduct().getName(), orderProduct.getProduct().getThumbnailUrl(),
                        orderProduct.getStatus().name(), orderProduct.getAmount(), orderProduct.getQuantity()))
                .toList();
    }
}
