package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Courses;
import com.applicationswp.datacontroller.Tasks;
import com.applicationswp.dataparser.TimeParser;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Aufgaben-Seite und verwaltet diese.
 * Die Funktionen der Aufgaben-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Aufgaben-Seite.
 */
@ManagedBean(eager = true)
@ViewScoped
public class TaskController{
    private List<Task> tasks;
    private List<Course> courses;
    private String searchText;

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    public TaskController(){
        tasks = new ArrayList<>();
        courses = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Aufgaben und die Kurse
     * Else muss noch dazu
     */
    @PostConstruct
    public void init(){
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_VIEW)) {
            Tasks tasksDB = mainApplication.getSessionCacheController().getTask();
            Courses coursesDB = ApplicationCacheController.getInstance().getCourses();

            for(Task task:tasksDB){
                this.tasks.add(task);
            }
            for(Course course:coursesDB){
                this.courses.add(course);
            }


        }
    }

    /**
     * Gibt zurueck ob der aktuelle Benutzer eine Aufgabe hinzufuegen kann
     * @return true der aktuelle Benutzer eine Aufgabe hinzufuegen kann false wenn nicht
     */
    public boolean canAdd(){
        return mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_ADD);
    }

    /**
     * Gibt zurueck ob der aktuelle Benutzer eine Aufgabe loeschen kann
     * @return der aktuelle Benutzer eine Aufgabe loeschen kann
     */
    public boolean canDelete(){
        return mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_DELETE);
    }

    /**
     * Getter fuer alle erledigten Aufgaben
     * @return alle erledigten Aufgaben
     */
    public List<Task> getSolvedTasks(){
        List<Task> solvedTasks = new ArrayList();
        for (Task t: tasks){
            if(t.getStatus() == TaskStatus.SOLVED && fitsSearchText(t)){
                solvedTasks.add(t);
            }
        }
        return solvedTasks;
    }

    /**
     * Getter fuer alle offene Aufgaben
     * @return alle offene Aufgaben
     */
    public List<Task> getOpenTasks(){
        List<Task> openTasks = new ArrayList();
        for (Task t: tasks){
            if(t.getStatus() == TaskStatus.OPEN && fitsSearchText(t)){
                openTasks.add(t);
            }
        }
        return openTasks;
    }


    /**
     * Filterfunktion fuer die Suche
     * @param task der zu filternde Aufgabe
     * @return true wenn die Aufgabe der Suche entspricht und fals wenn nicht
     */
    private boolean fitsSearchText(Task task){
        if(searchText == null || searchText.equals("")){
            return true;
        }
        String searchTextLower = searchText.toLowerCase();
        return task.getTitle().toLowerCase().contains(searchTextLower)
                || task.getDescription().toLowerCase().contains(searchTextLower);
    }


    /**
     * OnSearch Handler
     * @param searchText der neue Suchtext
     */
    public void onSearch(String searchText){
        this.searchText = searchText;
    }


    /**
     * Getter fuer einen Kurs fuer eine ID
     * @param id die ID eines Kurses
     * @return einen Kurs fuer die gewaehlte ID
     */
    public Course getCourseByID(int id){
        for(Course course:courses){
            if(course.getID() == id)
                return course;
        }
        Course courseUndefined = new Course();
        courseUndefined.setID(-1);
        courseUndefined.setName("Kein Kurs");
        return courseUndefined;
    }

    /**
     * aendert den Status einer Aufgabe (OPEN zu SOLVED und SOLVED zu OPEN
     * @param task die Aufgabe dessen Status geaendert werden soll
     */
    public void changeTaskState(Task task){
        task.setStatus(task.getStatus() == TaskStatus.OPEN ? TaskStatus.SOLVED : TaskStatus.OPEN);
        mainApplication.getSessionCacheController().getTask().updateEntry(task);
    }


    /**
     * Fuegt eine Aufgabe hinzu
     * @param title Titel der Aufgabe
     * @param description Beschreibung der Aufgabe
     * @param dueTime bis wann die Aufgabe erledigt werden soll (Timestring)
     * @param course der zugehoerige Kurs (optional sonst null)
     */
    public void addNewTask(String title,String description,String dueTime,String course) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_ADD)) {
            throw new AuthenticationException(UserPermissions.Tasks.PERMISSION_TASK_ADD);
        }
        Task newTask = new Task();
        newTask.setStatus(TaskStatus.OPEN);
        newTask.setTitle(title);
        newTask.setDescription(description);
        try {
            newTask.setDueTime(TimeParser.timeStringToTimestamp(dueTime));
        } catch (Exception expected) {
            newTask.setDueTime(System.currentTimeMillis());
        }
        //über main application holen wir die Daten des aktuellen benutzers
        newTask.setUserID(mainApplication.getAuthenticationController().getMember().getID());
        newTask.setCourseID(Integer.parseInt(course)); //course als String übergeben und wird hier zum int geparsed
        tasks.add(newTask);
        mainApplication.getSessionCacheController().getTask().addEntry(newTask);

    }


    /**
     * Getter fuer den Taskstate
     * @param task der Task fuer den der State verlangt wird
     * @return den State fuer den gegeben Task
     */
    public String getTaskState(Task task){
        if(task.getStatus() == TaskStatus.OPEN){return "fa-circle";}
        else {return "fa-check-circle";}
    }



    /**
     * Loescht eine Aufgabe
     * @param task die zu loeschende Aufgabe
     */
    public void deleteTask(Task task) throws AuthenticationException {
        if (!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Tasks.PERMISSION_TASK_DELETE)) {
            throw new AuthenticationException(UserPermissions.Tasks.PERMISSION_TASK_DELETE);
        }
        tasks.remove(task);
        mainApplication.getSessionCacheController().getTask().removeEntry(task);
    }

    /**
     * Getter fuer alle Kurse
     * @return alle Kurse
     */
    public List<Course> getCourses(){
        return courses;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
}
