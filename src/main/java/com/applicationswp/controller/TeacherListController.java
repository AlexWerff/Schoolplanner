package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Course;
import com.applicationswp.data.Member;
import com.applicationswp.data.UserPermissions;
import com.applicationswp.datacontroller.Courses;
import com.applicationswp.datacontroller.MemberRoles;
import com.applicationswp.datacontroller.Members;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Lehrerlisten-Seite und verwaltet diese.
 * Die Funktionen der Admin-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Lehrerlisten-Seite.
 */
@ManagedBean
@ViewScoped
public class TeacherListController {
    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    private List<Member> teacher;

    /**
     * Konstruktor
     */
    public TeacherListController(){

    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die die Liste der Lehrer des aktuellen Benutzers
     */
    @PostConstruct
    public void init(){
        long memberID = -1;
        if(mainApplication.getAuthenticationController().isLoggedIn()){
            memberID = mainApplication.getAuthenticationController().getMember().getID();
        }
        if(memberID == -1){
            return;
        }

        teacher = new ArrayList<>();
        Members membersDB = ApplicationCacheController.getInstance().getMembers();
        MemberRoles memberRolesDB = ApplicationCacheController.getInstance().getMemberRoles();
        Courses coursesDB = ApplicationCacheController.getInstance().getCourses();

        List<Course> ownCourses = new ArrayList<>();
        for(Course course:coursesDB){
            for(Member member:course.getMembers()){
                if(member.getID() == memberID){
                    ownCourses.add(course);
                    break;
                }
            }
        }


        for(Member member:membersDB){
            boolean canAdd = true;
            for(String permissionNeeded:getPermissionsToBeTeacher()){
                if(!member.getPermissions().contains(permissionNeeded)){
                    canAdd = false;
                    break;
                }
            }

            boolean inCourse = false;
            for(Course course:ownCourses){
                for(Member mem:course.getMembers()){
                    if(mem.getID() == member.getID()){
                        inCourse = true;
                        break;
                    }
                }
                if(inCourse){
                    break;
                }
            }

            if(canAdd && inCourse){
                teacher.add(member);
            }
        }
    }

    /**
     * Getter fuer die Rechte die ein Benutzer braucht um ein Lehrer zu sein
     * @return die Rechte die ein Benutzer braucht um ein Lehrer zu sein
     */
    private String[] getPermissionsToBeTeacher(){
        return new String[]{
                UserPermissions.Substitution.PERMISSION_SUBSTITUTION_ADD
        };
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
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
     * Getter fuer die Lehrer des aktuellen Benutzers
     * @return die Lehrer des aktuellen Benutzers
     */
    public List<Member> getTeacher() {
        return teacher;
    }
}
