package pl.coderslab.charity.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.appuser.AppUser;
import pl.coderslab.charity.appuser.AppUserRepository;
import pl.coderslab.charity.appuser.CurrentUser;
import pl.coderslab.charity.appuser.RoleRepository;
import pl.coderslab.charity.donation.DonationRepository;
import pl.coderslab.charity.organization.Organization;
import pl.coderslab.charity.organization.OrganizationRepository;

import java.util.Collections;
import java.util.HashSet;

@Controller
public class AdminController {
    private final AppUserRepository appUserRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationRepository donationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String REDIRECT_ORGANIZATIONS = "redirect:/admin/organizations";
    private static final String REDIRECT_APP_USERS = "redirect:/admin/appusers";
    private static final String REDIRECT_ADMINS = "redirect:/admin/admins";

    public AdminController(AppUserRepository appUserRepository, OrganizationRepository organizationRepository,
                           DonationRepository donationRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.organizationRepository = organizationRepository;
        this.donationRepository = donationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/appusers")
    public String adminDashboard(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "admin/appusers-table";
    }

    @GetMapping("/admin/appusers/edit/{id}")
    public String editAppUser(@PathVariable long id, Model model) {
        model.addAttribute("appuser", appUserRepository.findById(id).get());
        return "admin/edit-appuser";
    }

    @PostMapping("/admin/appusers/edit/{id}")
    public String editAppUserPerform(@PathVariable long id, AppUser appUser) {
        AppUser databaseAppUser = appUserRepository.findById(id).get();
        boolean isAdmin = databaseAppUser.isThisAppUserAnAdmin();
        databaseAppUser.setName(appUser.getName());
        databaseAppUser.setEmail(appUser.getEmail());
        databaseAppUser.setEnabled(appUser.isEnabled());
        appUserRepository.save(databaseAppUser);
        return redirectToAdminsOrAppUsersTable(isAdmin);
    }

    private String redirectToAdminsOrAppUsersTable(boolean isAdmin) {
        if (isAdmin) {
            return REDIRECT_ADMINS;
        }
        return REDIRECT_APP_USERS;
    }

    @GetMapping("/admin/appusers/delete/{id}")
    public String deleteAppUser(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        AppUser loggedInAdmin = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        AppUser userToBeDeleted = appUserRepository.findById(id).get();
        if (loggedInAdmin.getEmail().equals(userToBeDeleted.getEmail())) {
            redirectAttributes.addFlashAttribute("cantDeleteYourselfMessage", "You can't delete yourself");
            return REDIRECT_APP_USERS;
        }
        model.addAttribute("appuser", userToBeDeleted);
        return "admin/delete-appuser-confirm";
    }

    @GetMapping("/admin/appusers/deletePerform/{id}")
    public String deleteAppUserPerform(@PathVariable long id) {
        AppUser databaseAppUser = appUserRepository.findById(id).get();
        boolean isAdmin = databaseAppUser.isThisAppUserAnAdmin();
        appUserRepository.delete(databaseAppUser);
        return redirectToAdminsOrAppUsersTable(isAdmin);
    }

    @GetMapping("/admin/appusers/delete/cancel")
    public String cancelDeleteAppUserOperation() {
        return REDIRECT_APP_USERS;
    }

    @GetMapping("/admin/donations")
    public String adminDonations(Model model) {
        model.addAttribute("donations", donationRepository.findAll());
        return "admin/donations-table";
    }

    @GetMapping("/admin/admins")
    public String adminsList(Model model) {
        model.addAttribute("admins", appUserRepository.findAdmins());
        model.addAttribute("admin", new AppUser());
        return "admin/admins-table";
    }

    @PostMapping("/admin/admins/add")
    public String addAdmin(AppUser admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setEnabled(true);
        admin.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_ADMIN"))));
        appUserRepository.save(admin);
        return REDIRECT_ADMINS;
    }

    @GetMapping("/admin/admins/deletePerform/{id}")
    public String deleteAdminPerform(@PathVariable long id) {
        appUserRepository.delete(appUserRepository.findById(id).get());
        return REDIRECT_ADMINS;
    }

    @GetMapping("/admin/admins/delete/cancel")
    public String cancelDeleteAdminOperation() {
        return REDIRECT_ADMINS;
    }

    @GetMapping("/admin/organizations")
    public String adminOrganizations(Model model) {
        model.addAttribute("organizations", organizationRepository.findAll());
        model.addAttribute("organization", new Organization());
        return "admin/organizations-table";
    }

    @PostMapping("/admin/organizations/add")
    public String addNewOrganization(Organization organization) {
        organizationRepository.save(organization);
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admin/organizations/edit/{id}")
    public String editOrganization(@PathVariable long id, Model model) {
        model.addAttribute("organization", organizationRepository.findById(id).get());
        return "admin/edit-organization";
    }

    @PostMapping("/admin/organizations/edit/{id}")
    public String editOrganizationPerform(Organization organization) {
        organizationRepository.save(organization);
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admin/organization/delete/{id}")
    public String deleteOrganization(@PathVariable long id, Model model) {
        model.addAttribute("organization", organizationRepository.findById(id).get());
        return "admin/delete-organization-confirm";
    }

    @GetMapping("/admin/organization/deletePerform/{id}")
    public String deleteOrganizationPerform(@PathVariable long id) {
        organizationRepository.delete(organizationRepository.findById(id).get());
        return REDIRECT_ORGANIZATIONS;
    }

    @GetMapping("/admin/organization/delete/cancel")
    public String cancelDeleteOrganizationOperation() {
        return REDIRECT_ORGANIZATIONS;
    }
}
