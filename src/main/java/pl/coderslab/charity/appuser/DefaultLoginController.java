package pl.coderslab.charity.appuser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultLoginController {

    @RequestMapping("/default")
    public String defaultLoginPage(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/appusers";
        }
        return "redirect:/donate";
    }
}
