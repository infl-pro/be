package song.mall2.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.order.dto.OrdersIdDto;
import song.mall2.domain.payment.dto.Callback;
import song.mall2.domain.payment.dto.Webhook;
import song.mall2.domain.payment.service.PaymentService;
import song.mall2.security.authentication.principal.UserPrincipal;

@Slf4j
@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * test payment
     */
    @GetMapping("/portone")
    public String portone() {

        return "pay/portone";
    }

    @PostMapping("/webhook")
    public ResponseEntity<Object> postWebhook(@RequestBody Webhook webhook) {
        log.info("webhook: {}", webhook);
        paymentService.paymentWebhook(webhook);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/callback")
    public ResponseEntity<OrdersIdDto> postCallback(@RequestBody Callback callback,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("callback: {}", callback);
//        OrdersIdDto ordersId = paymentService.getOrdersId(userPrincipal.getId(), callback.getPaymentId());
        OrdersIdDto ordersId = paymentService.getOrdersId(1L, callback.getPaymentId());

        return ResponseEntity.ok(ordersId);
    }
}
