package song.mall2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import song.mall2.domain.user.dto.SignupDto;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.service.UserService;

import static song.mall2.domain.user.entity.UserRole.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Init {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.setData();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final UserService userService;

        public void setData() {
            Long userA = saveUser("a", "a", "address A");
            Long userB = saveUser("b", "b", "address B");

            userService.grantRole(userA, ROLE_SELLER.name());
        }

        private Long saveUser(String username, String password, String address) {
            SignupDto signupDto = new SignupDto();
            signupDto.setUsername(username);
            signupDto.setPassword(password);
            signupDto.setAddress(address);

            return userService.save(signupDto);
        }
    }
}
