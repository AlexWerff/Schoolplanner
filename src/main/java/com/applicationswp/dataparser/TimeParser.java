package com.applicationswp.dataparser;

import net.bootsfaces.render.E;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Ein Zeiten/Datum Parser
 * Wandelt Timestamps etc zu Timestrings etc.
 */
public final class TimeParser {

    private TimeParser(){

    }

    /**
     * Konvertiert einen Timestring zu einem Timestamp
     * @param timestamp den zu konvertierenden Timestring
     * @return den konvertierten Timestring
     */
    public static String timestampToTimestring(long timestamp){
        TimeZone tz = TimeZone.getTimeZone("CET");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        Date date = new Date((Math.abs(timestamp)*1000));
        String nowAsISO = df.format(date);
        return nowAsISO;
    }



    /**
     * Konvertiert einen Timestamp zu einem Timestring
     * @param timestring den zu konvertierenden Timestring
     * @return den konvertierten Timestamp
     */
    public static long timeStringToTimestamp(String timestring){
        try{
            TimeZone tz = TimeZone.getTimeZone("CET");
            SimpleDateFormat dateFormat = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            dateFormat.setTimeZone(tz);
            Date parsedDate = dateFormat.parse(timestring);
            return (parsedDate.getTime()/1000);
        }catch(Exception e){//this generic but you can control another types of exception

        }
        return 0;
    }

    /**
     * Formatiert die aktuelle Zeit je nach Query (weekday oder day oder month etc)
     * @param query die Query (weekday oder day oder month)
     * @return das jeweils gewaehlte Datum (Wochentag oder Tag oder Monat etc)
     */
    public static String getCurrentDateTime(String query){
        DateFormat df;
        Date dateobj;
        switch(query){
            case "weekday":
                df = new SimpleDateFormat("EEEEEEEE");
                dateobj = new Date();
                return df.format(dateobj);
            case "day":
                df = new SimpleDateFormat("dd.");
                dateobj = new Date();
                return df.format(dateobj);
            case "month":
                df = new SimpleDateFormat("MMMMMMMM");
                dateobj = new Date();
                return df.format(dateobj);
            case "year":
                df = new SimpleDateFormat("yyyy");
                dateobj = new Date();
                return df.format(dateobj);
            case "hour":
                df = new SimpleDateFormat("hh");
                dateobj = new Date();
                return df.format(dateobj);
            case "minute":
                df = new SimpleDateFormat("mm");
                dateobj = new Date();
                return df.format(dateobj);
            case "second":
                df = new SimpleDateFormat("ss");
                dateobj = new Date();
                return df.format(dateobj);
            default:return "-";
        }
    }


    //20161108
    public static long dateStringToTimestamp(String dateString) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            date = df.parse(dateString.replace("\"",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String[] getDays(){
        return new String[]{
                "Montag",
                "Dienstag",
                "Mittwoch",
                "Donnerstag",
                "Freitag",
                "Samstag",
                "Sonntag"
        };
    }
}
