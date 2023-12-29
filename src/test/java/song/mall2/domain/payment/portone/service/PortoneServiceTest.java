package song.mall2.domain.payment.portone.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.exception.already.exceptions.AlreadyCancelledException;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class PortoneServiceTest {
    @Autowired
    PortoneService portoneService;

    @Test
    void getAuth() {
        portoneService.authenticate();
    }

    @Test
    void getPayment() {
        portoneService.getPortonePayment("pid8");
    }

    @Test
    void cancel() {
        Assertions.assertThatThrownBy(() -> portoneService.cancelPayment("pid33"))
                .isInstanceOf(AlreadyCancelledException.class);
    }

}