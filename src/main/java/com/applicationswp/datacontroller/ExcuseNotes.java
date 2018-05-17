package com.applicationswp.datacontroller;

import com.applicationswp.data.Appointment;
import com.applicationswp.data.ExcuseNote;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Entschuldigungen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class ExcuseNotes extends BasicEntryList<ExcuseNote> {

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<ExcuseNote> getEntityClass() {
        return ExcuseNote.class;
    }

    public void load(long userID){
        Query q = getEntityManager().createQuery("select u from ExcuseNote u where u.userID="+userID);
        List<ExcuseNote> result = q.getResultList();
        for(ExcuseNote u:result){
            add(u);
        }
    }
}
