package com.pay_my_buddy.Controller;

import com.pay_my_buddy.DTO.TransactionDTO;
import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.TransactionService;
import com.pay_my_buddy.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import com.pay_my_buddy.DTO.RelationDTO;
import com.pay_my_buddy.DTO.TransfereDTO;

public class TransactionControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController(userService, transactionService);
    }

    @Test
    void testShowTransactionPage() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        List<RelationDTO> relations = Arrays.asList(new RelationDTO(), new RelationDTO());
        List<TransfereDTO> transfers = Arrays.asList(new TransfereDTO(), new TransfereDTO());

        when(session.getAttribute("userID")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);
        when(userService.getRelationsUser(user)).thenReturn(relations);
        when(transactionService.getTransfereDTO(userId)).thenReturn(transfers);

        String viewName = transactionController.showTransactionPage(model, session);

        assertEquals("transaction", viewName);
        verify(model).addAttribute("activePage", "transaction");
        verify(model).addAttribute("relations", relations);

    }

    @Test
    void testTransaction_WithError() {
        int userId = 1;
        String relation = "friend@example.com";
        String amount = "10.00";
        String description = "Lunch";
        User user = new User();
        user.setId(userId);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setError("Insufficient funds");

        when(session.getAttribute("userID")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);
        when(transactionService.processTransaction(userId, relation, amount, description)).thenReturn(transactionDTO);

        String result = transactionController.transaction(relation, amount, description, redirectAttributes, session);

        assertEquals("redirect:/transaction", result);
        verify(redirectAttributes).addFlashAttribute("error", "Insufficient funds");
        verify(userService, never()).soldTransfer(any());
        verify(redirectAttributes, never()).addFlashAttribute(eq("success"), any());
    }

    @Test
    void testTransaction_Success() {
        int userId = 1;
        String relation = "friend@example.com";
        String amount = "20.00";
        String description = "Dinner";
        User user = new User();
        user.setId(userId);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setError(null);
        transactionDTO.setSuccess("Transaction successful");
        // Assuming Transaction is a class in your model
        com.pay_my_buddy.Model.Transaction transaction = new com.pay_my_buddy.Model.Transaction();
        transactionDTO.setTransaction(transaction);

        when(session.getAttribute("userID")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);
        when(transactionService.processTransaction(userId, relation, amount, description)).thenReturn(transactionDTO);

        String result = transactionController.transaction(relation, amount, description, redirectAttributes, session);

        assertEquals("redirect:/transaction", result);
        verify(userService).soldTransfer(transaction);
        verify(redirectAttributes).addFlashAttribute("success", "Transaction successful");
        verify(redirectAttributes, never()).addFlashAttribute(eq("error"), any());
    }
}
