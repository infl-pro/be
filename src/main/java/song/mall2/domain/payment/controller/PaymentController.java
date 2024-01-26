package song.mall2.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.payment.dto.Callback;
import song.mall2.domain.payment.dto.PaymentDto;
import song.mall2.domain.payment.dto.Webhook;
import song.mall2.domain.payment.service.PaymentService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

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

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/webhook")
    public ResponseEntity<Object> postWebhook(@RequestBody Webhook webhook) {
        log.info("webhook: {}", webhook);
        paymentService.paymentWebhook(webhook);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseApi<PaymentDto, String> getPayment(@RequestBody Callback callback,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        PaymentDto payment = paymentService.getPayment(userPrincipal.getId(), callback.getPaymentId());

        return new ResponseApi<>(HttpStatus.OK.value(), "결제 조회", payment);
    }
}
