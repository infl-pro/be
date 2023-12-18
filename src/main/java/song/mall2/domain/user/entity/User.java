package song.mall2.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roleList = new ArrayList<>();

    private String username;
    private String password;
    private String address;

    private User(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public static User create(String username, String password, String address) {
        return new User(username, password, address);
    }

    public void addRole(UserRole userRole) {
        roleList.add(userRole);
    }
}
