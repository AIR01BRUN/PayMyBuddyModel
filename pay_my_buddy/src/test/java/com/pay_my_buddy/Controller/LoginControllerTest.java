package com.pay_my_buddy.Controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.SessionService;
import com.pay_my_buddy.Service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginController loginController;

    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(0, "test", "test@example.com", "password");

    }

    @Test
    void testLogin_Success() {
        String email = "test@example.com";
        String password = "password";

        when(userService.authenticateUser(email, password)).thenReturn(true);
        when(userService.getUserByEmail(email)).thenReturn(user);

        String viewName = loginController.login(email, password, redirectAttributes, session);

        verify(sessionService).loginUserIn(session, user.getId());
        assertEquals("redirect:/transaction", viewName);
    }

    @Test
    void testLogin_WrongPassword() {
        String email = "test@example.com";
        String password = "wrong";

        when(userService.authenticateUser(email, password)).thenReturn(false);

        String viewName = loginController.login(email, password, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Email ou mot de passe incorrect.");
        assertEquals("redirect:/login", viewName);
    }

}
