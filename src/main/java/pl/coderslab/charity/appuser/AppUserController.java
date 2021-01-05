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

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class AppUserController {

    private final AppUserRepository appUserRepository;
    private final DonationRepository donationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private static final String APP_USER = "appuser";
    private static final String LOGGED_IN_APP_USER = "loggedInAppUser";
    private static final String DIFFERENT_PASSWORDS_MESSAGE = "Hasła są różne";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String REDIRECT_USER_HOME = "redirect:/user/home";
    private static final String REGISTER_VIEW = "register";
    private static final String CHANGE_PASSWORD_VIEW = "appuser-change-password";

    public AppUserController(AppUserRepository appUserRepository, DonationRepository donationRepository,
                             RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder,
                             EmailService emailService) {
        this.appUserRepository = appUserRepository;
        this.donationRepository = donationRepository;
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
            result.addError(new FieldError(APP_USER, "password", DIFFERENT_PASSWORDS_MESSAGE));
            return REGISTER_VIEW;
        }
        AppUser userFoundByEnteredEmail = appUserRepository.findByEmail(appUser.getEmail());
        if (userFoundByEnteredEmail != null) {
            result.addError(new FieldError(APP_USER, "email",
                    "Konto o podanym adresie email już istnieje."));
            return REGISTER_VIEW;
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
        appUser.setVerificationToken(UUID.randomUUID().toString());
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
            return "verify-token-expired";
        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        return "verify-confirmation";
    }

    @GetMapping("/recoverPassword")
    public String recoverPass() {
        return "forgot-password-form";
    }

    @PostMapping("/recoverPassword")
    public String recoverPassPerform(@RequestParam String email, Model model) throws MessagingException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            model.addAttribute("message", "Nie istnieje konto o takim adresie email. Spróbuj ponownie.");
            return "forgot-password-form";
        }
        appUser.setChangePasswordToken(UUID.randomUUID().toString());
        appUser.setChangePasswordTokenExpiryDate(calculateTokenExpiryDate(3 * 60));
        emailService.sendEmailToChangeForgottenPassword(appUser.getEmail(), appUser.getChangePasswordToken());
        appUserRepository.save(appUser);
        return "forgot-password-confirmation";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam String token, Model model) {
        AppUser appUser = appUserRepository.findByChangePasswordToken(token);
        if (appUser == null) {
            return "redirect:/login";
        } else if (appUser.getChangePasswordTokenExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            return "donation-form-confirmation";
        }
        model.addAttribute(APP_USER, appUser);
        return "reset-password-form";
    }

    @PostMapping("/resetPassword")
    public String settingNewPassword(@Valid AppUser appUser, Model model, BindingResult result,
                                     @RequestParam String password2) {
        if (result.hasErrors() || !appUser.getPassword().equals(password2)) {
            model.addAttribute(APP_USER, appUser);
            model.addAttribute("diffpasswordsMessage", DIFFERENT_PASSWORDS_MESSAGE);
            return REGISTER_VIEW;
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);
        return "redirect:/login";
    }

    @GetMapping("/user/profile")
    public String editAppUsersProfileInfo(Model model) {
        addLoggedInUserToModel(model);
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
        return REDIRECT_USER_HOME;
    }

    @GetMapping("/user/profile/changepassword")
    public String appUserChangePassword(Model model) {
        addLoggedInUserToModel(model);
        return CHANGE_PASSWORD_VIEW;
    }

    @PostMapping("/user/profile/changepassword")
    public String appUserChangePasswordPerform(@Valid @ModelAttribute(name = "loggedInAppUser") AppUser loggedInAppUser,
                                               BindingResult result, @RequestParam long id, @RequestParam String password2) {
        if (result.hasErrors()) {
            return CHANGE_PASSWORD_VIEW;
        }
        if (!loggedInAppUser.getPassword().equals(password2)) {
            result.addError(new FieldError(LOGGED_IN_APP_USER, "password", DIFFERENT_PASSWORDS_MESSAGE));
            return CHANGE_PASSWORD_VIEW;
        }
        AppUser databaseAppUser = appUserRepository.findById(id).get();
        databaseAppUser.setPassword(passwordEncoder.encode(loggedInAppUser.getPassword()));
        appUserRepository.save(databaseAppUser);
        return REDIRECT_USER_HOME;
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
        model.addAttribute(LOGGED_IN_APP_USER, loggedInUser);
        return "appuser-donations";
    }

    // (required = false) allows boolean pickedUp
    // to have the 'false' value and be sent by form
    @PostMapping("/user/home")
    public String changeDonationsPickedUpStatus(@RequestParam(required = false) boolean pickedUp,
                                                @RequestParam long id) {
        Donation donation = donationRepository.findById(id).get();
        donation.setPickedUp(pickedUp);
        donationRepository.save(donation);
        return REDIRECT_USER_HOME;
    }

    @GetMapping("/user/donation/{id}")
    public String showDonationsDetails(@PathVariable long id, Model model) {
        model.addAttribute("donation", donationRepository.findById(id).get());
        addLoggedInUserToModel(model);
        return "appuser-donation-details";
    }

    private Timestamp calculateTokenExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(calendar.getTime().getTime());
    }

    private Model addLoggedInUserToModel(Model model) {
        AppUser loggedInUser = ((CurrentUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        return model.addAttribute(LOGGED_IN_APP_USER, loggedInUser);
    }
}
