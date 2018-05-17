package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Courses;
import com.applicationswp.datacontroller.Files;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse ist der Controller fuer die Kurse-Seite und verwaltet diese.
 * Die Funktionen der Kurse-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Kurse-Seite.
 */
public class CoursesController {
    private List<Course> allCourses;
    private List<Course> ownCourses;
    private boolean showOwn;
    private String searchText;


    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    public CoursesController(){
        allCourses = new ArrayList<>();
        ownCourses = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Kurse und Dateien
     */
    @PostConstruct
    public void init(){
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Course.PERMISSION_COURSE_VIEW)) {
            Courses coursesDB = ApplicationCacheController.getInstance().getCourses();

            long id = mainApplication.getAuthenticationController().getMember().getID();
            if (id != -1) {
                for (Course course : coursesDB) {
                    for (Member member : course.getMembers()) {
                        if (member.getID() == id) {
                            ownCourses.add(course);
                        }
                    }
                    allCourses.add(course);
                }
            }
        }

    }

    /**
     * Getter fuer die Kurse
     * @return die Kurse
     */
    public List<Course> getCourses() {
        List<Course> courses = showOwn ? ownCourses : allCourses;
        List<Course> filtered = new ArrayList<>();
        for(Course course:courses){
            if(fitsSearchText(course)){
                filtered.add(course);
            }
        }
        return filtered;
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
     * @param course der zu filternde Kurse
     * @return true wenn Kurs der Suche entspricht und fals wenn nicht
     */
    private boolean fitsSearchText(Course course){
        if(searchText == null || searchText.equals("")){
            return true;
        }
        if(course.getName() == null || course.getName().equals("")){
            return false;
        }
        String searchTextLower = searchText.toLowerCase();
        String ownerName = getCourseOwner(course).toNameString();
        return course.getName().toLowerCase().contains(searchTextLower)
                || course.getDescription().toLowerCase().contains(searchTextLower)
                || ownerName.toLowerCase().contains(searchTextLower);
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }


    /**
     * Fuegt einen neuen Kurs hinzu
     * @param title der Titel des Kurses
     * @param description die Beschreibung des Kurses
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public void addNewCourse(String title,String description) throws AuthenticationException{
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Course.PERMISSION_COURSE_ADD))
            throw new AuthenticationException(UserPermissions.Course.PERMISSION_COURSE_ADD);
        Course course = new Course();
        course.setName(title);
        course.setDescription(description);
        course.setCreatorID((int) mainApplication.getAuthenticationController().getMember().getID());
        ApplicationCacheController.getInstance().getCourses().addEntry(course);
        mainApplication.getAuthenticationController().getMember().getCourses().add(course);
        course.getMembers().add(mainApplication.getAuthenticationController().getMember());
        ApplicationCacheController.getInstance().getMembers().updateEntry(mainApplication.getAuthenticationController().getMember());

        allCourses.add(course);
        ownCourses.add(course);


        /*
        availableMembers.updateEntry(m);
                    this.availableMembers.updateEntry(m);
                    this.courses.updateEntry(this.course);
         */
    }

    /**
     * Loescht einen Kurs
     * @param course der zu loeschende Kurs
     * @throws AuthenticationException wenn der Benutzer nicht authentifiziert ist
     */
    public void deleteCourse(Course course) throws AuthenticationException{
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Course.PERMISSION_COURSE_DELETE))
            throw new AuthenticationException(UserPermissions.Course.PERMISSION_COURSE_DELETE);
        ApplicationCacheController.getInstance().getCourses().removeEntry(course);
        allCourses.remove(course);
        ownCourses.remove(course);
    }

    /**
     * Gibt zurueck ob nur die eigenen Kurse angezeigt werden sollen
     * @return true wenn nur eigene Kurse angezeigt werden sollen und false wenn nicht
     */
    public boolean isShowOwn() {
        return showOwn;
    }

    /**
     *  Event-Handler fuer onShowChange Switch
     */
    public void onShowOwnChanged() {
        this.showOwn = !showOwn;
    }

    /**
     * Getter fuer den Besitzer des Kurses
     * @param course der zu untersuchende Kurs
     * @return den Besitzer des Kurses
     */
    public Member getCourseOwner(Course course){
        for(Member member: course.getMembers()){
            if(member.getID() == course.getCreatorID()){
                return member;
            }
        }
        Member unknown = new Member();
        unknown.setFirstname("Unknown");
        return unknown;
    }
}
