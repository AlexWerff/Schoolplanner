package com.applicationswp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert einen Kurs.
 */
@Entity
public class Course extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String name;
    private String description;
    private int creatorID;
    @Column(length = 5000)
    private String phoneListJSON;

    @ManyToMany(mappedBy = "courses")
    @JoinTable(
            name="MEMBER_COURSE",
            joinColumns=@JoinColumn(name="MEMBERS_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="COURSES_ID", referencedColumnName="ID"))
    private List<Member> members;

    @ManyToMany(mappedBy = "courses")
    @JoinTable(
            name="FILE_COURSE",
            joinColumns=@JoinColumn(name="FILE_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="COURSES_ID", referencedColumnName="ID"))
    private List<File> files;

    @ManyToMany(mappedBy = "courses")
    @JoinTable(
            name="APPOINTMENT_COURSE",
            joinColumns=@JoinColumn(name="APPOINTMENT_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="COURSES_ID", referencedColumnName="ID"))
    private List<Appointment> events;

    @ManyToMany(mappedBy = "courses")
    @JoinTable(
            name="ANNOUNCEMENT_COURSE",
            joinColumns=@JoinColumn(name="ANNOUNCEMENT_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="COURSES_ID", referencedColumnName="ID"))
    private List<Announcement> announcements;




    public Course(){
        this.members = new ArrayList<>();
        this.files = new ArrayList<>();
        this.events = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.phoneListJSON = "[]";
    }

    /**
     * Getter fuer den Namen des Kurses
     * @return den Namen des Kurses
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fuer den Namen des Kurses
     * @param name den Namen des Kurses
     */
    public void setName(String name) {
        this.name = name;
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
     * Getter fuer die ErstellerID
     * @return die ErstellerID
     */
    public int getCreatorID() {
        return creatorID;
    }

    /**
     * Setter fuer die ErstellerID
     * @param creatorID die ErstellerID
     */
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    /**
     * Getter fuer  alle Teilnehmer des Kurses
     * @return alle Teilnehmer des Kurses
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Setter fuer alle Teilnehmer des Kurses
     * @param members alle Teilnehmer des Kurses
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }


    /**
     * Getter fuer alle Dateien des Kurses
     * @return alle Dateien des Kurses
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * Getter fuer alle Events des Kurses
     * @return alle Events des Kurses
     */
    public List<Appointment> getEvents() {
        return events;
    }

    /**
     * Getter fuer alle Ankuendigungen des Kurses
     * @return alle Ankuendigungen des Kurses
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public String getPhoneListJSON() {
        return phoneListJSON;
    }

    public void setPhoneListJSON(String phoneListJSON) {
        this.phoneListJSON = phoneListJSON;
    }
}
