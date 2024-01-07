package song.mall2.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.repository.UserRoleJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userRepository;
    private final UserRoleJpaRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


}
