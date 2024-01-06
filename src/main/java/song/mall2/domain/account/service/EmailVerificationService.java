package song.mall2.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.account.entity.EmailVerificationToken;
import song.mall2.domain.account.repository.EmailTokenJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidEmailTokenException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailTokenJpaRepository emailTokenRepository;
    private final UserJpaRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public void createEmailVerificationToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            throw new InvalidRequestException("이미 등록된 이메일 입니다.");
        }

        String emailToken = createEmailToken(email);

        emailService.sendMail(email, "이메일 인증", "token: " + emailToken);
    }

    @Transactional
    public void verifyEmailToken(String token) {
        EmailVerificationToken emailVerificationToken = emailTokenRepository.findByToken(token)
                .orElseThrow(InvalidEmailTokenException::new);

        emailVerificationToken.verifyEmail();
    }

    private String createEmailToken(String email) {
        Optional<EmailVerificationToken> findToken = emailTokenRepository.findByEmail(email);
        if (findToken.isPresent()) {
            EmailVerificationToken emailVerificationToken = findToken.get();
            emailVerificationToken.updateToken(UUID.randomUUID().toString().substring(0, 5));
            EmailVerificationToken saveToken = emailTokenRepository.save(emailVerificationToken);
            return saveToken.getToken();
        }

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(email, UUID.randomUUID().toString().substring(0, 5));
        EmailVerificationToken saveToken = emailTokenRepository.save(emailVerificationToken);
        return saveToken.getToken();
    }
}
