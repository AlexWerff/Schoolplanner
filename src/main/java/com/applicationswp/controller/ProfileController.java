package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Members;
import com.applicationswp.dataparser.UserFactory;
import com.applicationswp.upload.UploadException;
import com.applicationswp.upload.UploadManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse ist der Controller fuer die Profil-Seite und verwaltet diese.
 * Die Funktionen der Profil-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Profil-Seite.
 */
@ManagedBean(eager = true)
@ViewScoped
public class ProfileController {
    private Members members;
    private Member thisMember;
    private boolean birthdayIsPublic;
    private boolean nicknameIsPublic;
    private boolean ageIsPublic;
    private boolean phoneNumberIsPublic;
    private boolean emailIsPublic;
    private boolean hobbiesIsPublic;
    private Part uploadFile;

    @ManagedProperty(value = "#{MainApplication}")
    private MainApplication mainApplication;

    /**
     * Konstruktor
     */
    public ProfileController() {

    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Setzt alle Profilattribute (was oeffentlich ist) initial auf true
     * Initialisiert den Benutzer mithilfe des Url Parameters (...?userID=1)
     */
    @PostConstruct
    public void init() {
        birthdayIsPublic = true;
        nicknameIsPublic = true;
        ageIsPublic = true;
        phoneNumberIsPublic = true;
        emailIsPublic = true;
        hobbiesIsPublic = true;
        this.members = ApplicationCacheController.getInstance().getMembers();
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String parameterOne = params.get("userID");
            long userID = -1;
            try {
                userID = Long.parseLong(parameterOne);
            } catch (Exception expected) {
                thisMember = mainApplication.getAuthenticationController().getMember();
            }
            for (Member member : members) {
                if (member.getID() == userID) {
                    thisMember = member;
                }
            }
        }
        catch (Exception ex){
            thisMember = new Member();
        }
    }


    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Getter fuer alle Teilnehmer
     * @return
     */
    public Member getThisMember() {
        return thisMember;
    }

    /**
     * Setter fuer den aktuellen Benutzer welcher auf dem Profil angezeigt wird
     * @param member der Benutzer der angezeigt werden soll
     */
    public void setThisMember(Member member) {
        this.thisMember = member;
    }

    /**
     * Updated die aenderungen des jeweiligen Benutzers
     * @param member
     */
    public void editProfile(Member member) {
        this.members.updateEntry(member);
    }

    /**
     * Getter fuer das Profilattribut Nickname Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String nicknamePublicIcon(){
        if (nicknameIsPublic() == "false"){return "fa-lock";}
        if (nicknameIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Getter fuer das Profilattribut Geburtstag Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String birthdayPublicIcon(){
        if (birthdayIsPublic() == "false"){return "fa-lock";}
        if (birthdayIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Getter fuer das Profilattribut Alter Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String agePublicIcon(){
        if (ageIsPublic() == "false"){return "fa-lock";}
        if (ageIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Getter fuer das Profilattribut Telefonnummer Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String phonePublicIcon(){
        if (phoneIsPublic() == "false"){return "fa-lock";}
        if (phoneIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Getter fuer das Profilattribut Email Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String emailPublicIcon(){
        if (emailIsPublic() == "false"){return "fa-lock";}
        if (emailIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Getter fuer das Profilattribut Hobbies Icon
     * @return die Iconbezeichnung im FontAwesome Stil(fa-lock oder fa-unlock-alt)
     */
    public String hobbiesPublicIcon(){
        if (hobbiesIsPublic() == "false"){return "fa-lock";}
        if (hobbiesIsPublic() == "true"){return "fa-unlock-alt";}
        else {return null;}
    }

    /**
     * Waldet das Feld nicknameIsPublic in einen String um (boolean zu String)
     * @return das Feld nicknameIsPublic als String
     */
    public String nicknameIsPublic(){
        return String.valueOf(nicknameIsPublic);
    }

