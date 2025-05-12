package com.pay_my_buddy.Controller;

import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddRelationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AddRelationController addRelationController;

    User userSender;
    User userReceiver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSender = new User(1, "Sender", "Sender@example.com", "passwordSender");
        userReceiver = new User(2, "Receiver", "Receiver@example.com", "passwordReceiver");

        userSender.setConnections(new ArrayList<>());
        userSender.setConnections(new ArrayList<>());

        when(session.getAttribute("userID")).thenReturn(userSender.getId());
        when(userService.userExistsByEmail(userReceiver.getEmail())).thenReturn(true);
        when(userService.getUserByEmail(userReceiver.getEmail())).thenReturn(userReceiver);
        when(userService.getUserById(userSender.getId())).thenReturn(userSender);

    }

    @Test
    void testAddRelation_Successful() {

        when(userService.hasRelation(userSender, userReceiver)).thenReturn(false);

        String view = addRelationController.addRelation(userReceiver.getEmail(), redirectAttributes, session);

        verify(userService).saveUser(userSender);
        verify(userService).saveUser(userSender);
        verify(redirectAttributes).addFlashAttribute("success", userReceiver.getUsername() + " Ajouter");
        assertEquals("redirect:/relations", view);
    }

    @Test
    void testPostMethodName_EmailNotFound() {
        String emailReceiver = "notfound@example.com";

        when(userService.userExistsByEmail(emailReceiver)).thenReturn(false);

        String view = addRelationController.addRelation(emailReceiver, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Email not found.");
        assertEquals("redirect:/relations", view);
    }

    @Test
    void testPostMethodName_UserAlreadyRelated() {
        String emailReceiver = "Receiver@example.com";

        when(userService.hasRelation(userSender, userReceiver)).thenReturn(true);

        String view = addRelationController.addRelation(emailReceiver, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Utilisateur deja dans vos relation");
        assertEquals("redirect:/relations", view);
    }

    @Test
    void testPostMethodName_AddingSelfAsRelation() {
        String emailSender = "Sender@example.com";

        when(userService.userExistsByEmail(emailSender)).thenReturn(true);
        when(userService.getUserByEmail(emailSender)).thenReturn(userSender);
        when(userService.getUserById(userSender.getId())).thenReturn(userSender);
        when(userService.hasRelation(userSender, userSender)).thenReturn(false);

        String view = addRelationController.addRelation(emailSender, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("error", "Utilisateur deja dans vos relation");
        assertEquals("redirect:/relations", view);
    }

}
