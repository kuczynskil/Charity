package pl.coderslab.charity.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    AppUser findByVerificationToken(String token);

    AppUser findByChangePasswordToken(String token);

    List<AppUser> findAllByRoles(Role role);
}
