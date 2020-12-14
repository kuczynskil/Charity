package pl.coderslab.charity.AppUser;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.Email.EmailService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;

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
        emailService.sendEmail();
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("appuser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerPerform(@Valid AppUser appUser, BindingResult result, Model model,
                                  @RequestParam String password2) {

        if (!appUser.getPassword().equals(password2)) {
            result.addError(new FieldError("appuser", "password2", "Hasła są różne"));
        }
        if (result.hasErrors()) {
            model.addAttribute("appuser", appUser);
            return "register";
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
        appUserRepository.save(appUser);
        return "index";
    }
}
