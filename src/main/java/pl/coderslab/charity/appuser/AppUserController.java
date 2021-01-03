package pl.coderslab.charity.appuser;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.donation.Donation;
import pl.coderslab.charity.donation.DonationRepository;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.organization.OrganizationRepository;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class AppUserController {

    private final AppUserRepository appUserRepository;
    private final DonationRepository donationRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private static final String APP_USER = "appuser";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String REGISTER_VIEW = "register";

    public AppUserController(AppUserRepository appUserRepository, DonationRepository donationRepository, OrganizationRepository organizationRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.appUserRepository = appUserRepository;
        this.donationRepository = donationRepository;
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute(APP_USER, new AppUser());
        return REGISTER_VIEW;
    }

    @PostMapping("/register")
    public String registerPerform(@Valid @ModelAttribute(name = "appuser") AppUser appUser, BindingResult result,
                                  @RequestParam String password2) throws MessagingException {

        if (result.hasErrors()) {
            return REGISTER_VIEW;
        }
        if (!appUser.getPassword().equals(password2)) {
            result.addError(new FieldError(APP_USER, "password", "Hasła są różne"));
            return REGISTER_VIEW;
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
        appUser.setVerificationToken(UUID.randomUUID().toString());
        //expiry date set to 24 hours
        appUser.setVerificationTokenExpiryDate(calculateTokenExpiryDate(24 * 60));
        emailService.sendEmailToActivateNewAccount(appUser.getEmail(), appUser.getVerificationToken());
        appUserRepository.save(appUser);
        return REDIRECT_INDEX;
    }

    @GetMapping("/verify")
    public String verifyAppUser(@RequestParam String token) {
        AppUser appUser = appUserRepository.findByVerificationToken(token);
        if (appUser == null || appUser.isEnabled()) {
            return REDIRECT_INDEX;
        } else if (appUser.getVerificationTokenExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            return "form-confirmation";
        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        return REDIRECT_INDEX;
    }

    private Timestamp calculateTokenExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(calendar.getTime().getTime());
    }

    @GetMapping("/recoverPassword")
    public String recoverPass() {
        return "forgot-password-form";
    }

    @PostMapping("/recoverPassword")
    public String recoverPassPerform(@RequestParam String email, Model model) throws MessagingException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            model.addAttribute("message", "Nie istnieje konto o takim emailu. Spróbuj ponownie.");
            return "forgot-password-form";
        }
        appUser.setChangePasswordToken(UUID.randomUUID().toString());
        appUser.setChangePasswordTokenExpiryDate(calculateTokenExpiryDate(3 * 60));
        emailService.sendEmailToChangeForgottenPassword(appUser.getEmail(), appUser.getChangePasswordToken());
        appUserRepository.save(appUser);
        return REDIRECT_INDEX;
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam String token, Model model) {
        AppUser appUser = appUserRepository.findByChangePasswordToken(token);
        if (appUser == null) {
            return "redirect:/login";
        } else if (appUser.getChangePasswordTokenExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            return "form-confirmation";
        }
        model.addAttribute(APP_USER, appUser);
        return "reset-password-form";
    }

    @PostMapping("/resetPassword")
    public String settingNewPassword(@Valid AppUser appUser, Model model, BindingResult result,
                                     @RequestParam String password2) {
        if (result.hasErrors() || !appUser.getPassword().equals(password2)) {
            model.addAttribute(APP_USER, appUser);
            model.addAttribute("diffpasswordsMessage", "Hasła są różne");
            return REGISTER_VIEW;
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);
        return "redirect:/login";
    }

    @GetMapping("/user/profile")
    public String editAppUsersProfileInfo(Model model) {
        AppUser loggedInUser = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        model.addAttribute("loggedInAppUser", loggedInUser);
        return "appuser-edit-profile";
    }

    @PostMapping("/user/profile")
    public String editAppUsersProfileInfoPerform(@Valid @ModelAttribute(name = "loggedInAppUser") AppUser loggedInAppUser,
                                                 BindingResult result, @RequestParam long id) {
        if (result.hasErrors()) {
            return "appuser-edit-profile";
        }
        AppUser databaseAppUser = appUserRepository.findById(id).get();
        databaseAppUser.setName(loggedInAppUser.getName());
        databaseAppUser.setEmail(loggedInAppUser.getEmail());
        appUserRepository.save(databaseAppUser);
        return "redirect:/user/home";
    }

    @GetMapping("/user/profile/changepassword")
    public String appUserChangePassword(Model model) {
        AppUser loggedInUser = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        model.addAttribute("loggedInAppUser", loggedInUser);
        return "appuser-change-password";
    }

    @PostMapping("/user/profile/changepassword")
    public String appUserChangePasswordPerform(@Valid @ModelAttribute(name = "loggedInAppUser") AppUser loggedInAppUser,
                                               BindingResult result, @RequestParam long id, @RequestParam String password2) {
        if (result.hasErrors()) {
            return "appuser-change-password";
        }
        if (!loggedInAppUser.getPassword().equals(password2)) {
            result.addError(new FieldError("loggedInAppUser", "password", "Hasła są różne"));
            return "appuser-change-password";
        }
        AppUser databaseAppUser = appUserRepository.findById(id).get();
        databaseAppUser.setPassword(passwordEncoder.encode(loggedInAppUser.getPassword()));
        appUserRepository.save(databaseAppUser);
        return "redirect:/user/home";
    }

    @GetMapping("/user/home")
    public String appUsersDonations(Model model) {
        AppUser loggedInUser = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        List<Donation> appUsersDonations = donationRepository.findAllByAppUser(loggedInUser);
        appUsersDonations.sort(Comparator.comparing(Donation::isPickedUp)
                .thenComparing(Donation::getPickUpDate, Comparator.reverseOrder())
                .thenComparing(Donation::getCreatedOn, Comparator.reverseOrder()));
        model.addAttribute("donations", appUsersDonations);
        return "appuser-donations";
    }

    @PostMapping("/user/home")
    public String changeDonationsPickedUpStatus(@RequestParam(required = false) boolean pickedUp,
                                                @RequestParam long id) {
        Donation donation = donationRepository.findById(id).get();
        donation.setPickedUp(pickedUp);
        donationRepository.save(donation);
        return "redirect:/user/home";
    }

    @GetMapping("/user/donation/{id}")
    public String showDonationsDetails(@PathVariable long id, Model model) {
        model.addAttribute("donation", donationRepository.findById(id).get());
        return "appuser-donation-details";
    }
}
