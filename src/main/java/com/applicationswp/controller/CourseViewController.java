package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.*;
import com.applicationswp.dataparser.PhoneListParser;
import com.applicationswp.upload.UploadException;
import com.applicationswp.upload.UploadManager;
import org.primefaces.component.tree.Tree;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse ist der Controller fuer die Kurs-Seite und verwaltet diese.
 * Die Funktionen der Kurs-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Kurs-Seite.
 */
@ManagedBean
@ViewScoped
public class CourseViewController {

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;
    private Course course;
    private List<Course> courses;
    private List<Member> availableMembers;


    private List<Member> phoneListMember;
    private AppointmentTypes appointmentTypes;
    private TreeNode phoneTree;
    private int id;
    private Part uploadFile;
    private String currentUploadUrl;
    private TreeNode currentNode;
    private List<TreeNode> allNodes;
    private String paramID;

    public CourseViewController(){
        this.courses = new ArrayList<>();
        this.availableMembers = new ArrayList<>();
        this.allNodes = new ArrayList<>();
    }

    private void buildPhoneTree(){
        this.phoneTree = PhoneListParser.getNodeFromJSON(this.course.getPhoneListJSON());
        this.allNodes = PhoneListParser.getNodesFromNode(this.phoneTree);
    }


    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Benutzer, die Dateien, die Termine,die Ankuendigungen und die Telefonliste
     */
    @PostConstruct
    public void init(){
        this.course = new Course();

        Courses coursesDB = ApplicationCacheController.getInstance().getCourses();
        for(Course course:coursesDB){
            this.courses.add(course);
        }

        Members membersDB = ApplicationCacheController.getInstance().getMembers();
        for(Member member:membersDB){
            this.availableMembers.add(member);
        }


        this.appointmentTypes = ApplicationCacheController.getInstance().getAppointmentTypes();


        try {
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            setParamID(params.get("courseID"));

        }
        catch (Exception expected){

        }
        try {
            id = Integer.parseInt(getParamID());
        }
        catch (Exception expected){

        }
        if(mainApplication.getSessionCacheController().getLastCourse() != null)
            this.course = mainApplication.getSessionCacheController().getLastCourse();

        if(mainApplication.getAuthenticationController().getMember() != null) {
            long ownID = mainApplication.getAuthenticationController().getMember().getID();
            for (Course course : courses) {
                if (course.getID() == id) {
                    for (Member member : course.getMembers()) {
                        if (member.getID() == ownID) {
                            this.course = course;
                            mainApplication.getSessionCacheController().setLastCourse(this.course);
                            break;
                        }
                    }
                }
            }
        }

        buildPhoneTree();

    }


    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Getter fuer den aktuellen Kurs
     * @return den aktuellen Kurs
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Getter fuer die Benutzer des Kurses
     * @return die Benutzer des Kurses
     */
    public List<Member> getMembers(){
        return this.course.getMembers();
    }

    /**
     * Speichert alle aenderungen am Kurs
     */
    public void save() {
        ApplicationCacheController.getInstance().getCourses().updateEntry(course);
    }

    /**
     * Getter fuer alle Dateien des Kurses
     * @return alle Dateien des Kurses
     */
    public List<File> getFiles() {
        return this.course.getFiles();
    }

    /**
     * Getter fuer alle Termine des Kurses
     * @return alle Termine des Kurses
     */
    public List<Appointment> getEvents() {
        return this.course.getEvents();
    }

    /**
     * Getter fuer alle Ankuendigungen des Kurses
     * @return alle Ankuendigungen des Kurses
     */
    public List<Announcement> getAnnouncements(){return this.course.getAnnouncements();}

    /**
     * Getter fuer alle Kurse des Benutzers
     * @return alle Kurse des Benutzers
     */
    public List<Course> getCourses(){
        return this.courses;
    }

    /**
     * Getter fuer alle Mitglieder der Telefonkette
     * @return alle Mitglieder der Telefonkette
     */
    public List<Member> getPhoneListMember() {
        return phoneListMember;
    }

    /**
     * Getter fuer alle verfuegbaren Benutzer, welche dem Kurs hinzugefuegt werden koennen
     * @return alle verfuegbaren Benutzer, welche dem Kurs hinzugefuegt werden koennen
     */
    public List<Member> getAvailableMembers() {
        return availableMembers;
    }

