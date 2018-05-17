package com.applicationswp.security;

import net.bootsfaces.render.E;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Hilfsklasse welche das Speichern und Laden von Cookies verwaltet
 * Statische Hilfsklasse
 */
public final class CookieUtils {

    private CookieUtils(){

    }

    /**
     * Schreibt einen Bezeichnung und einen Wert in einen Cookie (aehnlich Dictionary)
     * @param name die Bezeichnung des Cookie Eintrages
     * @param value den Wert fuer den Cookie Eintrag
     * @param expiry die Lebenszeit des Cookie Eintrages (in Sekunden)
     */
    public static void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0 ) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    /**
     * Gibt einen Cookie fuer eine Bezeichnung zurueck
     * @param name die Bezeichnung
     * @return den Cookie fuer die Bezeichnung
     */
    public static Cookie getCookie(String name) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                Cookie cookie = null;

                Cookie[] userCookies = request.getCookies();
                if (userCookies != null && userCookies.length > 0) {
                    for (int i = 0; i < userCookies.length; i++) {
                        if (userCookies[i].getName().equals(name)) {
                            cookie = userCookies[i];
                            return cookie;
                        }
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Loescht einen Cookie mit einer Bezeichnung
     * @param name die Bezeichnung des Cookies
     * @return true wenn erfolgreich und false wenn nicht
     */
    public static boolean removeCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            Cookie cookie = null;

            Cookie[] userCookies = request.getCookies();
            if (userCookies != null && userCookies.length > 0) {
                for (int i = 0; i < userCookies.length; i++) {
                    if (userCookies[i].getName().equals(name)) {
                        cookie = userCookies[i];
                        cookie.setMaxAge(0);
                        cookie.setValue("|");
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
