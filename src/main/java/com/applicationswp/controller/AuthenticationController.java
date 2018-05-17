package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Member;
import com.applicationswp.datacontroller.Members;
import com.applicationswp.dataparser.UserFactory;
import com.applicationswp.security.MD5Generator;

import javax.faces.context.FacesContext;

/**
 * Authentication Controller
 * Verwaltet die Authentifikation.
 * Verwaltet die Rechte eines Benutzers.
 * Verwaltet den Benutzer.
 */
public class AuthenticationController {
    private Member member;
    private IAuthenticationListener authenticationListener;


    public AuthenticationController(){
    }



    /**
     * ueberprueft ob der aktuelle Benutzer ueber bestimmte Rechte verfuegt
     * @param permission die zu ueberpruefenden Rechte
     * @return true wenn der Benutzer die Rechte hat und false wenn nicht
     */
    public boolean isAuthenticated(String permission){
        if(getMember() == null || getMember().getPassword() == null)
            return false;
        return getMember().getPermissions().contains(permission);
    }


    /**
     * Authentifiziert einen Benutzer mithilfe von Benutzername und Passwort
     * Setzt wenn alle Daten korrekt sind den aktuellen Benutzer
     * @param username der Benutzername
     * @param password das Password (MD5 encoded)
     * @return true wenn erfolgreich authentifiziert und false wenn nicht
     */
    public boolean authenticate(String username,String password){
        Members members = ApplicationCacheController.getInstance().getMembers();
        String md5Password = MD5Generator.getMD5(password);
        if(members.size() == 0){
            this.member = UserFactory.getAdminUser(username,md5Password);
            members.addEntry(member);
        }
        for(Member member : members){
            if(member.getUsername() != null && member.getPassword() != null) {
                if (member.getUsername().equals(username) && member.getPassword().equals(md5Password)) {
                    this.member = member;
                    break;
                }
            }
        }
        if(authenticationListener != null){
            authenticationListener.loggedIn(member);
        }
        return this.member != null && !this.member.getUsername().isEmpty();
    }

    /**
     * Liefert ob ein Benutzer eingeloggt ist
     * @return true wenn ein Benutzer eingeloggt ist und false wenn nicht
     */
    public boolean isLoggedIn(){
        return this.member != null && !this.member.getUsername().isEmpty();
    }

    /**
     * Loescht den aktuellen Benutzer
     * @return true wenn erfolgreich und false wenn nicht
     */
    public boolean unauthenticate(){
        this.member = null;
        if(authenticationListener != null){
            authenticationListener.loggedOut();
        }
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return true;
    }


    /**
     * Getter fuer den aktuellen Benutzer
     * @return der aktuelle Benutzer
     */
    public Member getMember(){
        return this.member;
    }

    /**
     * Setter fuer den AuthenticationListener
     * @param authenticationListener den AuthenticationListener
     */
    public void setAuthenticationListener(IAuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    /**
     * Interface Listener Klasse fuer den AuthenticationController
     */
    public interface IAuthenticationListener{
        void loggedOut();
        void loggedIn(Member member);
    }

}
