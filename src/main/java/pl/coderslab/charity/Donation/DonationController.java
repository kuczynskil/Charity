package pl.coderslab.charity.Donation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.Category.Category;
import pl.coderslab.charity.Category.CategoryRepository;
import pl.coderslab.charity.Organization.OrganizationRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DonationController {

    private final CategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;

    public DonationController(CategoryRepository categoryRepository, OrganizationRepository organizationRepository, DonationRepository donationRepository) {
        this.categoryRepository = categoryRepository;
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
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
            return "form";
        }
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
        model.addAttribute("categoriesString", sb);
        return "form-summary";
    }


    @PostMapping("/donateAdd")
    public String addDonation(Donation donation) {
        donationRepository.save(donation);
        return "form-confirmation";
    }
}
