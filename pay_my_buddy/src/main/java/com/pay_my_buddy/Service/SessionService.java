package com.pay_my_buddy.Service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    private final String attributeNameID;

    public SessionService() {
        this.attributeNameID = "userID";
    }

    public void loginUserIn(HttpSession session, int idUser) {
        session.setAttribute(attributeNameID, idUser);
    }

    public boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute(attributeNameID) != null;
    }

    public void loginUserOut(HttpSession session) {
        session.removeAttribute(attributeNameID);
    }

}
