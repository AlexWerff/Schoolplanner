package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.Appointment;
import com.applicationswp.data.Member;
import com.applicationswp.data.UserPermissions;
import com.applicationswp.datacontroller.Members;
import com.applicationswp.data.Message;
import com.applicationswp.datacontroller.Messages;
import net.bootsfaces.component.inputText.InputText;
import net.bootsfaces.render.E;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse ist der Controller fuer die Nachrichten-Seite und verwaltet diese.
 * Die Funktionen der Nachrichten-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Nachrichten-Seite.
 */
@ManagedBean
@ViewScoped
public class MessagesController {
    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;

    private List<Message> messages;
    private List<Member> members;
    private Member currentChatMember;
    private String searchText;

    /**
     *
     */
    public MessagesController(){
        messages = new ArrayList<>();
        members = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Nachrichten und Benutzer
     */
    @PostConstruct
    public void init(){
        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Messages.PERMISSION_MESSAGE_VIEW)) {
            long ownID = mainApplication.getAuthenticationController().getMember().getID();
            this.messages = new Messages();

            Members membersDB = ApplicationCacheController.getInstance().getMembers();
            Messages messagesDB = mainApplication.getSessionCacheController().getMessages();

            for (Member member : membersDB) {
                if (member.getID() != ownID) {
                    members.add(member);
                }
            }

            for (Message message : messagesDB) {
                messages.add(message);
            }

            for (Message message : messages) {
                if (message.getRecieverID() == ownID || message.getSenderID() == ownID) {
                    for (Member member : members) {
                        if (message.getRecieverID() == member.getID() || message.getSenderID() == member.getID()) {
                            member.getMessages().add(message);
                        }
                    }
                }
            }
            String parameterOne = "-1";
            try {
                Map<String, String> params = FacesContext.getCurrentInstance().
                        getExternalContext().getRequestParameterMap();
                parameterOne = params.get("userID");
            }catch (Exception ex){
                ex.printStackTrace();
            }

            long userIndex = 0;
            try {
                userIndex = Integer.parseInt(parameterOne);
            } catch (Exception expected) {
                if (members.size() != 0) {
                    userIndex = members.get(0).getID();
                }
            }

            for (Member member : members) {
                if (member.getID() == userIndex) {
                    currentChatMember = member;
                    break;
                }
            }
        }

    }

    /**
     * Getter fuer alle Nachrichten des Benutzers
     * @return alle Nachrichten des Benutzers
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Fuegt einen neue Nachricht hinzu
     * @param content inhalt der Nachricht
     * @throws AuthenticationException wenn der Benutzer die Rechte dafuer nicht besitzt
     */
    public void addMessage(InputText content) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.Messages.PERMISSION_MESSAGE_WRITE)) {
            throw new AuthenticationException(UserPermissions.Messages.PERMISSION_MESSAGE_WRITE);
        }
        Message message = new Message();
        message.setMessage(content.getValue().toString());
        message.setRecieverID(currentChatMember.getID());
        message.setSenderID(mainApplication.getAuthenticationController().getMember().getID());
        message.setTimestamp(System.currentTimeMillis());
        mainApplication.getSessionCacheController().getMessages().addEntry(message);
        getCurrentChatMember().getMessages().add(message);
        content.setValue("");
    }

    /**
     * Getter fuer alle Benutzer
     * @return alle Benutzer
     */
    public List<Member> getMembers(){
        List<Member> result = new ArrayList<>();
        for(Member member:members){
            if(fitsSearchText(member)){
                result.add(member);
            }
        }
        return result;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Setzt den aktuellen Chat
     * @param currentChatMember Benutzer dessen Chat ausgewählt werden soll
     */
    public void setCurrentChatMember(Member currentChatMember) {
        this.currentChatMember = currentChatMember;
    }

    /**
     * Getter fuer den aktuellen Benutzer
     * @return den aktuellen Benutzer
     */
    public Member getCurrentChatMember(){
        return this.currentChatMember;
    }

    /**
     * Getter fuer die aktuellen Nachrichten des aktuellen Chats
     * @return die aktuellen Nachrichten des aktuellen Chats
     */
    public List<Message> getCurrentMessages(){
        if(currentChatMember == null)
            return new ArrayList<>();
        else
            return currentChatMember.getMessages();
    }

    /**
     * Getter fuer die letzte Nachricht eines Chats
     * @param member der Benutzer dessen Chat ausgewaehlt werden soll
     * @return die letzte Nachricht eines Chats
     */
    public Message getLastChatMessage(Member member){
        if(member.getMessages().size() > 0){
            return member.getMessages().get(member.getMessages().size()-1);
        }
        else{
            return new Message();
        }
    }

    /**
     * Getter fuer den namen des aktuellen Chats
     * @return namen des aktuellen Chats
     */
    public String getCurrentChatName(){
        return currentChatMember == null ? "" : currentChatMember.getFirstname();
    }

    /**
     * Getter fuer den CSS-Style eines Chats
     * @param member den Benutzer (Chat)
     * @return den CSS-Style eines Chats
     */
    public String getCurrentChatStyle(Member member){
        if(member == currentChatMember){
            return "chat-contact-item chat-active";
        }
        else{
            return "chat-contact-item";
        }
    }

    /**
     * Getter fuer den CSS-Style einer Nachricht
     * @param message die Nachricht
     * @return den CSS-Style einer Nachricht
     */
    public String getMessageOrientation(Message message){
        if(message.getSenderID() != mainApplication.getAuthenticationController().getMember().getID()){
            return "bubbledLeft";
        }
        else{
            return "bubbledRight";
        }
    }

    /**
     * Getter fuer die ID des aktuellen Benutzers (Chat)
     * @return die ID des aktuellen Benutzers (Chat)
     */
    public long getCurrentMemberID(){
        return currentChatMember == null ? -1 : currentChatMember.getID();
    }

    /**
     * Setter fuer den Suchtext
     * @param searchText den Suchtext
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
     * @param member der zu filternde Benutzer
     * @return true wenn Benutzer der Suche entspricht und fals wenn nicht
     */
    private boolean fitsSearchText(Member member){
        if(searchText == null || searchText.equals("")){
            return true;
        }
        String searchTextLower = searchText.toLowerCase();
        return member.getFirstname().toLowerCase().contains(searchTextLower)
                || member.getLastname().toLowerCase().contains(searchTextLower);
    }
}
