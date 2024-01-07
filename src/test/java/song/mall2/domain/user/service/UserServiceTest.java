package song.mall2.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.account.dto.UserSignupDto;

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
        UserSignupDto userSignupDto = new UserSignupDto();
        userSignupDto.setUsername("testUsername");
        userSignupDto.setPassword("testPassword");

        Assertions.assertDoesNotThrow(() -> userService.saveCommonUser(userSignupDto));
    }

}