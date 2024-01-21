package song.mall2.domain.account.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.account.dto.SignupDto;
import song.mall2.domain.account.entity.EmailVerificationToken;
import song.mall2.domain.account.repository.EmailTokenJpaRepository;
import song.mall2.domain.user.dto.UserDto;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    EmailTokenJpaRepository emailTokenJpaRepository;

    @BeforeEach
    void beforeEach() {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken("test@naver.com", "abc");
        emailVerificationToken.verifyEmail();
        emailTokenJpaRepository.save(emailVerificationToken);
    }

    @Test
    void saveUser() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUser");
        signupDto.setPassword("testPwd");
        signupDto.setConfirmPassword("testPwd");
        signupDto.setName("testName");
        signupDto.setEmail("test@naver.com");

        UserDto userDto = accountService.saveUser(signupDto);

        User user = userRepository.findById(userDto.getUserId()).get();
        assertThat(user.getUsername())
                .isEqualTo(signupDto.getUsername());
    }

    @Test
    void validateUsername() {
        String username = "testUN";
        User user = User.create(username, "testPwd", "testName");
        userRepository.save(user);

        Assertions.assertThatThrownBy(() -> accountService.validateUsername(username))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void createEmailToken() {
        String email = "newtest@naver.com";
        accountService.createEmailVerificationToken(email);
        accountService.createEmailVerificationToken(email);

        Assertions.assertThat(emailTokenJpaRepository.findByEmail(email))
                .isNotEmpty();
    }
}