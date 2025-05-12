package com.pay_my_buddy.Controller;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ProfileController profileController;

    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1, "User", "user@example.com", "password");

        when(session.getAttribute("userID")).thenReturn(user.getId());
        when(userService.getUserById(user.getId())).thenReturn(user);
    }

    @Test
    void testShownProfilePage() {
        String view = profileController.shownProfilePage(redirectAttributes, session);

        verify(redirectAttributes).addAttribute("activePage", "profile");
        verify(redirectAttributes).addAttribute("user", user);
        assertEquals("profile", view);
    }

    @Test
    void testEditProfile_EmailAlreadyUsed() {
        String newEmail = "used@example.com";
        when(userService.userExistsByEmail(newEmail)).thenReturn(true);

        String view = profileController.editProfile("NewName", newEmail, "newPass", redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Email deja utiliser");
        assertEquals("redirect:/profile", view);
    }

    @Test
    void testEditProfile_Success() {
        String newEmail = "new@example.com";
        String newPassword = "newPassword";
        String newUsername = "NewUsername";

        when(userService.userExistsByEmail(newEmail)).thenReturn(false);

        String view = profileController.editProfile(newUsername, newEmail, newPassword, redirectAttributes, session);

        assertEquals(newEmail, user.getEmail());
        assertEquals(newPassword, user.getPassword());
        assertEquals(newUsername, user.getUsername());

        verify(userService).addUser(user);
        verify(redirectAttributes).addFlashAttribute("success", "Profil modifier");

        assertEquals("redirect:/profile", view);
    }
}
