package com.applicationswp.data;

import com.applicationswp.dataparser.TimeParser;

import javax.persistence.*;

/**
 *Diese Klasse repraesentiert eine Entschuldigung.
 */
@Entity
public class ExcuseNote extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String description;
    private int userID;
    private int excuseDateFrom;
    private int excuseDateTo;
    private int courseID;
    private ExcuseStatus status;

    @OneToOne(fetch=FetchType.LAZY)
    private File excuseFile;

    public ExcuseNote(){
        this.status = ExcuseStatus.OPEN;
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
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter fuer die UserID
     * @return die UserID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter fuer die UserID
     * @param userID die UserID
     */
    public void setUserID(int userID) {
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
     * Getter fuer den Fehlzeitenanfang
     * @return den Fehlzeitenanfang
     */
    public int getExcuseDateFrom() {
        return excuseDateFrom;
    }

    /**
     * Getter fuer den Formatierten Fehlzeitenanfang (Timestring)
     * @return den Formatierten Fehlzeitenanfang (Timestring)
     */
    public String getExcuseDateFromFormatted() { return TimeParser.timestampToTimestring(excuseDateFrom);}

    /**
     * Setter fuer den Fehlzeitenanfang
     * @return den Fehlzeitenanfang
     */
    public void setExcuseDateFrom(int excuseDate) {
        this.excuseDateFrom = excuseDate;
    }

    /**
     * Getter fuer das Fehlzeitenende
     * @return das Fehlzeitenende
     */
    public int getExcuseDateTo() {
        return excuseDateTo;
    }

    /**
     * Getter fuer das Formatierten Fehlzeitenende (Timestring)
     * @return das Formatierten Fehlzeitenende (Timestring)
     */
    public String getExcuseDateToFormatted() { return TimeParser.timestampToTimestring(excuseDateTo);}

    /**
     * Setter fuer das Fehlzeitenende
     * @param excuseDate das Fehlzeitenende
     */
    public void setExcuseDateTo(int excuseDate) {
        this.excuseDateTo = excuseDate;
    }

    /**
     * Getter fuer den Status
     * @return den Status
     */
    public ExcuseStatus getStatus() {
        return status;
    }

    /**
     * Getter fuer den Status als String
     * @return den Status als String
     */
    public String getStringStatus(){
        return getStatus() == ExcuseStatus.OPEN ? "Offen" : "Entschuldigt";
    }

    /**
     * Setter fuer den Status
     * @param status den Status
     */
    public void setStatus(ExcuseStatus status) {
        this.status = status;
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

    public File getExcuseFile() {
        return excuseFile;
    }

    public void setExcuseFile(File excuseFile) {
        this.excuseFile = excuseFile;
    }
}
