package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.UserService;

@Controller
public class ProfileController {

    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String shownProfilePage(Model model, HttpSession session) {

        model.addAttribute("activePage", "profile");
        User user = userService.getUserById((int) session.getAttribute("userID"));
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String editProfile(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, RedirectAttributes redirectAttributes,
            HttpSession session) {

        User user = userService.getUserById((int) session.getAttribute("userID"));
        if (userService.userExistsByEmail(email) && !email.equals(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email deja utiliser");
        } else {
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("success", "Profil modifier");
        }

        return "redirect:/profile";

    }

}
