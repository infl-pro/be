package song.mall2.domain.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import song.mall2.domain.account.dto.RequestValidateUsername;
import song.mall2.domain.account.dto.SignupDto;
import song.mall2.domain.account.entity.EmailVerificationToken;
import song.mall2.domain.account.repository.EmailTokenJpaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmailTokenJpaRepository emailTokenRepository;
    @Autowired
    ObjectMapper objectMapper;

    String email = "accountControllerTest@email.com";

    @BeforeEach
    void beforeEach() {
        emailTokenRepository.deleteAll();
        EmailVerificationToken emailToken = new EmailVerificationToken(email, "token");
        emailToken.verifyEmail();
        emailTokenRepository.save(emailToken);
    }

    @Test
    void signupUser() throws Exception {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUsername");
        signupDto.setPassword("testPassword");
        signupDto.setConfirmPassword("testPassword");
        signupDto.setName("testName");
        signupDto.setEmail(email);
        mockMvc.perform(post("/account/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void validateUsername() throws Exception {
        RequestValidateUsername validateUsername = new RequestValidateUsername("testUsername");
        mockMvc.perform(post("/account/validateUsername")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validateUsername)))
                .andExpect(status().isOk());
    }
}