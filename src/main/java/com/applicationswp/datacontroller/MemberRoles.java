package com.applicationswp.datacontroller;

import com.applicationswp.data.MemberRole;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Benutzerrollen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class MemberRoles extends BasicEntryList<MemberRole> {
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<MemberRole> getEntityClass() {
        return MemberRole.class;
    }

}
