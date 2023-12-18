package song.mall2.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupDto {
    private String username;
    private String password;
    private String address;
}
