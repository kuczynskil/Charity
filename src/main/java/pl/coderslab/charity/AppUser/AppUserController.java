package pl.coderslab.charity.AppUser;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.Email.EmailService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Controller
public class AppUserController {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AppUserController(AppUserRepository appUserRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.appUserRepository = appUserRepository;
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
        model.addAttribute("appuser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerPerform(@Valid AppUser appUser, BindingResult result, Model model,
                                  @RequestParam String password2) throws MessagingException {

        if (result.hasErrors() || !appUser.getPassword().equals(password2)) {
            model.addAttribute("appuser", appUser);
            model.addAttribute("diffpasswordsMessage", "Hasła są różne");
            return "register";
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
        appUser.setVerificationToken(UUID.randomUUID().toString());
        //expiry date set to 24 hours
        appUser.setVerificationTokenExpiryDate(calculateTokenExpiryDate(24 * 60));
        emailService.sendEmailToActivateNewAccount(appUser.getEmail(), appUser.getVerificationToken());
        appUserRepository.save(appUser);
        return "redirect:/";
    }

    @GetMapping("/verify")
    public String verifyAppUser(@RequestParam String token) {
        AppUser appUser = appUserRepository.findByVerificationToken(token);
        if (appUser == null || appUser.isEnabled()) {
            return "redirect:/";
        } else if (appUser.getVerificationTokenExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            return "form-confirmation";
        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        return "redirect:/";
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
        return "redirect:/";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam String token, Model model) {
        AppUser appUser = appUserRepository.findByChangePasswordToken(token);
        if (appUser == null) {
            return "redirect:/login";
        } else if (appUser.getChangePasswordTokenExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            return "form-confirmation";
        }
        model.addAttribute("appuser", appUser);
        return "reset-password-form";
    }

    @PostMapping("/resetPassword")
    public String settingNewPassword(Model model,
                                     @RequestParam String password, @RequestParam String email) {
        AppUser appUser = appUserRepository.findByEmail(email);
//        if (!appUser.getPassword().equals(password2)) {
//            model.addAttribute("appuser", appUser);
//            model.addAttribute("diffpasswordsMessage", "Hasła są różne");
//            return "reset-password-form";
//        }
        System.out.println(password);
        System.out.println(email);
        System.out.println(appUser.getEmail());
        appUser.setPassword(passwordEncoder.encode(password));
        appUserRepository.save(appUser);
        return "redirect:/login";
    }
}
