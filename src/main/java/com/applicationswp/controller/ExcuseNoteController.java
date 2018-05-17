package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Courses;
import com.applicationswp.datacontroller.ExcuseNotes;
import com.applicationswp.upload.UploadException;
import com.applicationswp.upload.UploadManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Fehlzeiten-Seite und verwaltet diese.
 * Die Funktionen der Fehlzeiten-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Fehlzeiten-Seite.
 */
@ManagedBean(eager = true)
@ViewScoped
public class ExcuseNoteController{
    private List<ExcuseNote> excuseNotes;
    private List<Course> courses;

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    private Part uploadFile;
    private String currentUploadUrl;
    private ExcuseNote currentExcuseNote;

    /**
     * Konstruktor
     */
    public ExcuseNoteController(){
        excuseNotes = new ExcuseNotes();
        courses = new Courses();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die die Entschuldigungen und Kurse
     */
    @PostConstruct
    public void init(){
        Courses coursesDB = ApplicationCacheController.getInstance().getCourses();
        ExcuseNotes excuseNotesDB = mainApplication.getSessionCacheController().getExcuseNotes();

        for(Course course:coursesDB){
            courses.add(course);
        }
        for(ExcuseNote excuseNote:excuseNotesDB){
            excuseNotes.add(excuseNote);
        }
    }

    /**
     * Getter fuer alle Fehlzeiten des Benutzers die entschuldigt sind
     * @return alle Fehlzeiten des Benutzers die entschuldigt sind
     */
    public List<ExcuseNote> getExcusedNotes(){
        List<ExcuseNote> excusedNotes = new ArrayList<>();
        for (ExcuseNote e: excuseNotes){
            if(e.getStatus() == ExcuseStatus.EXCUSED){
                excusedNotes.add(e);
            }
        }
        return excusedNotes;
    }

    /**
     * Getter fuer alle Fehlzeiten des Benutzers die nicht entschuldigt sind
     * @return alle Fehlzeiten des Benutzers die nicht entschuldigt sind
     */
    public List<ExcuseNote> getNonExcusedNotes(){
        List<ExcuseNote> nonExcusedNotes = new ArrayList<>();
        for (ExcuseNote e: excuseNotes){
            if(e.getStatus() == ExcuseStatus.OPEN){
                nonExcusedNotes.add(e);
            }
        }
        return nonExcusedNotes;
    }


    /**
     * Liefert einen Kurs fuer eine Kurs-ID
     * @param id die ID des Kurses
     * @return der Kurs fuer die ID
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
     * aendert den Fehlzeitenstatus zum Gegenteil (Entschuldigt => nicht Entschuldigt)
     * @param excuseNote die Fehlzeit deren Status geaendert werden soll
     */
    public void changeExcuseNoteState(ExcuseNote excuseNote){
        excuseNote.setStatus(excuseNote.getStatus() == ExcuseStatus.OPEN ? ExcuseStatus.EXCUSED : ExcuseStatus.OPEN);
        mainApplication.getSessionCacheController().getExcuseNotes().updateEntry(excuseNote);
    }


    /**
     * Fuegt eine neue Fehlzeit hinzu
     * @param title Titel der Fehlzeit
     * @param description Beschreibung der Fehlzeit
     * @param excuseDateFrom von wann (Timestring)
     * @param excuseDateTo bis wann (Timestring)
     * @param course Kurs in dem gefehlt wurde (optional sonst null)
     */
    public void addNewExcuseNote(String title,String description,String excuseDateFrom,String excuseDateTo,String course){
        ExcuseNote newExcuseNote = new ExcuseNote();
        newExcuseNote.setStatus(ExcuseStatus.OPEN);
        newExcuseNote.setTitle(title);
        newExcuseNote.setDescription(description);
        try {
            newExcuseNote.setExcuseDateFrom(Integer.parseInt(excuseDateFrom));
        }
        catch (Exception expected){
            newExcuseNote.setExcuseDateFrom((int) System.currentTimeMillis());
        }
        try {
            newExcuseNote.setExcuseDateTo(Integer.parseInt(excuseDateTo));
        }
        catch (Exception expected){
            newExcuseNote.setExcuseDateTo((int) System.currentTimeMillis());
        }
        newExcuseNote.setUserID(1);
        newExcuseNote.setCourseID(Integer.parseInt(course));
        mainApplication.getSessionCacheController().getExcuseNotes().addEntry(newExcuseNote);
        excuseNotes.add(newExcuseNote);
    }

    /**
     * Loescht eine Fehlzeit
     * @param excuseNote die zu loeschende Fehlzeit
     */
    public void deleteExcuseNote(ExcuseNote excuseNote){
        mainApplication.getSessionCacheController().getExcuseNotes().removeEntry(excuseNote);
        excuseNotes.remove(excuseNote);
    }

    /**
     * Getter fuer alle Fehlzeiten
     * @return alle Fehlzeiten
     */
    public List<ExcuseNote> getExcuseNotes() {
        return excuseNotes;
    }

    /**
     * Gibt den das Statuslogo fuer eine Fehlzeit
     * @param excuseNote die Fehlzeit
     * @return die Logobezeichnung als FontAwesome-Icon (fa-circle oder fa-plus-circle)
     */
    public String getExcuseNoteState(ExcuseNote excuseNote){
        if(excuseNote.getStatus() == ExcuseStatus.OPEN){return "fa-circle";}
        else {return "fa-plus-circle";}
    }

    /**
     * Getter fuer alle Kurse des Benutzers
     * @return alle Kurse des Benutzers
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }



    /**
     * Event-Handler welcher die aktuelle Url der hochgeladenen Datei anzeigt
     */
    public void uploadFileChanged(){
        if(uploadFile != null && currentExcuseNote != null) {
            currentUploadUrl = UploadManager.getFullFilename(uploadFile);

            File file = new File();
            file.setName(currentUploadUrl);
            String uploadUrl = currentUploadUrl;
            try {
                uploadUrl = UploadManager.uploadFile(uploadFile, UploadManager.getFilename(uploadFile));
            } catch (UploadException e) {
                e.printStackTrace();
            }

            file.setPath(uploadUrl);

            mainApplication.getSessionCacheController().getFiles().addEntry(file);
            currentExcuseNote.setExcuseFile(file);
            mainApplication.getSessionCacheController().getExcuseNotes().updateEntry(currentExcuseNote);
            uploadFile = null;
        }
    }

    /**
     * Fuegt eine Datei dem Kurs hinzu
     * @param filename der Name der Datei
     * @param fileUrl die Url der Datei
     */
    public void addFile(String filename,String fileUrl){
        mainApplication.getSessionCacheController().getFiles().save();
        ApplicationCacheController.getInstance().getCourses().save();
        uploadFile = null;
        currentUploadUrl = "";
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
     * Setter fuer die aktuelle Entschuldigung
     * @param excuseNote die aktuelle Entschuldigung
     */
    public void setCurrentExcuseNote(ExcuseNote excuseNote){
        this.currentExcuseNote = excuseNote;
    }
}
