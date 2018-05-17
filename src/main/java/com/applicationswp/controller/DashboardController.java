package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.*;
import com.applicationswp.dataparser.TimeParser;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Dashboard-Seite und verwaltet diese.
 * Die Funktionen der Dashboard-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Dashboard-Seite.
 */
@ManagedBean
@ViewScoped
public class DashboardController {

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;
    private List<Task> tasks;
    private List<Appointment> appointments;
    private List<Substitution> substitutions;
    private List<Message> messages;

    /**
     * Konstruktor
     */
    public DashboardController(){
        tasks = new ArrayList<>();
        appointments = new ArrayList<>();
        substitutions = new ArrayList<>();
        messages = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Termine, Nachrichten und Vertretungen
     */
    @PostConstruct
    public void init(){
        long memberID = -1;
        try {
            memberID = mainApplication.getAuthenticationController().getMember().getID();
        }
        catch (Exception expected){

        }
        Tasks tasksDB= mainApplication.getSessionCacheController().getTask();
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_VIEW)) {
            for(Task task:tasksDB){
                this.tasks.add(task);
            }
        }

        long timeTableID = ApplicationCacheController.getInstance().getAppointmentTypes().getTimetableID();

        Appointments appointmentsDB = mainApplication.getSessionCacheController().getAppointments();
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW)) {
            for(Appointment appointment:appointmentsDB){
                if(appointment.getTitle() != null
                        && !appointment.getTitle().equals("") && timeTableID != appointment.getAppointmentTypeID() ){
                    this.appointments.add(appointment);
                }
            }
        }
        Substitutions substitutionsDB = mainApplication.getSessionCacheController().getSubstitutions();
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_VIEW)) {
            for(Substitution sub:substitutionsDB){
                substitutions.add(sub);
            }
        }
        Messages messagesDB = mainApplication.getSessionCacheController().getMessages();
        this.messages = new ArrayList<>();
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Messages.PERMISSION_MESSAGE_VIEW)) {
            this.messages.addAll(messagesDB);
            for(int i=this.messages.size()-1;i>=0;i--){
                if(messages.get(i).getRecieverID() != memberID){
                    messages.remove(i);
                }
            }
            this.messages.sort((o1, o2) -> {
                if(o1.getTimestamp() > o2.getTimestamp()){
                    return -1;
                }
                else if(o1.getTimestamp() < o2.getTimestamp()){
                    return 1;
                }
                return 0;
            });
        }
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Getter fuer alle Aufgaben des Benutzers
     * @return alle Aufgaben des Benutzers
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Getter fuer alle Termine des Benutzers
     * @return alle Termine des Benutzers
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Getter fuer alle Vertretungen des Benutzers
     * @return alle Vertretungen des Benutzers
     */
    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    /**
     * Getter fuer alle Nachrichten des Benutzers
     * @return alle Nachrichten des Benutzers
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * Gibt fuer eine BenutzerID einen Benutzer zurueck
     * @param userID die BenutzerID
     * @return den Benutzer fuer die BenutzerID
     */
    public Member getMemberForID(long userID){
        for(Member member: ApplicationCacheController.getInstance().getMembers()){
            if(member.getID() == userID){
                return member;
            }
        }
        return new Member();
    }

    /**
     * Getter fuer alle offenen Aufgaben des Benutzers
     * @return alle offenen Aufgaben des Benutzers
     */
    public Tasks getOpenTasks(){
        Tasks openTasks = new Tasks();
        if(!tasks.isEmpty()){
            for (Task t: tasks){
                if(t.getStatus() == TaskStatus.OPEN){
                    openTasks.add(t);
                }
            }
        }
        return openTasks;
    }

    /**
     * Gibt das aktuelle Datum zurueck
     * @param query
     * @return das aktuelle Datum
     */
    public String getDate(String query){
        return TimeParser.getCurrentDateTime(query);
    }

}
