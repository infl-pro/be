package song.mall2.domain.account.service;

import jakarta.mail.MessagingException;
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
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendMail() throws MessagingException {
        emailService.sendMail("dkclasltmf@naver.com", "test email", "test");
    }

}