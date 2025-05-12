package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.SessionService;
import com.pay_my_buddy.Service.UserService;

@Controller
public class RegisterController {

    private UserService userService;

    private SessionService sessionService;

    public RegisterController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("activePage", "register");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (userService.userExistsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email deja utiliser");
            return "redirect:/register";
        } else {
            User user = new User(username, email, password);
            userService.addUser(user);
            user = userService.getUserByEmail(email);
            sessionService.loginUserIn(session, user.getId());
            return "redirect:/transaction";
        }
    }

}
