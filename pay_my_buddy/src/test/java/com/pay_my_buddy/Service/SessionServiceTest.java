package com.pay_my_buddy.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpSession;

class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    HttpSession mockSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockSession = mock(HttpSession.class);
    }

    @Test
    void testLoginUserIn() {
        int userId = 1;

        sessionService.loginUserIn(mockSession, userId);

        verify(mockSession).setAttribute("userID", userId);

    }

    @Test
    void testIsUserLoggedIn() {

        when(mockSession.getAttribute("userID")).thenReturn(123);

        boolean result = sessionService.isUserLoggedIn(mockSession);

        assertTrue(result);
    }

    @Test
    void testIsNotUserLoggedIn() {

        when(mockSession.getAttribute("userID")).thenReturn(null);

        boolean result = sessionService.isUserLoggedIn(mockSession);

        assertFalse(result);
    }

    @Test
    void testloginUserOut() {

        sessionService.loginUserOut(mockSession);

        verify(mockSession).removeAttribute("userID");
        assertNull(mockSession.getAttribute("userID"));
    }

}
