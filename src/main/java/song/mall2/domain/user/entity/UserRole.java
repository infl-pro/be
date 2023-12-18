package song.mall2.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    private UserRole(User user, String roleName) {
        this.user = user;
        this.role = Role.valueOf(roleName);
    }

    public static UserRole create(User user, String roleName) {
        UserRole userRole = new UserRole(user, roleName);
        user.addRole(userRole);
        return userRole;
    }

    public enum Role {
        ROLE_USER, ROLE_SELLER, ROLE_ADMIN
    }
}
