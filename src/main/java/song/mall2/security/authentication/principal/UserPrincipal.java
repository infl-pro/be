package song.mall2.security.authentication.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import song.mall2.domain.user.entity.UserRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> roleList = new ArrayList<>();

    private UserPrincipal(Long id, String username, String password, List<SimpleGrantedAuthority> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }

    public static UserPrincipal create(Long id, String username, String password, List<UserRole> roleList) {
        List<SimpleGrantedAuthority> simpleRole = roleList.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                .toList();

        return new UserPrincipal(id, username, password, simpleRole);
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
