package com.pay_my_buddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import com.pay_my_buddy.Service.SessionService;

@Controller
public class LogoutController {

    private SessionService sessionService;

    public LogoutController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.loginUserOut(session);
        return "redirect:/login";
    }

}
