package pl.coderslab.charity.AppUser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_role", joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{3,}$")
    @Size(min = 8)
    private String password;

    @NotBlank
    private String name;
    private boolean enabled;
    private String verificationToken;
    private Timestamp verificationTokenExpiryDate;

    private String changePasswordToken;
    private Timestamp changePasswordTokenExpiryDate;

    public AppUser() {
        this.setEnabled(false);
    }
}
