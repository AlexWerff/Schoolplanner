package com.applicationswp.controller;

import com.applicationswp.data.Member;
import com.applicationswp.security.CookieUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Diese Klasse ist der Controller fuer die Login-Seite und verwaltet diese.
 * Die Funktionen der Login-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Login-Seite.
 */
@ManagedBean
@SessionScoped
public class LoginController {
    private String resultMessage;
    @ManagedProperty("#{MainApplication}")
    private MainApplication mainApplication;

    public LoginController(){
        resultMessage = "";
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     */
    @PostConstruct
    public void init() {
        boolean result = mainApplication.getAuthenticationController().isAuthenticated("");
    }

    /**
     * Getter fuer die Login Meldung
     * @return die Login Meldung (Login fehlgeschlagen oder erfolgreich eingeloggt)
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Setter fuer die Login Meldung
     * @param resultMessage die Login Meldung
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }




    /**
     * Loggt den Benutzer mithilfe von Benutzername und Passwort ein
     * Verwendet den AuthenticationController der MainApplication
     * @param username der Benutzername
     * @param password das Passwort
     */
    public void login(String username,String password){
        boolean result = mainApplication.getAuthenticationController().authenticate(username,password);
        if(result == false){
            resultMessage ="Wrong username";
        }
        else{
            resultMessage ="";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Gibt zurueck ob ein Benutzer aktuell eingeloggt ist
     * @return true wenn ein Benutzer eingeloggt ist und false wenn nicht
     */
    public boolean getLoggedIn(){
        return mainApplication.getAuthenticationController().isLoggedIn();
    }

    public void logout(){
        mainApplication.getAuthenticationController().unauthenticate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("landing.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Getter fuer den Namen des aktuellen Benutzers
     * @return den Namen des aktuellen Benutzers
     */
    public String getUsername() {
        Member currentMember = mainApplication.getAuthenticationController().getMember();
        return currentMember != null ? currentMember.getUsername() : "";
    }
}
