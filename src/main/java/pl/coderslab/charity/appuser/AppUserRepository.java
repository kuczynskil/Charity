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

    @Query(value = "SELECT * FROM app_user JOIN app_user_role aur on app_user.id = aur\n" +
            "    .app_user_id JOIN role r on r.id = aur.role_id WHERE r.name = 'ROLE_ADMIN';", nativeQuery = true)
    List<AppUser> findAdmins();

}
