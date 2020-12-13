package pl.coderslab.charity.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.Donation.DonationRepository;
import pl.coderslab.charity.Organization.OrganizationRepository;

@Controller
public class HomeController {

    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;

    public HomeController(OrganizationRepository organizationRepository, DonationRepository donationRepository) {
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping("")
    public String homepage(Model model) {
        model.addAttribute("organizations", organizationRepository.findAll());
        model.addAttribute("sumOfBags", donationRepository.sumOfBags());
        model.addAttribute("numberOfDonations", donationRepository.numberOfDonations());
        return "index";
    }
}
