package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.pay_my_buddy.Service.SessionService;
import com.pay_my_buddy.Service.UserService;

@Controller
public class LoginController {

    private UserService userService;

    private SessionService sessionService;

    public LoginController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("activePage", "login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
            RedirectAttributes redirectAttributes, HttpSession session) {

        if (userService.authenticateUser(email, password)) {
            sessionService.loginUserIn(session, userService.getUserByEmail(email).getId());
            return "redirect:/transaction";
        } else {
            redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect.");
            return "redirect:/login";

        }

    }

}
