package com.applicationswp.security;

/**
 * Repraesentiert eine gesicherte Seite
 */
public class SecurePage {
    private String servletPath;
    private String permissionsRequired;

    public SecurePage(){

    }

    /**
     * Getter fuer den URL Pfad
     * @return den URL Pfad
     */
    public String getServletPath() {
        return servletPath;
    }

    /**
     * Setter fuer den URL Pfad
     * @param servletPath den URL Pfad
     * @return das objekt selbst
     */
    public SecurePage setServletPath(String servletPath) {
        this.servletPath = servletPath;
        return this;
    }

    /**
     * Setter fuer die notwendigen Rechte
     * @return die notwendigen Rechte
     */
    public String getPermissionsRequired() {
        return permissionsRequired;
    }

    /**
     * Setter fuer die notendigen Rechte
     * @param permissionsRequired die notwendigen Rechte
     * @return das objekt selbst
     */
    public SecurePage setPermissionsRequired(String permissionsRequired) {
        this.permissionsRequired = permissionsRequired;
        return this;
    }
}
