package com.pay_my_buddy.Controller;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.TransactionService;
import com.pay_my_buddy.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private TransactionController transactionController;

    User sender;
    User receiver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sender = new User(1, "Sender", "sender@example.com", "password");
        receiver = new User(2, "Receiver", "receiver@example.com", "password");

        when(session.getAttribute("userID")).thenReturn(sender.getId());
        when(userService.getUserById(sender.getId())).thenReturn(sender);
        when(userService.getUserById(receiver.getId())).thenReturn(receiver);
    }

    @Test
    void testShowNavigationPage_ReturnsTransactionView() {
        var model = mock(org.springframework.ui.Model.class);

        String view = transactionController.showTransactionPage(model, session);

        verify(model).addAttribute("activePage", "transaction");
        verify(model).addAttribute("relations", userService.getRelationsUser(sender));
        verify(model).addAttribute("transferes", transactionService.getTransfereDTO(sender.getId()));
        assertEquals("transaction", view);
    }

    @Test
    void tesTransaction_NoRelationSelected() {

        String relation = "0";
        String description = "Payment for services";
        String amount = "100";

        String view = transactionController.transaction(relation, description, amount, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Sélectionnez une relation.");
        assertEquals("redirect:/transaction", view);
    }

    @Test
    void testTransaction_EmptyAmount() {

        String relation = String.valueOf(receiver.getId());
        String description = "Payment for services";
        String amount = "";

        String view = transactionController.transaction(relation, description, amount, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Entrez un montant valide.");
        assertEquals("redirect:/transaction", view);
    }

}
