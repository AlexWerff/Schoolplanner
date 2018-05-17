package com.applicationswp.datacontroller;

import com.applicationswp.data.Course;
import com.applicationswp.data.Task;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Kursen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Courses extends BasicEntryList<Course> {
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Course> getEntityClass() {
        return Course.class;
    }

}
