package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AddRelationController {

    private UserService userService;

    public AddRelationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/relations")
    public String shownAddRelationPage(Model model, HttpSession session) {

        model.addAttribute("activePage", "relation");
        return "relation";

    }

    @PostMapping("/relations")
    public String addRelation(@RequestParam String email, RedirectAttributes redirectAttributes, HttpSession session) {

        if (userService.userExistsByEmail(email)) {
            User user = userService.getUserByEmail(email);
            User currentUser = userService.getUserById((int) session.getAttribute("userID"));

            if (!userService.hasRelation(currentUser, user) && !currentUser.getEmail().equals(email)) {
                currentUser.getConnections().add(user);
                user.getConnections().add(currentUser);
                userService.saveUser(currentUser);
                userService.saveUser(user);

                redirectAttributes.addFlashAttribute("success", user.getUsername() + " Ajouter");
            } else {
                redirectAttributes.addFlashAttribute("error", "Utilisateur deja dans vos relation");
            }

        } else {
            redirectAttributes.addFlashAttribute("error", "Email not found.");
        }
        return "redirect:/relations";

    }

}
