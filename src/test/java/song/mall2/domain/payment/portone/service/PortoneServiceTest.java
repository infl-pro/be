package song.mall2.domain.payment.portone.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
}