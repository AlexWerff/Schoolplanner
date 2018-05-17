package com.applicationswp.datacontroller;

import com.applicationswp.data.Appointment;
import com.applicationswp.data.TimeTable;

import javax.persistence.Entity;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by alexwerff on 20.02.17.
 */
public class Timetables  extends BasicEntryList<TimeTable>{
    @Override
    protected Class<TimeTable> getEntityClass() {
        return TimeTable.class;
    }

    public void load(long userID){
        Query q = getEntityManager().createQuery("select u from TimeTable u where u.userID="+userID);
        List<TimeTable> result = q.getResultList();
        for(TimeTable u:result){
            add(u);
        }
    }

}
