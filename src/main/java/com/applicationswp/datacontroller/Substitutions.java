package com.applicationswp.datacontroller;

import com.applicationswp.data.Substitution;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Vertretungen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Substitutions extends BasicEntryList<Substitution> {

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Substitution> getEntityClass() {
        return Substitution.class;
    }

}
