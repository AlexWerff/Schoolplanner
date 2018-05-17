package com.applicationswp.datacontroller;

import com.applicationswp.data.AppointmentType;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Termin-Typen.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class AppointmentTypes extends BasicEntryList<AppointmentType> {
    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<AppointmentType> getEntityClass() {
        return AppointmentType.class;
    }



    public long getTimetableID(){
        for(AppointmentType type:this){
            if(type.getName().equals("Stundenplan")){
                return type.getID();
            }
        }
        return -1;
    }
}
