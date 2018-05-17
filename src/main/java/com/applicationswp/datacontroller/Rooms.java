package com.applicationswp.datacontroller;

import com.applicationswp.data.Room;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Raeumen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Rooms extends BasicEntryList<Room>{
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Room> getEntityClass() {
        return Room.class;
    }
}
