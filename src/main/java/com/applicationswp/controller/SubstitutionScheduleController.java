package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Appointments;
import com.applicationswp.datacontroller.Courses;
import com.applicationswp.datacontroller.Substitutions;
import com.applicationswp.dataparser.CSVParser;
import com.applicationswp.dataparser.IOUtils;
import com.applicationswp.upload.UploadException;
import com.applicationswp.upload.UploadManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Diese Klasse ist der Controller fuer die Vertretungsplan-Seite und verwaltet diese.
 * Die Funktionen der Vertretungsplan-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Vertretungplan-Seite.
 */
public class SubstitutionScheduleController {
    private List<Substitution> substitutions;
    private List<Course> courses;
    private List<Appointment> appointments;
    private Substitution currentSub;

    private Part uploadFile;
    private String currentUploadUrl;
    private String searchText;


    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    public SubstitutionScheduleController(){
        this.substitutions = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.currentSub = new Substitution();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Vertretungen, die Kurse und die Termine
     */
    @PostConstruct
    public void init(){

        Substitutions substitutionsDB = mainApplication.getSessionCacheController().getSubstitutions();
        Courses coursesDB = ApplicationCacheController.getInstance().getCourses();
        Appointments appointmentsDB = mainApplication.getSessionCacheController().getAppointments();

        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW)) {
            for (Appointment appointment : appointmentsDB) {
                if (appointment.getTitle() != null && !appointment.getTitle().equals("")) {
                    this.appointments.add(appointment);
                }
            }
        }

        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Course.PERMISSION_COURSE_VIEW)) {
            for (Course course : coursesDB) {
                this.courses.add(course);
            }
        }

        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_VIEW)) {
            for (Substitution substitution : substitutionsDB) {
                this.substitutions.add(substitution);
            }
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
     *
     */
    public void editCurrentSub(){
        if(currentSub != null){
            mainApplication.getSessionCacheController().getSubstitutions().updateEntry(currentSub);
        }
    }
    /**
     * Filterfunktion fuer die Suche
     * @param substitution der zu filternde Vertretung
     * @return true wenn Vertretung der Suche entspricht und fals wenn nicht
     */
    private boolean fitsSearchText(Substitution substitution){
        if(searchText == null || searchText.equals("")){
            return true;
        }
        if(substitution.getTitle() == null || substitution.getTitle().equals("")){
            return false;
        }
        if(substitution.getDescription() == null || substitution.getDescription().equals("")){
            return false;
        }
        String searchTextLower = searchText.toLowerCase();
        return substitution.getTitle().toLowerCase().contains(searchTextLower)
                || substitution.getDescription().toLowerCase().contains(searchTextLower);
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
     * Fuegt die akutell zu erstellende Vertretung den Vertretungen hinzu
     */
    public void addCurrentSubstitution(){
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_ADD)) {
            mainApplication.getSessionCacheController().getSubstitutions().addEntry(currentSub);
            this.substitutions.add(this.currentSub);
            this.currentSub = null;
        }
    }


    /**
     * Erstellt eine neue aktuell zu erstellende Vetretung
     */
    public void createCurrentSub(){
        this.currentSub = new Substitution();
    }

    /**
     * Loescht eine Vertretung
     * @param sub die zu loeschende Vertretung
     */
    public void deleteSub(Substitution sub) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_DELETE)) {
            throw new AuthenticationException(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_DELETE);
        }
        mainApplication.getSessionCacheController().getSubstitutions().removeEntry(sub);
        this.substitutions.remove(sub);
    }

    /**
     * Getter fuer alle Vertretungen des Benutzers
     * @return alle Vertretungen des Benutzers
     */
    public List<Substitution> getSubstitutions() {
        List<Substitution> subs = new ArrayList<>();
        for(Substitution sub:substitutions){
            if(fitsSearchText(sub)){
                subs.add(sub);
            }
        }
        return subs;
    }


    /**
     * Getter fuer alle Kurse des Benutzers
     * @return alle Kurse des Benutzers
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Getter fuer alle Termine des Benutzers
     * @return alle Termine des Benutzers
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Getter fuer die aktuell zu erstellende Vertretung
     * @return die aktuell zu erstellende Vertretung
     */
    public Substitution getCurrentSub() {
        return currentSub;
    }

    /**
     * Setter fuer die aktuell zu erstellende Vertretung
     * @param currentSub die aktuell zu erstellende Vertretung
     */
    public void setCurrentSub(Substitution currentSub) {
        this.currentSub = currentSub;
    }



    /**
     * Event-Handler welcher die aktuelle Url der hochgeladenen Datei anzeigt
     */
    public void uploadFileChanged(){
        if(uploadFile != null)
            currentUploadUrl = UploadManager.getFullFilename(uploadFile);
    }



    /**
     * Fuegt eine Datei dem Kurs hinzu
     * @param filename der Name der Datei
     * @param fileUrl die Url der Datei
     */
    public void addFile(String filename,String fileUrl){
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_ADD)) {
            File file = new File();
            file.setName(filename);
            String uploadUrl = fileUrl;


            if (uploadFile != null) {
                try {
                    InputStream inputStream = uploadFile.getInputStream();
                    String result = IOUtils.getStringFromInputStream(inputStream);
                    List<Substitution> substitutionsCSV = CSVParser.getSubsitionsFromCSV(result);
                    for (Substitution sub : substitutionsCSV) {
                        mainApplication.getSessionCacheController().getSubstitutions().addEntry(sub);
                        this.substitutions.add(sub);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            uploadFile = null;
            currentUploadUrl = "";
        }
    }

    /**
     * Wird verwendet fuer den Dateiupload
     * @return
     */
    public Part getUploadFile() {
        return uploadFile;
    }

    /**
     * Wird verwendet fuer den Dateiupload
     * @return
     */
    public void setUploadFile(Part uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * Wird verwendet fuer den Dateiupload
     * @return
     */
    public String getCurrentUploadUrl() {
        return currentUploadUrl;
    }

    /**
     * Wird verwendet fuer den Dateiupload
     * @return
     */
    public void setCurrentUploadUrl(String currentUploadUrl) {
        this.currentUploadUrl = currentUploadUrl;
    }

    /**
     * Getter fuer alle Vertretungen des Benutzers
     * @return alle Vertretungen des Benutzers
     */
    public List<SubstitutionDay> getSubsitionDays() {
        HashMap<String,List<Substitution>> map = new HashMap<>();

        for(Substitution substitution:getSubstitutions()){
            String key = new SimpleDateFormat("dd.MM.yyyy").format(new Date(substitution.getTimeFrom()));
            if(map.containsKey(key)){
                map.get(key).add(substitution);
            }
            else{
                List<Substitution> list = new ArrayList<>();
                list.add(substitution);
                map.put(key,list);
            }
        }

        List<SubstitutionDay> substitutionDays = new ArrayList<>();
        for(Map.Entry<String,List<Substitution>> entry: map.entrySet()){
            SubstitutionDay substitutionDay = new SubstitutionDay();
            substitutionDay.setDate(entry.getKey());
            for(Substitution sub:entry.getValue()){
                substitutionDay.getSubstitutions().add(sub);
            }
            substitutionDays.add(substitutionDay);
        }


        return substitutionDays;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Gibt zurueck ob der aktuelle Benutzer Vetretungen hinzufuegen kann
     * @return true wenn der aktuelle Benutzer Vertretungen hinzufuegen kann und false wenn nicht
     */
    public boolean addSubstitutionVisible(){
        return mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_ADD);
    }

    /**
     * Helferklasse um die Vertretungen nach Tagen zu sortieren
     */
    public class SubstitutionDay{
        private List<Substitution> substitutions;
        private String date;

        public SubstitutionDay(){
            substitutions = new ArrayList<>();
        }

        public List<Substitution> getSubstitutions() {
            return substitutions;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
