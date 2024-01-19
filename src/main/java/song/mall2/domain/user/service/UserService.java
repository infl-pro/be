package song.mall2.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import song.mall2.domain.user.dto.UserDto;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

    @Transactional
    public UserDto getUser(Long userId) {
        User user = findUser(userId);

        return new UserDto(user.getId(), user.getName(), user.getProfileUrl(), user.getEmail());
    }

    @Transactional
    public UserDto updateProfile(Long userId, String profileUrl) {
        User user = findUser(userId);

        user.updateProfile(profileUrl);
        User saveUser = userRepository.save(user);

        return new UserDto(saveUser.getId(), saveUser.getName(), saveUser.getProfileUrl(), saveUser.getEmail());
    }

    @Transactional
    public UserDto updatePassword(Long userId, String originalPassword, String newPassword, String confirmNewPassword) {
        User user = findUser(userId);

        if (!passwordEncoder.matches(originalPassword, user.getPassword())) {
            throw new InvalidRequestException("비밀번호가 일치하지 않습니다");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new InvalidRequestException("기존 비밀번호와 다른 비밀번호를 입력해주세요");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new InvalidRequestException("새로운 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));

        User saveUser = userRepository.save(user);

        return new UserDto(saveUser.getId(), saveUser.getName(), saveUser.getProfileUrl(), saveUser.getEmail());
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
