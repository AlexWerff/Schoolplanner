package com.applicationswp.controller.cache;

import com.applicationswp.data.AppointmentType;
import com.applicationswp.datacontroller.*;

/**
 * Application Cache Controller
 * Use as Singleton since this caches data for the whole application
 */

public final class ApplicationCacheController {
    private Courses courses;
    private Members members;
    private AppointmentTypes appointmentTypes;
    private MemberRoles memberRoles;
    private EditablePages editablePages;
    private RegisterCredits registerCredits;

    private static ApplicationCacheController instance;

    /**
     * Konstruktor
     * Ininitialisiert alle Datencontroller
     */
    private ApplicationCacheController(){
        courses = new Courses();
        members = new Members();
        appointmentTypes = new AppointmentTypes();
        memberRoles = new MemberRoles();
        editablePages = new EditablePages();
        registerCredits = new RegisterCredits();
    }


    /**
     * Statischer Getter fuer den ApplicationCacheController
     * @return die singelton instanz des ApplicationCacheController
     */
    public static ApplicationCacheController getInstance(){
        if(instance == null){
            instance = new ApplicationCacheController();
        }
        return instance;
    }

    /**
     * Getter fuer den zuletzt verwendeten Kurs
     * @return den zuletzt verwendeten Kurs
     */
    public Courses getCourses(){
        if(courses.size() == 0) {
            courses.load();
        }
        return courses;
    }

    /**
     * Getter fuer den zuletzt verwendeten Benutzer
     * @return die zuletzt verwendeten Benutzer
     */
    public Members getMembers() {
        if(members.size() == 0) {
            members.load();
        }
        return members;
    }

    /**
     * Getter fuer den zuletzt verwendeten Termine
     * @return die zuletzt verwendeten Termine
     */
    public AppointmentTypes getAppointmentTypes() {
        if(appointmentTypes.size() == 0){
            appointmentTypes.load();
        }
        return appointmentTypes;
    }

    /**
     * Getter fuer den zuletzt verwendeten Benutzerrollen
     * @return die zuletzt verwendeten Benutzerrollen
     */
    public MemberRoles getMemberRoles() {
        if(memberRoles.size() == 0){
            memberRoles.load();
        }
        return memberRoles;
    }

    /**
     * Getter fuer den zuletzt verwendeten bearbeitbare Seiten
     * @return den zuletzt verwendeten bearbeitbaren Seiten
     */
    public EditablePages getEditablePages() {
        if(editablePages.size() == 0){
            editablePages.load();
        }
        return editablePages;
    }

    /**
     * Getter fuer den zuletzt verwendeten Credits
     * @return die zuletzt verwendeten Credits
     */
    public RegisterCredits getRegisterCredits() {
        if(registerCredits.size() == 0){
            registerCredits.load();
        }
        return registerCredits;
    }
}
