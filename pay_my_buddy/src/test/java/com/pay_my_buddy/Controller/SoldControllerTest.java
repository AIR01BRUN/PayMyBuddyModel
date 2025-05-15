package com.pay_my_buddy.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.UserService;

import jakarta.servlet.http.HttpSession;

class SoldControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;
    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private SoldController soldController;

    private User user;
    private User bank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(2, "test", "test@test.com", "test");
        user.setAmount(100);

        bank = new User(1, "bank", "bank@bank.com", "bank");
        bank.setAmount(1000);

    }

    @Test
    void testShownSoldPage() {
        when(session.getAttribute("userID")).thenReturn(2);
        when(userService.getUserById(2)).thenReturn(user);

        String viewName = soldController.shownSoldPage(model, session);

        assertEquals("sold", viewName);
        verify(model).addAttribute("soldUser", user.getAmount());
        verify(model).addAttribute("activePage", "sold");
    }

    @Test
    void testAddSold_Negative() {
        when(session.getAttribute("userID")).thenReturn(2);

        String result = soldController.addSold(-50, model, session, redirectAttributes);

        assertEquals("redirect:/sold", result);
        verify(redirectAttributes).addFlashAttribute("error", "Doit etre supérieur a zero");
        verify(userService, never()).addSold(any(), anyDouble());
    }

    @Test
    void testAddSold_Positive() {
        when(session.getAttribute("userID")).thenReturn(2);
        when(userService.getUserById(2)).thenReturn(user);
        when(userService.getUserById(1)).thenReturn(bank);
        when(userService.monetizationPercentage(100)).thenReturn(5.0);

        String result = soldController.addSold(100, model, session, redirectAttributes);

        assertEquals("redirect:/sold", result);

        verify(redirectAttributes).addFlashAttribute("success", "100.0EUR Ajouter");
    }
}