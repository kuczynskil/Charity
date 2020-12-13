package pl.coderslab.charity.Donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "SELECT SUM(quantity) FROM donation", nativeQuery = true)
    int sumOfBags();

    @Query(value = "SELECT COUNT(id) FROM donation",nativeQuery = true)
    int numberOfDonations();
}
