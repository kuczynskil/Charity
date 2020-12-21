package pl.coderslab.charity.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.appuser.AppUser;
import pl.coderslab.charity.appuser.AppUserRepository;
import pl.coderslab.charity.donation.DonationRepository;
import pl.coderslab.charity.organization.Organization;
import pl.coderslab.charity.organization.OrganizationRepository;

@Controller
public class AdminController {
    private final AppUserRepository appUserRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;
    private static final String REDIRECT_ORGANIZATIONS = "redirect:/admino/organizations";
    private static final String REDIRECT_APP_USERS = "redirect:/admino/appusers";

    public AdminController(AppUserRepository appUserRepository, OrganizationRepository organizationRepository,
                           DonationRepository donationRepository) {
        this.appUserRepository = appUserRepository;
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping("/admino/appusers")
    public String adminDashboard(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "admin/appusers-table";
    }

    @GetMapping("/admino/appusers/edit/{id}")
    public String editAppUser(@PathVariable long id, Model model) {
        model.addAttribute("appuser", appUserRepository.findById(id).get());
        return "admin/edit-appuser";
    }

    @PostMapping("/admino/appusers/edit/{id}")
    public String editAppUserPerform(@PathVariable long id, AppUser appUser) {
        appUser.setRoles(appUserRepository.findById(id).get().getRoles());
        appUserRepository.save(appUser);
        return REDIRECT_APP_USERS;
    }

    @GetMapping("/admino/appusers/delete/{id}")
    public String deleteAppUser(@PathVariable long id, Model model) {
        model.addAttribute("appuser", appUserRepository.findById(id).get());
        return "admin/delete-appuser-confirm";
    }

    @GetMapping("/admino/appusers/deletePerform/{id}")
    public String deleteAppUserPerform(@PathVariable long id) {
        appUserRepository.deleteAppUserRoles(id);
        appUserRepository.delete(appUserRepository.findById(id).get());
        return REDIRECT_APP_USERS;
    }

    @GetMapping("/admino/appusers/delete/cancel")
    public String cancelDeleteAppUserOperation() {
        return REDIRECT_APP_USERS;
    }

    @GetMapping("/admino/donations")
    public String adminDonations(Model model) {
        model.addAttribute("donations", donationRepository.findAll());
        return "admin/donations-table";
    }

    @GetMapping("/admino/organizations")
    public String adminOrganizations(Model model) {
        model.addAttribute("organizations", organizationRepository.findAll());
        model.addAttribute("organization", new Organization());
        return "admin/organizations-table";
    }

    @GetMapping("/admino/admins")
    public String adminsList(Model model) {
        model.addAttribute("admins", appUserRepository.findAdmins());
        return "admin/admins-table";
    }

    @PostMapping("/admino/organizations/add")
    public String addNewOrganization(Organization organization) {
        organizationRepository.save(organization);
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admino/organizations/edit/{id}")
    public String editOrganization(@PathVariable long id, Model model) {
        model.addAttribute("organization", organizationRepository.findById(id).get());
        return "admin/edit-organization";
    }

    @PostMapping("/admino/organizations/edit/{id}")
    public String editOrganizationPerform(Organization organization) {
        organizationRepository.save(organization);
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admino/organization/delete/{id}")
    public String deleteOrganization(@PathVariable long id, Model model) {
        model.addAttribute("organization", organizationRepository.findById(id).get());
        return "admin/delete-organization-confirm";
    }

    @GetMapping("/admino/organization/deletePerform/{id}")
    public String deleteOrganizationPerform(@PathVariable long id) {
        organizationRepository.delete(organizationRepository.findById(id).get());
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admino/organization/delete/cancel")
    public String cancelDeleteOrganizationOperation() {
        return REDIRECT_ORGANIZATIONS;
    }
}
