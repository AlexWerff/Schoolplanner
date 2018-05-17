package com.applicationswp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert eine Ankuendigung.
 */
@Entity
public class Announcement extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String title;
    private String description;
    private AnnouncementPriority priority;

    @ManyToMany
    private List<Course> courses;

    public Announcement(){
        this.courses = new ArrayList<>();
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
     * Getter fuer die ID
     * @return die ID
     */
    @Override
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
     * Getter fuer alle zugehoerigen Kurse
     * @return alle zugehoerigen Kurse
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Getter fuer die Prioritaet
     * @return die Prioritaet
     */
    public AnnouncementPriority getPriority() {
        return priority;
    }

    /**
     * Setter fuer die Prioritaet
     * @param priority die Prioritaet
     */
    public void setPriority(AnnouncementPriority priority) {
        this.priority = priority;
    }
}
