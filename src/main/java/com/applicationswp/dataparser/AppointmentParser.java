package com.applicationswp.dataparser;

import com.applicationswp.data.Appointment;
import com.applicationswp.data.AppointmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexwerff on 22/11/2016.
 */
public final class AppointmentParser {

    private AppointmentParser(){

    }

    public static List<AppointmentType> getDefaultAppointmentTypes(){
        List<AppointmentType> appointmentTypes = new ArrayList<>();
        AppointmentType timetable = new AppointmentType();
        timetable.setName("Stundenplan");
        appointmentTypes.add(timetable);

        AppointmentType exam = new AppointmentType();
        exam.setName("Klausur");
        appointmentTypes.add(exam);

        AppointmentType appointment = new AppointmentType();
        appointment.setName("Termin");
        appointmentTypes.add(appointment);

        AppointmentType event = new AppointmentType();
        event.setName("Veranstaltung");
        appointmentTypes.add(event);

        return appointmentTypes;
    }

    public static String getAppointmentsJSON(List<Appointment> appointmentList){
        String result = "[";
        for(Appointment appointment:appointmentList){
            result += getAppointmentJSON(appointment);
            if(appointmentList.indexOf(appointment) != (appointmentList.size()-1))
                result+=",";
        }
        result += "]";
        return result;
    }

    public static String getAppointmentJSON(Appointment appointment){
        String start = TimeParser.timestampToTimestring(appointment.getBeginTime());
        String end = TimeParser.timestampToTimestring(appointment.getEndTime());
        return String.format("{title:'%s',start:'%s',end:'%s'}",appointment.getTitle(),start,end);
        /*
        return "[\n" +
                "        {\n" +
                "            title  : 'event1',\n" +
                "            start  : '2016-11-22'\n" +
                "        },\n" +
                "        {\n" +
                "            title  : 'event2',\n" +
                "            start  : '2016-11-23',\n" +
                "            end    : '2016-1-24'\n" +
                "        },\n" +
                "        {\n" +
                "            title  : 'event3',\n" +
                "            start  : '2010-01-09T12:30:00',\n" +
                "            allDay : false // will make the time show\n" +
                "        }\n" +
                "    ]";*/
    }
}
