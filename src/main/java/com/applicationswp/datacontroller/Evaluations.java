package com.applicationswp.datacontroller;

import com.applicationswp.data.Evaluation;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Selbsteinschaetzungen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Evaluations extends BasicEntryList<Evaluation>{
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Evaluation> getEntityClass() {
        return Evaluation.class;
    }
}
