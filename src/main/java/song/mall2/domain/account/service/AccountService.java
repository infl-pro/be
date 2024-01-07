package song.mall2.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import song.mall2.domain.account.dto.ResponseFindUsername;
import song.mall2.domain.account.entity.EmailVerificationToken;
import song.mall2.domain.account.entity.ResetPasswordToken;
import song.mall2.domain.account.repository.EmailTokenJpaRepository;
import song.mall2.domain.account.repository.ResetPasswordTokenJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidEmailTokenException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.TokenNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;
    private final ResetPasswordTokenJpaRepository resetPasswordTokenRepository;
    private final EmailTokenJpaRepository emailTokenRepository;
    private final EmailService emailService;

    public static final String LOCALHOST_URL = "localhost:8080/account/resetPassword/";

    @Transactional
    public void validateUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            throw new InvalidRequestException("이미 가입된 아이디 입니다.");
        }
    }

    @Transactional
    public ResponseFindUsername findUsername(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        return new ResponseFindUsername(user.getUsername());
    }

    @Transactional
    public void createResetPasswordToken(String username, String name, String email) {
        userRepository.findByUsernameAndNameAndEmail(username, name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        ResetPasswordToken passwordToken = createPasswordToken(email);

        emailService.sendMail(email, "비밀번호 초기화",
                LOCALHOST_URL + passwordToken.getToken());
    }

    @Transactional
    public void validateRestPasswordToken(String token) {
        getPasswordToken(token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword, String confirmPassword) {
        ResetPasswordToken passwordToken = getPasswordToken(token);

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidRequestException("비밀번호가 일치하지 않습니다.");
        }

        User user = getUser(passwordToken.getEmail());

        user.updatePassword(passwordEncoder.encode(newPassword));
        resetPasswordTokenRepository.delete(passwordToken);
    }

    @Transactional
    public void createEmailVerificationToken(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {throw new InvalidEmailTokenException("이미 가입된 이메일입니다.");});
        String token = createEmailToken(email);

        emailService.sendMail(email, "이메일 인증", "token: " + token);
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerificationToken emailVerificationToken = emailTokenRepository.findByToken(token)
                .orElseThrow(()-> new TokenNotFoundException("인증에 실패했습니다."));

        emailVerificationToken.verifyEmail();
    }

    private ResetPasswordToken createPasswordToken(String email) {
        Optional<ResetPasswordToken> optionalPasswordToken = resetPasswordTokenRepository.findByEmail(email);
        if (optionalPasswordToken.isPresent()) {
            ResetPasswordToken passwordToken = optionalPasswordToken.get();
            passwordToken.updateToken(UUID.randomUUID().toString().substring(0, 5));
            return resetPasswordTokenRepository.save(passwordToken);
        }

        ResetPasswordToken passwordToken = new ResetPasswordToken(email, UUID.randomUUID().toString().substring(0, 5));
        return resetPasswordTokenRepository.save(passwordToken);
    }

    private ResetPasswordToken getPasswordToken(String token) {
        return resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("토큰을 찾을 수 없습니다."));
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

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}