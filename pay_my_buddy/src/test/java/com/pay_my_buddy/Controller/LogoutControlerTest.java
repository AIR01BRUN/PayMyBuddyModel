package com.pay_my_buddy.Controller;

import com.pay_my_buddy.Service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoutControlerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LogoutController logoutController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogout_ShouldRedirectToLogin() {

        String redirection = logoutController.logout(session);

        verify(sessionService).loginUserOut(session);
        assertEquals("redirect:/login", redirection);
    }
}
