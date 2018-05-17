package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Appointment;
import com.applicationswp.data.AppointmentType;
import com.applicationswp.data.TimeTable;
import com.applicationswp.data.UserPermissions;
import com.applicationswp.datacontroller.AppointmentTypes;
import com.applicationswp.datacontroller.Appointments;
import com.applicationswp.datacontroller.Timetables;
import net.bootsfaces.render.E;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Stundenplan-Seite und verwaltet diese.
 * Die Funktionen der Stundenplan-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Stundenplan-Seite.
 */
@ManagedBean
@ViewScoped
public class TimetableController {
    private List<TimeTable> timeTables;
    private int currentTimetableIndex;
    private boolean edit;


    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    public TimetableController(){
        timeTables = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert den Stundenplan
     */
    @PostConstruct
    public void init(){
        this.currentTimetableIndex = mainApplication.getSessionCacheController().getLastTimetable();
        edit = true;

        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD)) {
            Timetables timeTablesDB = mainApplication.getSessionCacheController().getTimeTables();
            Appointments appointmentsDB = mainApplication.getSessionCacheController().getAppointments();

            if (timeTablesDB.size() == 0) {
                for (int i = 0; i < 2; i++) {
                    TimeTable timeTable = new TimeTable();
                    timeTable.setTitle("Stundenplan " + ((i % 2 == 0) ? "gerade" : "ungerade"));
                    timeTable.setUserID(mainApplication.getAuthenticationController().getMember().getID());
                    timeTablesDB.add(timeTable);
                    this.timeTables.add(timeTable);
                }


                for (TimeTable timeTable : this.timeTables) {
                    TimeTable data = TimeTable.getEmpty();
                    for (Appointment appointment : data.getAppointments()) {
                        appointment.setTimeTable(timeTable);
                        appointment.setAppointmentTypeID((int) ApplicationCacheController.getInstance().getAppointmentTypes().getTimetableID());
                        appointment.setCreatorID((int) mainApplication.getAuthenticationController().getMember().getID());
                        timeTable.getAppointments().add(appointment);
                        appointmentsDB.addEntry(appointment);
                    }
                    //timeTablesDB.updateEntry(timeTable);
                }
            }
            else{
                for(TimeTable timetable:timeTablesDB){
                    this.timeTables.add(timetable);
                }
            }
        }
    }

    /**
     * Wechselt den Editiermodus (Bearbeiten zu Lesen und Lesen zu Bearbeiten)
     */
    public void onEditChanged(){
        edit = !edit;
    }

    /**
     * Löscht den aktuellen Stundenplan
     */
    public void deleteCurrentTimetable(){
        TimeTable timeTableDelete = timeTables.get(currentTimetableIndex);
        for(int i =timeTableDelete.getAppointments().size()-1;i>=0;i--){
            Appointment appointment = timeTableDelete.getAppointments().get(i);
            mainApplication.getSessionCacheController().getAppointments().removeEntry(appointment);
            timeTableDelete.getAppointments().remove(i);
        }
        mainApplication.getSessionCacheController().getTimeTables().removeEntry(timeTableDelete);
        timeTables.remove(timeTableDelete);
        currentTimetableIndex = 0;
        mainApplication.getSessionCacheController().setLastTimetable(currentTimetableIndex);
    }

    /**
     * Fuegt einen neuen Stundenplan hinzu
     * @param timetableName der Name des neuen Stundeplans
     */
    public void addTimetable(String timetableName){
        TimeTable timeTable = new TimeTable();
        timeTable.setTitle(timetableName);
        timeTable.setUserID(mainApplication.getAuthenticationController().getMember().getID());
        mainApplication.getSessionCacheController().getTimeTables().add(timeTable);
        this.timeTables.add(timeTable);
        TimeTable data = TimeTable.getEmpty();
        for (Appointment appointment : data.getAppointments()) {
            appointment.setTimeTable(timeTable);
            appointment.setCreatorID((int) mainApplication.getAuthenticationController().getMember().getID());
            appointment.setAppointmentTypeID((int) ApplicationCacheController.getInstance().getAppointmentTypes().getTimetableID());
            timeTable.getAppointments().add(appointment);
            mainApplication.getSessionCacheController().getAppointments().addEntry(appointment);
        }
        //mainApplication.getSessionCacheController().getTimeTables().updateEntry(timeTable);
    }

    /**
     * Getter fuer alle Stundenplaene des Benutzers
     * @return alle Stundenplaene des Benutzers
     */
    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Getter fuer den Editiermodus
     * @return true wenn gerade editiert wird und false wenn nicht
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * Setter fuer den Editiermodus
     * @param edit true wenn editiert werden soll und false wenn nicht
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * Wechselt den aktuellen Stundenplan zum naechsten Stundenplan
     * Wenn am Ende angekommen dann wird wieder der Erste ausgewaehlt
     */
    public void nextTimetable(){
        if(this.currentTimetableIndex == this.getTimeTables().size()-1){
            this.currentTimetableIndex = 0;
        }
        else {
            this.currentTimetableIndex++;
        }
        mainApplication.getSessionCacheController().setLastTimetable(this.currentTimetableIndex);
    }

    /**
     * Wechselt den aktuellen Stundenplan zum vorherigen Stundenplan
     * Wenn am Anfang angekommen dann wird wieder der Letze ausgewaehlt
     */
    public void previousTimetable(){
        if(this.currentTimetableIndex == 0){
            this.currentTimetableIndex = this.getTimeTables().size()-1;
        }
        else {
            this.currentTimetableIndex--;
        }
        mainApplication.getSessionCacheController().setLastTimetable(this.currentTimetableIndex);
    }

    /**
     * Getter fuer den aktuellen Stundenplan
     * @return den aktuellen Stundenplan
     */
    public TimeTable getCurrentTimetable() throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW)){
            throw new AuthenticationException(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW);
        }
        try {
            return this.timeTables.get(currentTimetableIndex);
        }
        catch (IndexOutOfBoundsException ex){
            TimeTable timeTable = new TimeTable();
            timeTable.setTitle("Keine Stundenpläne");
            return timeTable;
        }
    }

    /**
     * Handler fuer die Aenderung eines Stundenplaneintrages
     * @param appointment der Stundenplaneintrag in Form eines Termins
     * @param value der neue Wert fuer den Stundenplaneintrag
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public void onEntryChanged(Appointment appointment,String value) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD)){
            throw new AuthenticationException(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD);
        }
        appointment.setTitle(value);
        appointment.setDescription(appointment.getTimeTable().getTitle());
        mainApplication.getSessionCacheController().getAppointments().updateEntry(appointment);
    }

    /**
     * Getter fuer den CSS-Style fuer die Editieransicht
     * @return den CSS-Style fuer die Editieransicht
     */
    public String getEditStyle(){
        return edit ? "":"display:none";
    }

    /**
     * Getter fuer den CSS-Style fuer die View Ansicht
     * @return den CSS-Style fuer die View Ansicht
     */
    public String getViewStyle(){
        return !edit ? "":"display:none";
    }

}
