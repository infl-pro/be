package song.mall2.domain.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.repository.PaymentJpaRepository;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentJpaRepository paymentRepository;

    Long userAId = 1L;
    Long order1Id = 1L;

    @Test
    void getPayment() {
//        Payment payment = paymentRepository.findByOrdersId(order1Id).get();
//
//        Assertions.assertThat(paymentService.getOrdersId(userAId, payment.getPaymentId()).getOrdersId())
//                .isEqualTo(order1Id);
    }

}