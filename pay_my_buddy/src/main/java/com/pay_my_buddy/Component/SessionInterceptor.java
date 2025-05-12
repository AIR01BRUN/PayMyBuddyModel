package com.pay_my_buddy.Component;

import com.pay_my_buddy.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private SessionService sessionService;

    public SessionInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Vérifier si l'utilisateur est connecté
        if (!sessionService.isUserLoggedIn(request.getSession())) {
            // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
            response.sendRedirect("/login");
            return false; // Arrêter l'exécution de la requête
        }
        return true; // Continuer l'exécution de la requête
    }
}