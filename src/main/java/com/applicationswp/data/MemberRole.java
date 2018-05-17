package com.applicationswp.data;

import javax.persistence.*;

/**
 *Diese Klasse repraesentiert eine Benutzerrolle.
 */
@Entity
public class MemberRole extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String title;
    private String description;
    @Column(length = 500)
    private String permissions;


    public MemberRole(){

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
     * Getter fuer die Berechtiungen als CSV
     * @return die Berechtiungen als CSV
     */
    public String getPermissions() {
        return permissions;
    }

    /**
     * Setter fuer die Berechtigungen als CSV
     * @param permissions die Berechtigungen als CSV
     */
    public void setPermissions(String permissions) {
        this.permissions = permissions;
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


}
