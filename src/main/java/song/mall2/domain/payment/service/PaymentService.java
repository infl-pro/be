package song.mall2.domain.payment.service;

import com.siot.IamportRestClient.response.PortonePaymentsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.order.repository.OrdersJpaRepository;
import song.mall2.domain.payment.dto.PaymentDto;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.dto.Webhook;
import song.mall2.domain.payment.portone.dto.PortonePaymentRequest;
import song.mall2.domain.payment.portone.service.PortoneService;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.exception.already.exceptions.AlreadyCancelledException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.OrdersNotFoundException;
import song.mall2.exception.notfound.exceptions.PaymentNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PortoneService portoneService;
    private final PaymentJpaRepository paymentRepository;
    private final OrdersJpaRepository ordersRepository;
    private final OrderProductJpaRepository orderProductRepository;

    @Transactional
    public PaymentDto getPaymentByPaymentId(Long userId, String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

        payment.isBuyer(userId);

        return new PaymentDto(payment.getPaymentId(), payment.getTotalAmount(), payment.getStatus(), payment.getOrders().getId());
    }

    @Transactional
    public void paymentWebhook(Webhook webhook) {
        PortonePaymentsResponse portonePayment = getPortonePayment(webhook.getPayment_id());

        Orders orders = validateAmount(portonePayment);

        if ("Ready".equals(webhook.getStatus())) {
            createPayment(portonePayment, orders);
        }
        if ("PAID".equals(webhook.getStatus())) {
            paidPayment(portonePayment);
        }
    }

    @Transactional
    public PortonePaymentRequest getPortonePaymentRequest(Long userId, Long orderId) {
        Orders orders = getOrders(orderId);

        validateOrder(userId, orders);

        return portoneService.getPortonePaymentRequest(orders.getId(), orders.getAmount(), orders.getUser().getId());
    }


    @Transactional
    public void cancelPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrdersId(orderId)
                .orElseThrow(PaymentNotFoundException::new);

        if (cancel(payment)) {
            return;
        }

        Orders orders = payment.getOrders();
        orders.cancel();

        List<OrderProduct> orderProductList = orderProductRepository.findByOrdersId(orders.getId());
        orderProductList.forEach(OrderProduct::increaseStockQuantity);
    }

    private void validateOrder(Long userId, Orders orders) {
        if (!userId.equals(orders.getUser().getId())) {
            throw new InvalidRequestException("사용자가 일치하지 않습니다.");
        }
        if (!"READY".equals(orders.getStatus().name())) {
            throw new InvalidRequestException("결제할 수 없습니다.");
        }
    }

    private PortonePaymentsResponse getPortonePayment(String webhook) {
        return portoneService.getPortonePayment(webhook);
    }

    private Orders validateAmount(PortonePaymentsResponse portonePayment) {
        Orders orders = getOrders(Long.valueOf(portonePayment.getCustomData()));

        if (!portonePayment.getAmount().getTotal().equals(orders.getAmount())) {
            throw new InvalidRequestException();
        }

        return orders;
    }

    private Orders getOrders(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(OrdersNotFoundException::new);
    }

    private Long createPayment(PortonePaymentsResponse portonePayment, Orders orders) {
        Payment payment = Payment.of(orders.getUser(), orders,
                portonePayment.getId(), portonePayment.getStatus(), portonePayment.getAmount().getTotal(),
                portonePayment.getPaidAt(), portonePayment.getCancelledAt(), portonePayment.getFailedAt());

        Payment savePayment = paymentRepository.save(payment);

        return savePayment.getId();
    }

    private Long paidPayment(PortonePaymentsResponse portonePayment) {
        Payment payment = getPayment(portonePayment.getId());

        Orders orders = payment.getOrders();
        orders.paid();
        decreaseStockQuantity(orders);

        payment.update(portonePayment.getStatus(), portonePayment.getPaidAt(), portonePayment.getCancelledAt(), portonePayment.getFailedAt());

        Payment savePayment = paymentRepository.save(payment);

        return savePayment.getId();
    }

    private Payment getPayment(String paymentId) {
        return paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(PaymentNotFoundException::new);
    }

    private void decreaseStockQuantity(Orders orders) {
        List<OrderProduct> orderProductList = orderProductRepository.findByOrdersId(orders.getId());
        orderProductList.forEach(OrderProduct::decreaseStockQuantity);
    }

    private boolean cancel(Payment payment) {
        try {
            portoneService.cancelPayment(payment.getPaymentId());
        } catch (AlreadyCancelledException ex) {
            log.info(ex.getMessage());
            return true;
        }
        return false;
    }
}
