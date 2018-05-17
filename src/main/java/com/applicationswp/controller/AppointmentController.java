package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Appointment;
import com.applicationswp.data.AppointmentType;
import com.applicationswp.data.Task;
import com.applicationswp.datacontroller.AppointmentTypes;
import com.applicationswp.datacontroller.Appointments;
import com.applicationswp.data.UserPermissions;
import com.applicationswp.dataparser.AppointmentParser;
import com.applicationswp.dataparser.TimeParser;
import com.applicationswp.security.MD5Generator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Kalender-Seite und verwaltet diese.
 * Die Funktionen der Kalender-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Kalender-Seite.
 */
@ManagedBean
@ViewScoped
public class AppointmentController {
    private List<Appointment> appointments;
    private List<AppointmentType> appointmentTypes;
    private String searchText;

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;
    private AuthenticationController authenticationController;
    private boolean quartal;
    private Appointment currentAppointment;

    /**
     * Konstructor
     */
    public AppointmentController(){
        appointmentTypes = new ArrayList<>();
        appointments = new ArrayList<>();
    }


    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Termine und Termintypen
     */
    @PostConstruct
    public void init() {
        authenticationController = mainApplication.getAuthenticationController();
        long memberID = -1;
        try {
            memberID = authenticationController.getMember().getID();
        }
        catch (Exception expected){

        }

        if(authenticationController.isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW)) {
            AppointmentTypes appointmentTypesDB = ApplicationCacheController.getInstance().getAppointmentTypes();
            long timetableID = appointmentTypesDB.getTimetableID();
            for (AppointmentType type : appointmentTypesDB) {
                this.appointmentTypes.add(type);

            }

            for(Appointment appointment:mainApplication.getSessionCacheController().getAppointments()){
                if(!appointment.getTitle().equals("") && appointment.getAppointmentTypeID() != timetableID){
                    this.appointments.add(appointment);
                }
            }
        }

    }

    /**
     * Fuegt einen Termin hinzu
     * @param appointment Der hinzuzufuegende Termin
     */
    public void addAppointment(Appointment appointment) throws AuthenticationException {
        if(!authenticationController.isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD))
            throw new AuthenticationException(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD);
        this.appointments.add(appointment);
        mainApplication.getSessionCacheController().getAppointments().addEntry(appointment);
    }

    /**
     * Loescht einen Termin
     * @param appointment der zu loeschende Termin
     */
    public void deleteAppointment(Appointment appointment) throws AuthenticationException {
        if(!this.mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_DELETE))
            throw new AuthenticationException(UserPermissions.Appointment.PERMISSION_APPOINTMENT_DELETE);
        if(appointment != null) {
            this.appointments.remove(appointment);
            mainApplication.getSessionCacheController().getAppointments().removeEntry(appointment);
        }
    }

    /**
     * Liefert einen Termintypen fuer eine TermintypID
     * @param id die TermintypID
     * @return den Termintypen
     */
    public AppointmentType getAppointmentType(int id){
        for(AppointmentType type:appointmentTypes){
            if(type.getID() == id)
                return type;
        }
        AppointmentType undefined = new AppointmentType();
        undefined.setID(-1);
        undefined.setName("Kein Typ");
        return undefined;
    }

    /**
     * Liefert das Ergebnis ob der aktuelle Benutzer einen Termin hinzufuegen darf
     * @return true wenn erlaubt und false wenn nicht
     */
    public boolean canAddAppointment(){
        return authenticationController.isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD);
    }

    /**
     * Liefert das Ergebnis ob der aktuelle Benutzer einen Termin loeschen darf
     * @return true wenn erlaubt und false wenn nicht
     */
    public boolean canDeleteAppointment(Appointment appointment){
        return authenticationController.isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_DELETE);
    }


    /**
     * Getter fuer Termine
     * @return alle Termine des aktuellen Benutzers
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public List<Appointment> getAppointments() throws AuthenticationException {
        return appointments;
    }


    /**
     * Fuegt einen neuen Termin hinzu
     * @param title Titel des Termins
     * @param description Beschreibung des termins
     * @param from von wann (Timestring)
     * @param to bis wann (Timestring)
     * @param type den Termintypen
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public void addNewAppointment(String title,String description,String from,String to,String type) throws AuthenticationException{
        if(!authenticationController.isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD))
            throw new AuthenticationException(UserPermissions.Appointment.PERMISSION_APPOINTMENT_ADD);
        Appointment appointment = new Appointment();
        appointment.setCreatorID((int) mainApplication.getAuthenticationController().getMember().getID());
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setBeginTime(TimeParser.timeStringToTimestamp(from));
        appointment.setEndTime(TimeParser.timeStringToTimestamp(to));
        appointment.setAppointmentTypeID(Integer.parseInt(type));
        addAppointment(appointment);
    }

    /**
     * Getter fuer die Termintypen
     * @return
     */
    public List<AppointmentType> getAppointmentTypes() {
        return appointmentTypes;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }


    /**
     * Getter fuer alle Termine in JSON Form (fuer die Kalendarkomponente)
     * @return alle Termine in JSON Form
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public String getCalendarJSON() throws AuthenticationException{
        return AppointmentParser.getAppointmentsJSON(getAppointments());
    }

    /**
     * Switch zu der Quartalsansicht
     */
    public void setToQuartal(){
        this.quartal = !this.quartal;
    }

    /**
     * Getter fuer Quartalsansicht (ob Quartalsansicht aktiv)
     * @return true wenn Quartalsansicht false wenn nicht
     */
    public boolean isQuartal() {
        return quartal;
    }

    /**
     * Getter fuer alle gesuchten Termine
     * @return alle gesuchten Termine
     */
    public List<Appointment> getSearchAppointments(){
        List<Appointment> apps = new ArrayList<>();
        for(Appointment appointment:this.appointments){
            if(fitsSearchText(appointment)){
                apps.add(appointment);
            }
        }
        return apps;
    }

    /**
     * Getter fuer die aktuellen 3 Monate
     * @param number nummer der aktuellen 3 monate
     * @return den aktuellen Zeit-String
     */
    public String getDefaultDate(int number){
        if(number == 2){
            return TimeParser.timestampToTimestring((System.currentTimeMillis()/1000));
        }
        else if(number == 1){
            long time = (System.currentTimeMillis()/1000) - 2592000;
            return TimeParser.timestampToTimestring(time);
        }
        else{
            long time = (System.currentTimeMillis()/1000) + 2592000;
            return TimeParser.timestampToTimestring(time);
        }
    }

    /**
     * OnSearch Handler
     * @param searchText der neue Suchtext
     */
    public void onSearch(String searchText){
        this.searchText = searchText;
    }


    /**
     * Filterfunktion fuer die Suche
     * @param appointment der zu filternde Termin
     * @return true wenn der Termin der Suche entspricht und fals wenn nicht
     */
    private boolean fitsSearchText(Appointment appointment){
        if(searchText == null || searchText.equals("")){
            return true;
        }
        String searchTextLower = searchText.toLowerCase();
        return appointment.getTitle().toLowerCase().contains(searchTextLower)
                || appointment.getDescription().toLowerCase().contains(searchTextLower);
    }

    /**
     * Getter fuer den aktuellen Suchtext
     * @return den aktuellen Suchtext
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Gibt zurueck ob die Suchergebnisse angezeigt werden sollen oder nicht
     * @return true wenn die Suchergebnisse angezeigt werden sollen und nein wenn nicht
     */
    public boolean showSearchResults(){
        return searchText != null && !searchText.equals("");
    }

    public String getUpdateSearchID(){
        return (searchText== null || searchText.equals("")) ? "tasks" : "searched events";
    }

    /**
     * Getter fuer den CSS Style des Event-Tags
     * @return den CSS Style des Event-Tags
     */
    public String getEventsStyle(){
        return searchText == null || searchText.equals("") ? "":"display:none";
    }

    /**
     * Getter fuer den CSS Style des Suchergebniss-Tags
     * @return den CSS Style des Suchergebniss-Tags
     */
    public String getSearchStyle(){
        return searchText != null && !searchText.equals("") ? "":"display:none";
    }

    public Appointment getCurrentAppointment() {
        return currentAppointment;
    }

    public void setCurrentAppointment(Appointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
