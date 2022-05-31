package eu.elision.marketplace.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * This class contains the basic information of any user
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Firstname is mandatory")
    @Size(max = 15, min = 2, message = "Name can not be longer than 15 characters")
    private String firstName;
    @NotBlank(message = "Lastname is mandatory")
    @Size(max = 20, min = 2, message = "Name can not be longer than 15 characters")
    private String lastName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is wrong")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is required")
    @JsonIgnore // Prevent accidentally sending the password in any kind of JSON message
    private String password;
    private LocalDateTime createdDate;
    private boolean validated;

    protected User() {
        validated = false;
        createdDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new java.util.HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        if (this instanceof Vendor) roles.add(new SimpleGrantedAuthority("ROLE_VENDOR"));
        if (this instanceof Admin) roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roles;
    }

    @Override
    public String getUsername() {
        return getEmail();
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

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
