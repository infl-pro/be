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
    private String name;
    private String email;
    private String profileUrl;

    private User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.profileUrl = "/file/downloadFile/user.png";
    }

    private User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.profileUrl = "/file/downloadFile/user.png";
    }

    public static User create(String username, String password, String name, String email) {
        return new User(username, password, name, email);
    }

    public static User create(String username, String password, String name) {
        return new User(username, password, name);
    }

    public void addRole(UserRole userRole) {
        roleList.add(userRole);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfile(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
