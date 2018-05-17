package com.applicationswp.data;

import com.applicationswp.dataparser.TimeParser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 *Diese Klasse repraesentiert eine Aufgabe.
 */
@Entity
public class Task extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String description;
    private long userID;
    private long dueTime;
    private long courseID;
    private TaskStatus status;

    public Task(){

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
     * Getter fuer die UserID
     * @return die UserID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Setter fuer die UserID
     * @param userID die UserID
     */
    public void setUserID(long userID) {
        this.userID = userID;
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

    /**
     * Getter fuer die Deadline
     * @return die Deadline
     */
    public long getDueTime() {
        return dueTime;
    }

    /**
     * Getter fuer die formatierte Deadline (als Timestring)
     * @return die formatierte Deadline (als Timestring)
     */
    public String getDueTimeFormatted() {
        return new Date(dueTime*1000).toLocaleString();
    }

    /**
     * Setter fuer die Deadline
     * @param dueTime die Deadline
     */
    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    /**
     * Getter fuer den Status
     * @return den Status
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Getter fuer den Status als String
     * @return den Status als String
     */
    public String getStringStatus(){
        return getStatus() == TaskStatus.OPEN ? "Offen" : "Erledigt";
    }

    /**
     * Getter fuer den Deadline Wochentag
     * @return den Deadline Wochentag
     */
    public String getTaskDueDay(){
        return "Monday";
    }

    /**
     * Getter fuer das Deadline Datum
     * @return das Deadline Datum
     */
    public String getTaskDueDate(){
        return "17.11.2016";
    }

    /**
     * Stter fuer den Status
     * @param status den Status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Getter fuer die KursID
     * @return die KursID
     */
    public long getCourseID() {
        return courseID;
    }

    /**
     * Setter fuer die KursID
     * @param courseID die KursID
     */
    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }
}
