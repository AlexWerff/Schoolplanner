package com.applicationswp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert einen Termin.
 * Wird auch fuer den Stundenplan genutzt.
 */
@Entity
public class Appointment extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String title;
    private String description;
    private long beginTime;
    private long endTime;
    private int roomID;
    private int appointmentTypeID;
    private int creatorID;

    @ManyToMany
    private List<Course> courses;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="TIMETABLE_ID")
    private TimeTable timeTable;

    /**
     * Contstructor
     */
    public Appointment(){
        title ="";
        description = "";
        ID =-1;
        beginTime = 0;
        endTime = 0;
        roomID = -1;
        this.courses = new ArrayList<>();
    }

    /**
     * Getter fuer den Titel
     * @return den Titel
     */
    public String getTitle(){
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
     * Getter fuer die ID
     * @return ID die ID
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

    /**
     * Getter fuer die Anfangzeit
     * @return die Anfangszeit
     */
    public long getBeginTime() {
        return beginTime;
    }

    /**
     * Setter fuer die Anfangszeit
     * @param beginTime die Anfangszeit
     */
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter fuer die Endzeit
     * @return die Endzeit
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Setter fuer die Endzeit
     * @param endTime die Endzeit
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter fuer die Raum ID
     * @return die Raum ID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Setter fuer die Raum ID
     * @param roomID die Raum ID
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Getter fuer den Termintypen
     * @return den Termintypen
     */
    public int getAppointmentTypeID() {
        return appointmentTypeID;
    }

    /**
     * Setter fuer den Termintypen
     * @param appointmentTypeID den Termintypen
     */
    public void setAppointmentTypeID(int appointmentTypeID) {
        this.appointmentTypeID = appointmentTypeID;
    }

    /**
     * Getter fuer die Ersteller ID
     * @return die Ersteller ID
     */
    public int getCreatorID() {
        return creatorID;
    }

    /**
     * Setter fuer die Ersteller ID
     * @param creatorID die Ersteller ID
     */
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    /**
     * Getter fuer alle zugehoerigen Kurse
     * @return alle zugehoerigen Kurse
     */
    public List<Course> getCourses() {
        return courses;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
}
