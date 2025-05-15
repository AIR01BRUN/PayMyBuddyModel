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
public class SoldController {

    private UserService userService;

    public SoldController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/sold")
    public String shownSoldPage(Model model, HttpSession session) {
        User currentUser = userService.getUserById((int) session.getAttribute("userID"));
        model.addAttribute("soldUser", currentUser.getAmount());
        model.addAttribute("activePage", "sold");
        return "sold";

    }

    @PostMapping("/sold")
    public String addSold(@RequestParam double sold, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User currentUser = userService.getUserById((int) session.getAttribute("userID"));

        if (sold < 0) {
            redirectAttributes.addFlashAttribute("error", "Doit etre supérieur a zero");
            return "redirect:/sold";
        }

        userService.addSold(currentUser, sold);
        redirectAttributes.addFlashAttribute("success", sold + "EUR Ajouter");
        return "redirect:/sold";
    }

}
