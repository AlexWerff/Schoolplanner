package com.applicationswp.datacontroller;

import com.applicationswp.data.Appointment;
import com.applicationswp.data.Message;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Terminen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Appointments extends BasicEntryList<Appointment> {

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Appointment> getEntityClass() {
        return Appointment.class;
    }


    public void load(long userID){
        Query q = getEntityManager().createQuery("select u from Appointment u where u.creatorID="+userID);
        List<Appointment> result = q.getResultList();
        for(Appointment u:result){
            add(u);
        }
    }
}
