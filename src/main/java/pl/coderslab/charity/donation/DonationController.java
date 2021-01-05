package pl.coderslab.charity.donation;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.appuser.CurrentUser;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryRepository;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.organization.OrganizationRepository;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DonationController {

    private final CategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;
    private final EmailService emailService;
    private static final String APP_USER = "loggedInAppUser";

    public DonationController(CategoryRepository categoryRepository, OrganizationRepository organizationRepository, DonationRepository donationRepository, EmailService emailService) {
        this.categoryRepository = categoryRepository;
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
        this.emailService = emailService;
    }

    @GetMapping("/donate")
    public String setNewDonation(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute(APP_USER, ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser());
        return redirectToDonationForm(model);
    }

    @PostMapping("/donate")
    public String newDonationSummary(@Valid Donation donation, BindingResult result, Model model) {
        model.addAttribute("donation", donation);
        model.addAttribute(APP_USER, ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser());
        if (result.hasErrors()) {
            return redirectToDonationForm(model);
        }
        if (donation.getPickUpDate().isBefore(LocalDate.now())) {
            result.addError(new FieldError("donation", "pickUpDate",
                    "Data nie może być w przeszłości."));
            return redirectToDonationForm(model);
        }
        StringBuilder sb = addCategoriesToString(donation);
        model.addAttribute("categoriesString", sb);
        return "donation-form-summary";
    }


    @PostMapping("/donateAdd")
    public String addDonation(Donation donation, Model model) throws MessagingException {
        donation.setAppUser(((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser());
        donation.setCreatedOn(LocalDate.now());
        donation.setPickedUp(false);
        donationRepository.save(donation);
        sendEmailWithDonationDetails(donation);
        model.addAttribute(APP_USER, ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser());
        return "donation-form-confirmation";
    }

    private void sendEmailWithDonationDetails(Donation donation) throws MessagingException {
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
    }

    private StringBuilder addCategoriesToString(Donation donation) {
        StringBuilder sb = new StringBuilder();
        List<Category> categoryList = donation.getCategories();
        if (categoryList.size() == 1) {
            sb.append(categoryList.get(0).getName());
        } else if (categoryList.size() > 1) {
            for (int i = 0; i < categoryList.size() - 2; i++) {
                sb.append(categoryList.get(i).getName()).append(", ");
            }
            sb.append(categoryList.get(categoryList.size() - 2).getName()).append(" i ")
                    .append(categoryList.get(categoryList.size() - 1).getName()).append(".");
        }
        return sb;
    }

    private String redirectToDonationForm(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("organizations", organizationRepository.findAll());
        return "donation-form";
    }
}
