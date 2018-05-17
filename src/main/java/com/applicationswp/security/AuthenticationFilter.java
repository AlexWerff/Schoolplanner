package com.applicationswp.security;

import com.applicationswp.controller.AuthenticationController;
import com.applicationswp.controller.MainApplication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *  URL Filter welcher ueberprueft welche Seiten mit welchen Berechtigungen zugaenglich sind
 *
 */
public class AuthenticationFilter implements Filter {
    private List<SecurePage> securePages;


    private MainApplication mainApplication;

    public void destroy() {
    }

    /**
     * Filter der bei jedem Seitenaufruf ausgefuehrt wird
     * Sollte eine Seite nicht mit den aktuellen Berechtigungen des Benutzers erreichbar sein so wird zur Loginseite verwiesen
     * @param req der Seitenrequest
     * @param resp die Seitenantwort
     * @param chain die Weiterleitung
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (userHasPermission(req)) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).sendRedirect("login.xhtml");
        }

    }

    /**
     * Initialisiert das Seitenverzeichnis
     * @param config die Filterkonfiguration
     * @throws ServletException
     */
    public void init(FilterConfig config) throws ServletException {
        securePages = SecurePageFactory.getSecurePages();
    }

    private boolean userHasPermission(ServletRequest req){
        String servletPath = ((HttpServletRequest) req).getServletPath();
        for(SecurePage securePage:securePages){
            if(servletPath.equals(securePage.getServletPath())){
                if(mainApplication == null) { //TODO Optimize
                    HttpSession session = ((HttpServletRequest) req).getSession(false);
                    if(session != null)
                        mainApplication = (MainApplication) session.getAttribute("MainApplication");
                }
                if(mainApplication == null){
                    return false;
                }
                AuthenticationController authenticationController = mainApplication.getAuthenticationController();
                return authenticationController.isAuthenticated(securePage.getPermissionsRequired());
            }
        }
        return true;
    }

}
