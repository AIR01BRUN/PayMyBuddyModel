package com.pay_my_buddy.Controller;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.SessionService;
import com.pay_my_buddy.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RegisterController registerController;

    User newUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newUser = new User(1, "UserTest", "test@example.com", "Password");

        when(userService.getUserByEmail(newUser.getEmail())).thenReturn(newUser);
    }

    @Test
    void testShowRegisterPage() {
        var model = mock(org.springframework.ui.Model.class);

        String view = registerController.showRegisterPage(model);

        verify(model).addAttribute("activePage", "register");
        assertEquals("register", view);
    }

    @Test
    void testRegister_UserAlreadyExists() {
        when(userService.userExistsByEmail(newUser.getEmail())).thenReturn(true);

        String view = registerController.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(),
                redirectAttributes,
                session);

        assertEquals("redirect:/register", view);
    }

    @Test
    void testRegister_Successful() {
        when(userService.userExistsByEmail(newUser.getEmail())).thenReturn(false);

        String view = registerController.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(),
                redirectAttributes,
                session);

        verify(userService).addUser(any(User.class));
        verify(sessionService).loginUserIn(session, newUser.getId());
        assertEquals("redirect:/transaction", view);
    }
}
