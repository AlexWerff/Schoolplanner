package com.applicationswp.data;

import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert einen Tag.
 * Wird vom Stundenplan genutzt.
 */
public class Day{
    private String name;
    private List<Appointment> appointments;

    public Day(){
        this.name = "";
        this.appointments = new ArrayList<>();
    }

    /**
     * Getter fuer den Namen des Tages
     * @return den Namen des Tages
     */
    public String getName() {
        return name;
    }

    /**
     * Setter fuer den Namen des Tages
     * @param name den Namen des Tages
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter fuer alle Termine des Tages
     * @return alle Termine des Tages
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }
}