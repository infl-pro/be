package song.mall2.domain.payment.service;

import com.siot.IamportRestClient.response.PortonePaymentsResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.order.dto.OrdersIdDto;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.dto.Webhook;
import song.mall2.domain.payment.portone.service.PortoneService;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.PaymentNotFoundException;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PortoneService portoneService;
    private final PaymentJpaRepository paymentRepository;
    private final OrdersJpaRepository ordersRepository;
    private final ProductJpaRepository productRepository;
    private final UserJpaRepository userRepository;
    private final CartJpaRepository cartRepository;
    private final EntityManager em;

    @Transactional
    public OrdersIdDto getOrdersId(Long userId, String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

        payment.isBuyer(userId);

        return new OrdersIdDto(payment.getOrders().getId());
    }

    @Transactional
    public void paymentWebhook(Webhook webhook) {
        PortonePaymentsResponse portonePayment = getPortonePayment(webhook.getPayment_id());

        if ("Paid".equals(webhook.getStatus())) {
            completePayment(portonePayment);
            deleteCart(Long.valueOf(portonePayment.getCustomer().getId()), portonePayment.getCustomData().getCartList());
        }
    }

    private PortonePaymentsResponse getPortonePayment(String paymentId) {
        return portoneService.getPortonePayment(paymentId);
    }

    private Long completePayment(PortonePaymentsResponse portonePayment) {
        Optional<Payment> optionalPayment = paymentRepository.findByPaymentId(portonePayment.getId());

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.update(portonePayment.getStatus(), portonePayment.getPaidAt(), portonePayment.getCancelledAt(), portonePayment.getFailedAt());

            return paymentRepository.save(payment).getId();
        }

        User user = getUser(portonePayment.getCustomer().getId());
        Orders orders = saveOrders(portonePayment.getProducts(), user);
        Payment savePayment = savePayment(portonePayment, orders);

        return savePayment.getId();
    }

    private User getUser(String customerId) {
        return userRepository.findById(Long.valueOf(customerId))
                .orElseThrow(UserNotFoundException::new);
    }

    private Orders saveOrders(List<PortonePaymentsResponse.PaymentProduct> portonePayment, User user) {
        Orders orders = Orders.create(user);

        List<Product> productList = productRepository.findAllById(getProductIdList(portonePayment));
        Map<Long, Product> productMap = toMap(productList);
        for (PortonePaymentsResponse.PaymentProduct paymentProduct : portonePayment) {
            Product product = productMap.get(Long.valueOf(paymentProduct.getId()));
            if (product == null) {
                throw new ProductNotFoundException();
            }

            orders.addOrderProduct(OrderProduct.create(orders, product, user, paymentProduct.getQuantity()));
        }
        productRepository.saveAll(productList);
        return ordersRepository.save(orders);
    }

    private List<Long> getProductIdList(List<PortonePaymentsResponse.PaymentProduct> portonePayment) {
        return portonePayment.stream()
                .map(products -> Long.valueOf(products.getId()))
                .toList();
    }

    private Map<Long, Product> toMap(List<Product> productList) {
        return productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    private Payment savePayment(PortonePaymentsResponse portonePayment, Orders orders) {
        Payment payment = Payment.of(orders.getUser(), orders,
                portonePayment.getId(), portonePayment.getStatus(), portonePayment.getAmount().getTotal(),
                portonePayment.getPaidAt(), portonePayment.getCancelledAt(), portonePayment.getFailedAt());
        return paymentRepository.save(payment);
    }

    private void deleteCart(Long userId, List<Long> cartIdDtoList) {
        em.flush();
        em.clear();
        cartRepository.deleteAllByIdAndUserId(cartIdDtoList, userId);
    }
}
