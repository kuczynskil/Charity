package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.appuser.AppUser;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "SELECT SUM(quantity) FROM donation", nativeQuery = true)
    int sumOfBags();

    @Query(value = "SELECT COUNT(id) FROM donation",nativeQuery = true)
    int numberOfDonations();

    List<Donation> findAllByAppUser(AppUser appUser);
}
