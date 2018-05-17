package com.applicationswp.data;

import com.applicationswp.datacontroller.Appointments;
import com.applicationswp.datacontroller.Timetables;
import com.applicationswp.dataparser.TimeParser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert einen Stundenplan.
 * Enthaelt eine Liste von Tagen, welche den Stundenplan darstellen.
 */
@Entity
public class TimeTable extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String title;
    private long userID;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="timeTable")
    private List<Appointment> appointments;

    public TimeTable(){
        appointments = new ArrayList<>();
    }

    /**
     * Getter fuer alle Tage im Stundenplan
     * @return alle Tage im Stundenplan
     */
    public List<Day> getDays() {
        List<Day> days = new ArrayList<>();
        int counter = 0;
        int dayCounter = 0;
        String[] dayNames = TimeParser.getDays();

        Day currentDay = new Day();
        currentDay.setName(dayNames[dayCounter]);
        for(Appointment appointment:appointments){
            if(counter == 8){
                dayCounter ++;
                days.add(currentDay);
                currentDay = new Day();
                currentDay.setName(dayNames[dayCounter]);
                counter = 0;
            }
            currentDay.getAppointments().add(appointment);

            counter++;
        }
        return days;
    }





    /**
     * Getter fuer den Titel des Stundeplans
     * @return den Titel des Stundeplans
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter fuer den Titel des Stundenplans
     * @param title der Titel des Stundeplans
     */
    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }


    /**
     * Statische Methode um einen leeren Stundenplan zu erstellen
     * @return einen leeren Stundenplan
     */
    public static TimeTable getEmpty(){
        TimeTable timeTable = new TimeTable();

        int counter = 0;
        for(int j=0;j<5;j++){
            for(int i=0;i<9;i++){
                Appointment appointment = new Appointment();
                appointment.setTitle("");
                timeTable.getAppointments().add(appointment);
                counter++;
            }
        }
        return timeTable;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}




