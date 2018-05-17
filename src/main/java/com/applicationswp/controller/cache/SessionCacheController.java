package com.applicationswp.controller.cache;

import com.applicationswp.controller.AuthenticationController;
import com.applicationswp.data.Course;
import com.applicationswp.data.Member;
import com.applicationswp.datacontroller.*;

/**
 * Cache Controller
 * Speichert Daten zwischen um unnoetige Datenbankzugriffe zu ersparen
 */
public class SessionCacheController implements AuthenticationController.IAuthenticationListener{
    private Course lastCourse;
    private int lastTimetable;
    private Timetables timeTables;
    private Appointments appointments;
    private Tasks tasks;
    private Messages messages;
    private Substitutions substitutions;
    private Files files;
    private Announcements announcements;
    private ExcuseNotes excuseNotes;
    private final AuthenticationController authenticationController;

    /**
     * Konstruktor
     * Ininitialisiert alle Datencontroller
     * Setzt den AuthenticationController udn den AuthenticationListener um den Cache beim Logout zu loeschen
     * @param authenticationController
     */
    public SessionCacheController(AuthenticationController authenticationController){
        this.authenticationController = authenticationController;
        this.authenticationController.setAuthenticationListener(this);
        timeTables = new Timetables();
        appointments = new Appointments();
        tasks = new Tasks();
        messages = new Messages();
        substitutions = new Substitutions();
        files = new Files();
        announcements = new Announcements();
        excuseNotes = new ExcuseNotes();
    }

    /**
     * Gibt die BenutzerID des zur Zeit angemeldeten Benutzers zuruek
     * @return die BenutzerID wenn der Benutzer gesetzt sonst -1
     */
    private long getOwnID(){
        return authenticationController.getMember() == null ? -1 :authenticationController.getMember().getID();
    }


    /**
     * Getter fuer den zuletzt verwendeten Kurs
     * @return den zuletzt verwendeten Kurs
     */
    public Course getLastCourse() {
        return lastCourse;
    }

    /**
     * Setter fuer den zuletzt verwendeten Kurs
     * @param lastCourse der zuletzt verwendeten Kurs
     */
    public void setLastCourse(Course lastCourse) {
        this.lastCourse = lastCourse;
    }

    /**
     * Getter fuer den letzten ausgewaehlten Stundenplan
     * @return den letzten ausgewaehlten Stundenplan
     */
    public int getLastTimetable() {
        return lastTimetable;
    }

    /**
     * Setter fuer den letzten ausgewaehlten Stundenplan
     * @param lastTimetable den letzten ausgewaehlten Stundenplan
     */
    public void setLastTimetable(int lastTimetable) {
        this.lastTimetable = lastTimetable;
    }


    /**
     * Getter fuer Stundenplaene
     * @return die Stundenplaene
     */
    public Timetables getTimeTables() {
        if(timeTables.size() == 0) {
            timeTables.load(getOwnID());
        }
        return timeTables;
    }

    /**
     * Getter fuer Aufgaben
     * @return die Aufgaben
     */
    public Tasks getTask() {
        if(tasks.size() == 0){
            tasks.load(getOwnID());
        }
        return tasks;
    }

    /**
     * Getter fuer Nachrichten
     * @return die Nachrichten
     */
    public Messages getMessages() {
        if(messages.size() == 0){
            messages.load(getOwnID());
        }
        return messages;
    }

    /**
     * Getter fuer Termine
     * @return die Termine
     */
    public Appointments getAppointments() {
        if(appointments.size() == 0){
            appointments.load(getOwnID());
        }
        return appointments;
    }

    /**
     * Getter fuer Vertretungen
     * @return die Vertretungen
     */
    public Substitutions getSubstitutions() {
        if(substitutions.size() == 0){
            substitutions.load();
        }
        return substitutions;
    }

    /**
     * Getter fuer Dateien
     * @return die Dateien
     */
    public Files getFiles() {
        if(files.size() == 0){
            files.load();
        }
        return files;
    }

    /**
     * Getter fuer Ankuendigungen
     * @return die Ankuendigungen
     */
    public Announcements getAnnouncements() {
        if(announcements.size() == 0){
            announcements.load();
        }
        return announcements;
    }

    /**
     * Getter fuer Entschuldigungen
     * @return die Entschuldigungen
     */
    public ExcuseNotes getExcuseNotes() {
        if(excuseNotes.size() == 0){
            excuseNotes.load(getOwnID());
        }
        return excuseNotes;
    }

    /**
     * Event-Handler fuer LoggedOut vom AuthenticationListener
     */
    @Override
    public void loggedOut() {
        timeTables = new Timetables();
        appointments = new Appointments();
        tasks = new Tasks();
        messages = new Messages();
        substitutions = new Substitutions();
        files = new Files();
        announcements = new Announcements();
        excuseNotes = new ExcuseNotes();
    }

    /**
     * Event-Handler fuer LoggedOut vom AuthenticationListener
     * @param member der neue eingeloggte Member
     */
    @Override
    public void loggedIn(Member member) {
        timeTables = new Timetables();
        appointments = new Appointments();
        tasks = new Tasks();
        messages = new Messages();
        substitutions = new Substitutions();
        files = new Files();
        announcements = new Announcements();
        excuseNotes = new ExcuseNotes();
    }
}
