package song.mall2.domain.order.service;

import com.siot.IamportRestClient.response.CancellationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.dto.OrderFormDto;
import song.mall2.domain.order.dto.OrderProductListDto;
import song.mall2.domain.order.dto.OrdersDto;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.portone.service.PortoneService;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.OrdersNotFoundException;
import song.mall2.exception.notfound.exceptions.PaymentNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
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
    public OrderFormDto getOrderForm(Long userId, List<CartIdDto> cartDtoList) {
        List<Long> cartIdList = cartDtoList.stream().map(CartIdDto::getCartId).toList();
        List<Cart> cartList = getCartList(userId, cartIdList);

        List<OrderFormDto.Products> productsList = getProductsList(cartList);

        return new OrderFormDto(storeId, channelKey, getTotalAmount(cartList), userId, productsList, cartIdList);
    }

    @Transactional
    public List<OrderProductListDto> getOrderList(Long userId) {
        return orderProductRepository.findAllByUserId(userId).stream()
                .map(orderProduct -> new OrderProductListDto(orderProduct.getProduct().getId(), orderProduct.getProduct().getName(),
                        orderProduct.getProduct().getThumbnailUrl(), orderProduct.getOrders().getCreateAt(), orderProduct.getOrders().getId(),
                        orderProduct.getQuantity(), orderProduct.getAmount(), orderProduct.getStatus().name()))
                .toList();
    }

    @Transactional
    public OrdersDto getOrders(Long userId, Long ordersId) {
        Orders orders = ordersRepository.findByIdAndUserId(ordersId, userId)
                .orElseThrow(OrdersNotFoundException::new);

        List<OrderProductDto> orderProductDtoList = getOrderProductDtoList(orders);
        Payment payment = paymentRepository.findByOrdersId(orders.getId())
                .orElseThrow(PaymentNotFoundException::new);

        return new OrdersDto(orders.getId(), orders.getCreateAt(), orderProductDtoList, payment.getTotalAmount(), payment.getStatus());
    }

    @Transactional
    public OrdersDto cancelOrders(Long ordersId, Long userId) {
        Orders orders = ordersRepository.findByIdAndUserId(ordersId, userId)
                .orElseThrow(OrdersNotFoundException::new);

        List<OrderProductDto> orderProductDtoList = cancelOrderProductList(orders);

        Payment payment = paymentRepository.findByOrdersId(orders.getId()).
                orElseThrow(PaymentNotFoundException::new);
        CancellationResponse cancellationResponse = portoneService.cancel(payment.getPaymentId());

        payment.cancel(cancellationResponse.getCancellation().getCancelledAt());
        Payment cancelledPayment = paymentRepository.save(payment);

        return new OrdersDto(orders.getId(), orders.getCreateAt(), orderProductDtoList,
                cancelledPayment.getTotalAmount(), cancelledPayment.getStatus());
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
                .map(orderProduct -> new OrderProductDto(orderProduct.getId(), orderProduct.getProduct().getName(),
                        orderProduct.getStatus().name(), orderProduct.getAmount(), orderProduct.getQuantity()))
                .toList();
    }

    private List<OrderProductDto> cancelOrderProductList(Orders orders) {
        List<OrderProduct> orderProductList = orderProductRepository.findAllByOrdersId(orders.getId());
        orderProductList.forEach(orderProduct -> {
            if (!orderProduct.getStatus().equals(OrderProduct.Status.PAID)) {
                throw new InvalidRequestException("결제를 취소할 수 없습니다");
            }
            orderProduct.cancel();
        });

        return orderProductList.stream()
                .map(orderProduct -> new OrderProductDto(orderProduct.getProduct().getId(), orderProduct.getProduct().getName(),
                        orderProduct.getStatus().name(), orderProduct.getAmount(), orderProduct.getQuantity()))
                .toList();
    }
}
