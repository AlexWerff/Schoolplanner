package com.applicationswp.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *Diese Klasse repraesentiert einen Termin-Typen.
 */
@Entity
public class AppointmentType extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String name;

    public AppointmentType(){

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
     * Getter fuer den Namen
     * @return den Namen
     */
    public String getName() {
        return name;
    }

    /**
     * Setter fuer den Namen
     * @param name den Namen
     */
    public void setName(String name) {
        this.name = name;
    }
}
