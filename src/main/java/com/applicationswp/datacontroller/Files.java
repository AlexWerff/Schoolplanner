package com.applicationswp.datacontroller;

import com.applicationswp.data.File;
import com.applicationswp.datacontroller.BasicEntryList;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Dateien.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Files extends BasicEntryList<File> {
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<File> getEntityClass() {
        return File.class;
    }

}
