package com.applicationswp.data;

import javax.persistence.*;

/**
 *Diese Klasse repraesentiert eine Vertretung.
 */
@Entity
public class Substitution extends BasicEntry{
    private int courseID;
    private String title;
    private String description;
    private int appointmentID;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private long timeFrom;
    private long timeTo;

    public Substitution(){
        ID = -1;
    }

    /**
     * Getter fuer die KursID
     * @return die KursID
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * Setter fuer die KursID
     * @param courseID die KursID
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Getter fuer den Titel
     * @return den Titel
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter fuer den Titel
     * @param title den Titel
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter fuer die Beschreibung
     * @return die Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter fuer die Beschreibung
     * @param description die Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter fuer die TerminID
     * @return die TerminID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter fuer die TerminID
     * @param appointmentID die TerminID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter fuer die ID
     * @return die ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Setter fuer die ID
     * @param ID die ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }
}
