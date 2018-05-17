package com.applicationswp.datacontroller;

import com.applicationswp.data.Task;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Aufgaben.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Tasks extends BasicEntryList<Task> {

    public void load(long userID){
        Query q = getEntityManager().createQuery("select u from "+getTableName()+" u where u.userID="+userID);
        List<Task> result = q.getResultList();
        for(Task u:result){
            add(u);
        }
    }

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Task> getEntityClass() {
        return Task.class;
    }

}
