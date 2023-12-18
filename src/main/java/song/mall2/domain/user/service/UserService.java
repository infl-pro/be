package song.mall2.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import song.mall2.domain.user.dto.SignupDto;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.entity.UserRole.Role;
import song.mall2.domain.user.repository.UserRoleJpaRepository;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userRepository;
    private final UserRoleJpaRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(SignupDto signupDto) {
        User user = User.create(signupDto.getUsername(), passwordEncoder.encode(signupDto.getPassword()), signupDto.getAddress());
        User saveUser = userRepository.save(user);

        grantRole(saveUser.getId(), Role.ROLE_USER.name());

        return saveUser.getId();
    }

    @Transactional
    public void grantRole(Long userId, String roleName) {
        User user = getUserById(userId);
        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRole(userId, Role.valueOf(roleName));
        if (optionalUserRole.isPresent()) {
            log.info("이미 부여된 권한입니다. id = {}, role = {}", userId, roleName);
            return;
        }

        UserRole userRole = UserRole.create(user, roleName);
        userRoleRepository.save(userRole);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}