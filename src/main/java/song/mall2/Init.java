package song.mall2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import song.mall2.domain.user.dto.SignupDto;
import song.mall2.domain.user.service.UserService;

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
            SignupDto signupDto = new SignupDto();
            signupDto.setUsername("a");
            signupDto.setPassword("a");
            signupDto.setAddress("address");

            Long userA = userService.save(signupDto);
        }
    }
}
