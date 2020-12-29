package pl.coderslab.charity.home;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.appuser.AppUser;
import pl.coderslab.charity.appuser.CurrentUser;
import pl.coderslab.charity.donation.DonationRepository;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.organization.OrganizationRepository;

import javax.mail.MessagingException;

@Controller
public class HomeController {

    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;
    private final EmailService emailService;

    public HomeController(OrganizationRepository organizationRepository, DonationRepository donationRepository, EmailService emailService) {
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
        this.emailService = emailService;
    }

    @GetMapping("")
    public String homepage(Model model) {
        AppUser loggedInUser = loggedInUserOrNull();
        model.addAttribute("appuser", loggedInUser);
        model.addAttribute("organizations", organizationRepository.findAll());
        model.addAttribute("sumOfBags", donationRepository.sumOfBags());
        model.addAttribute("numberOfDonations", donationRepository.numberOfDonations());
        return "index";
    }

    public AppUser loggedInUserOrNull() {
        if (SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().equals("anonymousUser")) {
            return null;
        }
        AppUser loggedInUser = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        return loggedInUser;
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String name, @RequestParam String surname,
                              @RequestParam(required = false) String email,
                              @RequestParam String message) throws MessagingException {
        AppUser loggedInUser = loggedInUserOrNull();
        if (null == loggedInUser) {
            emailService.sendEmailFromContactForm(name, surname, email, message);
            return "message-sent-confirmation";
        }
        emailService.sendEmailFromContactForm(name, surname, loggedInUser.getEmail(), message);
        return "message-sent-confirmation";
    }
}

