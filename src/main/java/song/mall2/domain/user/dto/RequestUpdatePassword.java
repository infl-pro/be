package song.mall2.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePassword {
    private String originalPassword;
    private String newPassword;
    private String confirmNewPassword;
}
