package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import com.pay_my_buddy.Model.Transaction;
import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.TransactionService;
import com.pay_my_buddy.Service.UserService;

@Controller
public class TransactionController {

    private UserService userService;

    private TransactionService transactionService;

    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transaction")
    public String showTransactionPage(Model model, HttpSession session) {

        model.addAttribute("activePage", "transaction");

        User user = userService.getUserById((int) session.getAttribute("userID"));
        model.addAttribute("relations", userService.getRelationsUser(user));
        model.addAttribute("transferes", transactionService.getTransfereDTO(user.getId()));

        return "transaction";

    }

    @PostMapping("/transaction")
    public String transaction(@RequestParam String relation,
            @RequestParam String amount,
            @RequestParam String description,
            RedirectAttributes redirectAttributes, HttpSession session) {

        // Récupération de l'utilisateur
        User sender = userService.getUserById((int) session.getAttribute("userID"));

        if ("0".equals(relation)) {
            redirectAttributes.addFlashAttribute("error", "Sélectionnez une relation.");
            return "redirect:/transaction";
        }

        double amountDouble;
        try {
            amountDouble = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Entrez un montant valide.");
            return "redirect:/transaction";
        }

        if (amountDouble <= 0) {
            redirectAttributes.addFlashAttribute("error", "Le montant doit être supérieur à 0.");
            return "redirect:/transaction";
        }

        if (!userService.hasAmount(sender, amountDouble)) {
            redirectAttributes.addFlashAttribute("error", "Solde insuffisant.");
            return "redirect:/transaction";
        }

        User receiver = userService.getUserById(Integer.parseInt(relation));
        transactionService.addTransaction(new Transaction(description, amountDouble, sender, receiver));
        userService.soldTransfer(receiver, sender, amountDouble);

        redirectAttributes.addFlashAttribute("success", amount + " EUR envoyé à " + receiver.getEmail());
        return "redirect:/transaction";
    }

}
