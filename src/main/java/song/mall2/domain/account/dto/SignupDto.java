package song.mall2.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 8, max = 16, message = "아이디의 길이는 8 ~ 16자 사이여야 합니다.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호의 길이는 8 ~ 16자 사이여야 합니다.")
    private String password;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호의 길이는 8 ~ 16자 사이여야 합니다.")
    private String confirmPassword;
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 16, message = "이름의 길이는 2 ~ 16자 사이여야 합니다.")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
}
