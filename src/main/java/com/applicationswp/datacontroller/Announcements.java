package com.applicationswp.datacontroller;

import com.applicationswp.data.Announcement;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Ankuendigungen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Announcements extends BasicEntryList<Announcement> {

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Announcement> getEntityClass() {
        return Announcement.class;
    }
}
