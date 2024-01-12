package song.mall2.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserServiceTest {
    @Autowired
    UserService userService;

    Long userAId = 1L;
    String originalPassword = "a";

    @Test
    void updatePassword() {
        assertDoesNotThrow(() -> userService.updatePassword(userAId, originalPassword,
                "testPwd", "testPwd"));
    }
}