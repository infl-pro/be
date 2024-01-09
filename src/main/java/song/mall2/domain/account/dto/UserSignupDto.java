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
public class UserSignupDto {
    @NotBlank
    @Size(min = 8, max = 16)
    private String username;
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
    @NotBlank
    @Size(min = 2, max = 16)
    private String name;
    @NotBlank
    @Email
    private String email;
}
