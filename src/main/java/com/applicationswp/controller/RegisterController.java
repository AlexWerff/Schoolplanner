package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Member;
import com.applicationswp.data.RegisterCredit;
import com.applicationswp.dataparser.UserFactory;
import com.applicationswp.security.MD5Generator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Diese Klasse ist der Controller fuer die Registrierung-Seite und verwaltet diese.
 * Die Funktionen der Registrierung-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Registrierung-Seite.
 */
@ManagedBean
@ViewScoped
public class RegisterController {

    @ManagedProperty("#{MainApplication}")
    private MainApplication mainApplication;

    private String resultMessage;
    private String registerPassword;

    /**
     * Konstruktor
     */
    public RegisterController(){

    }

    /**
     * Registriert einen neuen Benutzer
     * @param username Benutzername
     * @param password Passwort
     * @param passwordRepeat Wiederholtes Passwort
     */
    public void register(String username,String password,String passwordRepeat){
        if(!password.equals(passwordRepeat)){
            resultMessage = "Passwörter stimmen nicht überein";
            return;
        }
        RegisterCredit registerCredit = null;

        for(RegisterCredit credit :ApplicationCacheController.getInstance().getRegisterCredits()){
            if(credit.getPassword() != null && credit.getPassword().equals(registerPassword)){
                registerCredit = credit;
                break;
            }
        }

        if(registerCredit == null || registerCredit.getQuantity() == 0){
            resultMessage = "Registrierungspasswort ungültig";
            return;
        }
        registerCredit.setQuantity(registerCredit.getQuantity()-1);
        Member newMember = UserFactory.getNewDefaultMember(username, MD5Generator.getMD5(password));
        newMember.setPermissions(registerCredit.getPermissions());
        ApplicationCacheController.getInstance().getMembers().addEntry(newMember);
        mainApplication.getAuthenticationController().authenticate(username,password);

        resultMessage ="";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml?userID="+newMember.getID());
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
     * Getter fuer die Fehlermeldung
     * @return die Fehlermeldung
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Setter fuer die Fehlermeldung
     * @param resultMessage die Fehlermeldung
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * Getter fuer das Registrierungspasswort
     * @return das Registrierungspasswort
     */
    public String getRegisterPassword() {
        return registerPassword;
    }

    /**
     * Getter fuer das Registrierungspasswort
     * @param registerPassword das Registrierungspasswort
     */
    public void setRegisterPassword(String registerPassword) {
        this.registerPassword = registerPassword;
    }
}
