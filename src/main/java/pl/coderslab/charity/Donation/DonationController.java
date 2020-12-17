package pl.coderslab.charity.Donation;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.AppUser.CurrentUser;
import pl.coderslab.charity.Category.Category;
import pl.coderslab.charity.Category.CategoryRepository;
import pl.coderslab.charity.Email.EmailService;
import pl.coderslab.charity.Organization.OrganizationRepository;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class DonationController {

    private final CategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;
    private final EmailService emailService;

    public DonationController(CategoryRepository categoryRepository, OrganizationRepository organizationRepository, DonationRepository donationRepository, EmailService emailService) {
        this.categoryRepository = categoryRepository;
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
        this.emailService = emailService;
    }

    @GetMapping("/donate")
    public String setNewDonation(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("organizations", organizationRepository.findAll());
        return "form";
    }

    @PostMapping("/donate")
    public String newDonationSummary(@Valid Donation donation, BindingResult result, Model model) {
        model.addAttribute("donation", donation);
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("organizations", organizationRepository.findAll());
            return "form";
        }
        StringBuilder sb = addCategoriesToString(donation);
        model.addAttribute("categoriesString", sb);
        return "form-summary";
    }


    @PostMapping("/donateAdd")
    public String addDonation(Donation donation) throws MessagingException {
        donation.setAppUser(((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser());
        donationRepository.save(donation);
        StringBuilder sb = new StringBuilder();
        sb.append(donation.getQuantity() + " worki, w których znajdują się " + addCategoriesToString(donation));
        sb.append(" Dla fundacji \"" + donation.getOrganization().getName() + "\".");
        sb.append("<h4>Adres odbioru:</h4>\n" +
                "<ul>\n" +
                "   <li>" + donation.getStreet() + "</li>\n" +
                "   <li>" + donation.getCity() + "</li>\n" +
                "   <li>" + donation.getZipCode() + "</li>\n" +
                "   <li>" + donation.getTelephoneNumber() + "</li>" +
                "</ul>");
        sb.append("<h4>Termin odbioru:</h4>\n" +
                "<ul>" +
                "   <li>" + donation.getPickUpDate() + "</li>\n" +
                "   <li>" + donation.getPickUpTime() + "</li>\n" +
                "   <li>" + donation.getPickUpComment() + "</li>\n" +
                "</ul>");
        emailService.sendEmailWithDonationDetails(donation.getAppUser().getEmail(), sb.toString());
        return "form-confirmation";
    }

    private StringBuilder addCategoriesToString(Donation donation) {
        StringBuilder sb = new StringBuilder();
        List<Category> categoryList = donation.getCategories();
        if (categoryList.size() == 1) {
            sb.append(categoryList.get(0));
        } else if (categoryList.size() > 1) {
            for (int i = 0; i < categoryList.size() - 2; i++) {
                sb.append(categoryList.get(i).getName()).append(", ");
            }
            sb.append(categoryList.get(categoryList.size() - 2).getName()).append(" i ")
                    .append(categoryList.get(categoryList.size() - 1).getName()).append(".");
        }
        return sb;
    }
}