    /**
     * Getter fuer alle verfuegbaren Mitglieder, welche dem Kurs hinzugefuegt werden koennen, als CSV
     * @return alle verfuegbaren Mitglieder, welche dem Kurs hinzugefuegt werden koennen, als CSV
     */
    public List<String> getAvailableMembersCSV(){
        List<String> result = new ArrayList<>();
        for(Member member:availableMembers){
            result.add(String.format("%s %s",member.getFirstname(),member.getLastname()));
        }
        return result;
    }

    /**
     * Getter fuer alle Termintypen
     * @return alle Termintypen
     */
    public AppointmentTypes getAppointmentTypes() {
        return appointmentTypes;
    }





    /**
     * Loescht den aktuellen Kurs
     */
    public void deleteCourse(){
        if(course.getCreatorID() == mainApplication.getAuthenticationController().getMember().getID()) {
            ApplicationCacheController.getInstance().getCourses().removeEntry(this.course);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("courses.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loescht ein Mitglied der Kurses
     * @param member das zu loeschende Mitglied
     */
    public void deleteMember(Member member){
        member.getCourses().removeIf(course1 -> {return course1.getID() == this.course.getID();});
        ApplicationCacheController.getInstance().getMembers().updateEntry(member);
        this.getMembers().remove(member);
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Loescht eine Datei des Kurses
     * @param file die zu loeschende Datei des Kurses
     */
    public void deleteFile(File file){
        this.getFiles().remove(file);
        mainApplication.getSessionCacheController().getFiles().removeEntry(file);
        UploadManager.removeUploadedFile(file.getPath());
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Loescht ein Event des Kurses
     * @param event das zu loeschende Event
     */
    public void deleteEvent(Appointment event){
        this.getEvents().remove(event);
        mainApplication.getSessionCacheController().getAppointments().removeEntry(event);
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Loescht eine Ankuendigung des Kurses
     * @param announcement die zu loeschende Ankuendigung
     */
    public void deleteAnnouncement(Announcement announcement){
        getAnnouncements().remove(announcement);
        mainApplication.getSessionCacheController().getAnnouncements().removeEntry(announcement);
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Fuegt neue Mitglieder dem Kurs hinzu
     * @param ids die IDs der Mitglieder als CSV
     */
    public void addMembers(String ids){
        String[] split = ids.split(",");
        for(String s:split){
            for(Member m:this.availableMembers){
                if(String.format("%s %s",m.getFirstname(),m.getLastname()).equals(s)){
                    getMembers().add(m);
                    m.getCourses().add(this.course);
                    ApplicationCacheController.getInstance().getMembers().updateEntry(m);
                    ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
                }
            }
        }
    }


    /**
     * Event-Handler welcher die aktuelle Url der hochgeladenen Datei anzeigt
     */
    public void uploadFileChanged(){
        if(uploadFile != null) {
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

            getFiles().add(file);
            file.getCourses().add(this.course);
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
     * Fuegt ein neus Event dem Kurs hinzu
     * @param title Titel des Events
     * @param description Beschreibung des Events
     * @param from von wann (Timestring)
     * @param to bis wann (Timestring)
     * @param type der Termintype
     */
    public void addEvent(String title,String description,String from, String to, String type){
        Appointment appointment = new Appointment();
        appointment.setTitle(title);
        appointment.setDescription(description);

        getEvents().add(appointment);
        mainApplication.getSessionCacheController().getAppointments().addEntry(appointment);
        appointment.getCourses().add(this.course);
        mainApplication.getSessionCacheController().getAppointments().updateEntry(appointment);
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Fuegt eine neue Ankuendigung dem Kurs hinzu
     * @param title Titel der Ankuendigung
     * @param description Beschreibung der Ankuendigung
     * @param priority Prioritaet der Ankuendigung (von Enum)
     */
    public void addAnnouncement(String title,String description,String priority){
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setPriority(AnnouncementPriority.values()[Integer.parseInt(priority)]);

        getAnnouncements().add(announcement);
        mainApplication.getSessionCacheController().getAnnouncements().addEntry(announcement);
        announcement.getCourses().add(this.course);
        mainApplication.getSessionCacheController().getAnnouncements().updateEntry(announcement);
        ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
    }

    /**
     * Getter fuer die Farbe (css style) fuer eine Ankuendigung
     * @param announcement die Ankuendigung fuer welche die Farbe zurueckgegeben werden soll
     * @return die Farbe im css Format (color:red oder color:#333)
     */
    public String getColor(Announcement announcement){
        if(announcement.getPriority() == AnnouncementPriority.HIGH)
            return "color:red";
        if(announcement.getPriority() == AnnouncementPriority.MEDIUM)
            return "color:orange";
        if(announcement.getPriority() == AnnouncementPriority.LOW)
            return "color:#333";
        return "color:#333";
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
     * Leitet weiter zur Nachrichtenseite zum direkten Nachrichtenverlauf mit einem Benutzer
     * @param member der Benutzer mit dem geschrieben werden soll
     */
    public void chatWithMember(Member member){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("messages.xhtml?userID="+member.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt zurueck ob der Benutzer der aktuelle Benutzer ist
     * @param member der zu ueberpruefende Benutzer
     * @return true wenn der Benutzer der aktuelle Benutzer ist und false wenn nicht
     */
    public boolean meberIsSelf(Member member){
        return member.getID() == mainApplication.getAuthenticationController().getMember().getID();
    }

    public boolean shouldShowOptions(){
        return mainApplication.getAuthenticationController().getMember().getID() == course.getCreatorID();
    }


    public void addToPhoneTree(String parentNode){
        TreeNode newNode = new DefaultTreeNode(parentNode+"Unknown");
        if(phoneTree.getChildren().size() == 0){
            phoneTree.getChildren().add(newNode);
        }
        else{
            for(TreeNode treeNode:allNodes){
                if(treeNode.getData().equals(parentNode)){
                    newNode = new DefaultTreeNode(newNode.getData().toString()+treeNode.getChildren().size());
                    treeNode.getChildren().add(newNode);
                    break;
                }
            }
        }
        course.setPhoneListJSON(PhoneListParser.getJSONFromTreeNode(phoneTree));
        ApplicationCacheController.getInstance().getCourses().updateEntry(course);
        buildPhoneTree();
    }

    public void setCurrentNode(String currNode){
        for(TreeNode treeNode:allNodes){
            if(treeNode.getData().equals(currNode)){
                currentNode = treeNode;
            }
        }
    }

    public void removeFromPhoneTree(String nodeData){
        TreeNode treeNode = null;
        for(TreeNode node:allNodes){
            if(node.getData().equals(nodeData)){
                treeNode = node;
            }
        }
        if(treeNode != null) {
            if(treeNode.getParent() != null) {
                for(TreeNode child:treeNode.getChildren()){
                    treeNode.getParent().getChildren().add(child);
                    child.setParent(treeNode.getParent());
                }
                treeNode.getParent().getChildren().remove(treeNode);
                treeNode.getChildren().clear();
            }
            else{
                phoneTree.getChildren().remove(treeNode);
            }
        }
        course.setPhoneListJSON(PhoneListParser.getJSONFromTreeNode(phoneTree));
        ApplicationCacheController.getInstance().getCourses().updateEntry(course);
        buildPhoneTree();
    }

    public TreeNode getCurrentNode() {
        return currentNode;
    }

    /**
     * Getter fuer den Telefonkettenbaum
     * @return der Telefonkettenbaum
     */
    public TreeNode getPhoneTree() {
        return phoneTree;
    }


    public void editPhoneMember(String memberID){
        if(currentNode != null) {
            TreeNode newNode = new DefaultTreeNode(memberID);
            newNode.setParent(currentNode.getParent());
            currentNode.getParent().getChildren().add(newNode);
            for (TreeNode child : currentNode.getChildren()) {
                newNode.getChildren().add(child);
            }
            currentNode.getChildren().clear();
            currentNode.getParent().getChildren().remove(currentNode);
            currentNode.setParent(null);
            currentNode = newNode;
            this.course.setPhoneListJSON(PhoneListParser.getJSONFromTreeNode(phoneTree));
            ApplicationCacheController.getInstance().getCourses().updateEntry(this.course);
            buildPhoneTree();
        }

    }

    public String getNodeText(String input){
        try {
            long id = Long.parseLong(input);
            for(Member member:getMembers()){
                if(member.getID() == id){
                    return member.toNameString();
                }
            }
            return input;
        }
        catch (Exception expected){
            return input;
        }
    }

    public String getParamID() {
        return paramID;
    }

    public void setParamID(String paramID) {
        this.paramID = paramID;
    }
}
