package song.mall2.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import song.mall2.domain.account.dto.ResponseFindUsername;
import song.mall2.domain.account.dto.SellerSignupDto;
import song.mall2.domain.account.dto.UserSignupDto;
import song.mall2.domain.account.entity.EmailVerificationToken;
import song.mall2.domain.account.entity.ResetPasswordToken;
import song.mall2.domain.account.repository.EmailTokenJpaRepository;
import song.mall2.domain.account.repository.ResetPasswordTokenJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.repository.UserRoleJpaRepository;
import song.mall2.exception.illegal.exceptions.IllegalTokenException;
import song.mall2.exception.invalid.exceptions.InvalidTokenException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.invalid.exceptions.InvalidUsernameException;
import song.mall2.exception.notfound.exceptions.TokenNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;
    private final UserRoleJpaRepository userRoleRepository;
    private final ResetPasswordTokenJpaRepository resetPasswordTokenRepository;
    private final EmailTokenJpaRepository emailTokenRepository;

    @Transactional
    public Long saveUser(UserSignupDto userSignupDto) {
        validateUsername(userSignupDto.getUsername());
        validateEmail(userSignupDto);

        User user = User.create(userSignupDto.getUsername(), passwordEncoder.encode(userSignupDto.getPassword()), userSignupDto.getName(), userSignupDto.getEmail());
        User saveUser = userRepository.save(user);

        grantRole(saveUser.getId(), UserRole.Role.ROLE_USER.name());

        return saveUser.getId();
    }

    @Transactional
    public Long saveSeller(SellerSignupDto sellerSignupDto) {
        validateUsername(sellerSignupDto.getUsername());
        User user = User.create(sellerSignupDto.getUsername(), passwordEncoder.encode(sellerSignupDto.getPassword()), sellerSignupDto.getName());
        User saveUser = userRepository.save(user);

        grantRole(saveUser.getId(), UserRole.Role.ROLE_SELLER.name());

        return saveUser.getId();
    }

    @Transactional
    public void grantRole(Long userId, String roleName) {
        User user = getUserById(userId);
        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRole(userId, UserRole.Role.valueOf(roleName));
        if (optionalUserRole.isPresent()) {
            log.info("이미 부여된 권한입니다. id = {}, role = {}", userId, roleName);
            return;
        }

        UserRole userRole = UserRole.create(user, roleName);
        userRoleRepository.save(userRole);
    }

    @Transactional
    public void validateUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            throw new InvalidUsernameException("중복된 아이디입니다.");
        }
    }

    @Transactional
    public String createEmailVerificationToken(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {throw new InvalidTokenException("이미 가입된 이메일입니다.");});
        return createEmailToken(email);
    }

    @Transactional
    public void verifyEmail(String email, String token) {
        EmailVerificationToken emailVerificationToken = emailTokenRepository.findByEmailAndToken(email, token)
                .orElseThrow(()-> new TokenNotFoundException("토큰을 찾을 수 없습니다."));

        if (emailVerificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalTokenException("토큰이 만료되었습니다.");
        }

        emailVerificationToken.verifyEmail();
    }

    @Transactional
    public ResponseFindUsername findUsername(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        return new ResponseFindUsername(user.getUsername());
    }

    @Transactional
    public ResetPasswordToken createResetPasswordToken(String username, String name, String email) {
        userRepository.findByUsernameAndNameAndEmail(username, name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        return createPasswordToken(email);
    }

    @Transactional
    public void validateRestPasswordToken(String token) {
        ResetPasswordToken passwordToken = getPasswordToken(token);

        if (passwordToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalTokenException("토큰이 만료되었습니다.");
        }
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

    private void validateEmail(UserSignupDto userSignupDto) {
        EmailVerificationToken emailToken = emailTokenRepository.findByEmail(userSignupDto.getEmail())
                .orElseThrow(() -> new TokenNotFoundException("이메일 인증을 먼저 시도해주세요."));
        if (!emailToken.isVerified()) {
            throw new InvalidTokenException("인증되지 않은 이메일 입니다.");
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
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