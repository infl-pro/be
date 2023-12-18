package song.mall2.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.user.dto.SignupDto;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void saveUser() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUsername");
        signupDto.setPassword("testPassword");
        signupDto.setAddress("testAddress");

        Assertions.assertDoesNotThrow(() -> userService.saveUser(signupDto));
    }

}