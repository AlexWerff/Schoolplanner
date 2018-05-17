package com.applicationswp.data;

/**
 *Diese Klasse repraesentiert einen Raum.
 */
public class Room extends BasicEntry{
    private long ID;
    private String description;
    private String location;

    public Room(){

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
     * Getter fuer die Location
     * @return die Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter fuer die Location
     * @param location die Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
