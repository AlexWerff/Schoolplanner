package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.controller.cache.SessionCacheController;
import com.applicationswp.data.EditablePage;
import com.applicationswp.datacontroller.AppointmentTypes;
import com.applicationswp.datacontroller.EditablePages;
import com.applicationswp.datacontroller.MemberRoles;
import com.applicationswp.dataparser.AppointmentParser;
import com.applicationswp.dataparser.EditablePageFactory;
import com.applicationswp.dataparser.UserFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * MainApplication fuer jeden Benutzer
 * Wird fuer jede Session erstellt und nach Ende der Session wieder zerstoert
 * Enthaelt den AuthenticationController und den SessionCacheController
 * Ist ein Singleton fuer jede Session
 */
@ManagedBean(name="MainApplication", eager=true)
@SessionScoped
public class MainApplication {
    private static boolean fistLaunch = true;
    private static MainApplication instance;
    private final AuthenticationController authenticationController;
    private final SessionCacheController sessionCacheController;

    /**
     * Konstruktor
     * Initialisiert den AuthenticationController und den SessionCacheController
     */
    public MainApplication(){
        this.authenticationController = new AuthenticationController();
        this.sessionCacheController = new SessionCacheController(this.authenticationController);
        initFirstLaunch();
    }

    /**
     * Getter fuer den Applikationstitel
     * @return den Applikationstitel
     */
    public String getTitle(){
        return "Application SWP";
    }

    /**
     * Getter fuer den AuthenticationController
     * @return den AuthenticationController
     */
    public AuthenticationController getAuthenticationController() {
        return authenticationController;
    }


    /**
     * Getter fuer den SessionCacheController
     * @return den SessionCacheController
     */
    public SessionCacheController getSessionCacheController() {
        return sessionCacheController;
    }


    /**
     * Initialisiert alle Daten beim ersten Start des Programmes
     * Legt alle Standardrollen an etc.
     */
    public void initFirstLaunch(){
        fistLaunch = false;

        EditablePages editablePages = new EditablePages();
        editablePages.load();
        if(editablePages.size() == 0) {
            editablePages.addAll(EditablePageFactory.getEditablePages());
            editablePages.save();
        }
        try {
            for (EditablePage page : editablePages) {
                EditablePageFactory.setContentForPage(page, page.getContent());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        MemberRoles memberRoles = new MemberRoles();
        memberRoles.load();
        if(memberRoles.size() == 0){
            memberRoles.addAll(UserFactory.getDefaultMemberRoles());
            memberRoles.save();
        }


        AppointmentTypes appointmentTypes = new AppointmentTypes();
        appointmentTypes.load();

        if(appointmentTypes.size() == 0){
            appointmentTypes.addAll(AppointmentParser.getDefaultAppointmentTypes());
            appointmentTypes.save();
        }
    }
}

