package com.applicationswp.controller;

/**
 * Exception fuer den Fall dass ein Benutzer die jeweiligen Rechte nicht besitzt.
 */
public class AuthenticationException extends Exception{
    /**
     * Konstruktor
     * @param permission die Rechte die erforderlich sind
     */
    public AuthenticationException(String permission){
        super(String.format("Authentication exception for permission: %s",permission));
    }
}