    /**
     * Waldet das Feld birthdayIsPublic in einen String um (boolean zu String)
     * @return das Feld birthdayIsPublic als String
     */
    public String birthdayIsPublic(){
        return String.valueOf(birthdayIsPublic);
    }

    /**
     * Waldet das Feld ageIsPublic in einen String um (boolean zu String)
     * @return das Feld ageIsPublic als String
     */
    public String ageIsPublic(){
        return String.valueOf(ageIsPublic);
    }

    /**
     * Waldet das Feld phoneNumberIsPublic in einen String um (boolean zu String)
     * @return das Feld phoneNumberIsPublic als String
     */
    public String phoneIsPublic(){
        return String.valueOf(phoneNumberIsPublic);
    }

    /**
     * Waldet das Feld emailIsPublic in einen String um (boolean zu String)
     * @return das Feld emailIsPublic als String
     */
    public String emailIsPublic(){
        return String.valueOf(emailIsPublic);
    }

    /**
     * Waldet das Feld hobbiesIsPublic in einen String um (boolean zu String)
     * @return das Feld hobbiesIsPublic als String
     */
    public String hobbiesIsPublic(){
        return String.valueOf(hobbiesIsPublic);
    }

    /**
     * Welchselt das Feld nicknameIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void nicknameSwitchPublic(){
        if(nicknameIsPublic == true){nicknameIsPublic = false;}
        else {nicknameIsPublic = true;}
    }

    /**
     * Welchselt das Feld birthdayIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void birthdaySwitchPublic(){
        if(birthdayIsPublic == true){birthdayIsPublic = false;}
        else {birthdayIsPublic = true;}
    }

    /**
     * Welchselt das Feld ageIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void ageSwitchPublic(){
        if(ageIsPublic == true){ageIsPublic = false;}
        else {ageIsPublic = true;}
    }

    /**
     * Welchselt das Feld phoneNumberIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void phoneSwitchPublic(){
        if(phoneNumberIsPublic == true){phoneNumberIsPublic = false;}
        else {phoneNumberIsPublic = true;}
    }

    /**
     * Welchselt das Feld emailIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void emailSwitchPublic(){
        if(emailIsPublic == true){emailIsPublic = false;}
        else {emailIsPublic = true;}
    }

    /**
     * Welchselt das Feld hobbiesIsPublic in das Gegenteil (true zu false und false zu true)
     */
    public void hobbiesSwitchPublic(){
        if(hobbiesIsPublic == true){
            hobbiesIsPublic = false;
        }
        else {
            hobbiesIsPublic = true;
        }
    }

    /**
     * Upload Handler um ein Profilfoto hochzuladen
     */
    public void uploadPicture(){
        String uploadUrl = null;
        if(uploadFile != null){
            try {
                uploadUrl = UploadManager.uploadFile(uploadFile, "profilepictures/"+thisMember.getID()+".jpg");
                uploadUrl = "profilepictures/"+uploadUrl;
            } catch (UploadException e) {
                e.printStackTrace();
            }
            uploadFile = null;
            if(uploadUrl != null){
                thisMember.setImageUrl(uploadUrl);
            }
        }
    }


    /**
     * Event-Handler welcher die aktuelle Url der hochgeladenen Datei anzeigt
     */
    public void uploadFileChanged(){
        uploadPicture();
    }

    /**
     * Getter fuer den Upload-Part
     * @return den Upload-Part
     */
    public Part getUploadFile() {
        return uploadFile;
    }

    /**
     * Setter fuer den Upload-Part
     * @param uploadFile den Upload-Part
     */
    public void setUploadFile(Part uploadFile) {
        this.uploadFile = uploadFile;
    }


    /**
     * Gibt zurueck ob der aktuelle Benutzer das Profil bearbeiten darf oder nicht
     * @return true wenn der aktuelle Benutzer das Profil bearbeiten darf
     */
    public boolean canEdit(){
        return mainApplication.getAuthenticationController().getMember().getID() == thisMember.getID();
    }
}
