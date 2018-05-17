package com.applicationswp.controller;

import com.applicationswp.controller.cache.ApplicationCacheController;
import com.applicationswp.data.*;
import com.applicationswp.datacontroller.EditablePages;
import com.applicationswp.datacontroller.Members;
import com.applicationswp.datacontroller.MemberRoles;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.applicationswp.datacontroller.RegisterCredits;
import com.applicationswp.dataparser.EditablePageFactory;
import com.applicationswp.dataparser.UserFactory;
import com.applicationswp.security.MD5Generator;
import com.applicationswp.security.RandomStringGenerator;
import net.bootsfaces.component.tree.event.TreeNodeCheckedEvent;
import net.bootsfaces.component.tree.event.TreeNodeEventListener;
import net.bootsfaces.component.tree.event.TreeNodeSelectionEvent;
import net.bootsfaces.component.tree.model.DefaultNodeImpl;
import net.bootsfaces.component.tree.model.Node;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist der Controller fuer die Admin-Seite und verwaltet diese.
 * Die Funktionen der Admin-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Admin-Seite.
 */
@ManagedBean
@ViewScoped
public class AdminController implements TreeNodeEventListener {

    private static final String DEFAULT_PASSWORD ="start";


    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;
    private List<Member> members;
    private List<MemberRole> memberRoles;
    private List<RegisterCredit> registerCredits;
    private Member currentMember;
    private MemberRole currentRole;
    private String currentPermissionFromTree;
    private Node permissionTree;
    private List<EditablePage> editPages;
    private EditablePage currentEditPage;
    private String currentEditPageContent;
    private String newPassword;
    private RegisterCredit currentRegister;

    public AdminController(){
        memberRoles = new ArrayList<>();
        members = new ArrayList<>();
        editPages = new ArrayList<>();
        registerCredits = new ArrayList<>();
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     * Initialisiert die Benutzer und baut den PermissionTree
     */
    @PostConstruct
    public void init(){
        currentMember = new Member();

        if(mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_VIEW)) {
            Members membersDB = ApplicationCacheController.getInstance().getMembers();
            MemberRoles memberRolesDB = ApplicationCacheController.getInstance().getMemberRoles();
            EditablePages editablePagesDB = ApplicationCacheController.getInstance().getEditablePages();
            RegisterCredits registerCreditsDB = ApplicationCacheController.getInstance().getRegisterCredits();

            for (Member member : membersDB) {
                members.add(member);
            }

            for (MemberRole memberRole : memberRolesDB) {
                memberRoles.add(memberRole);
            }

            for (EditablePage editablePage : editablePagesDB) {
                editPages.add(editablePage);
            }

            for(RegisterCredit registerCredit:registerCreditsDB){
                registerCredits.add(registerCredit);
            }
            this.permissionTree = new DefaultNodeImpl("Berechtigungen");
            UserFactory.generatePermissionTree(this.permissionTree);
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
     * Getter fuer die Members
     * @return members
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Setzt fuer einen Benutzer die Rolle und somit die zugehoerigen Permissions
     * Wenn der Name des Benutzers leer ist wird ein automatisch generierter zugewiesen
     * @param member der zu bearbeitende Benutzer
     * @param role die Rolle die zugewiesen werden soll
     */
    public void editUser(Member member,String role) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        if(member == null){
            return;
        }
        if(member.getFirstname().equals("") || member.getLastname().equals("")){
            return;
        }
        int index = -1;
        try{
            index = Integer.parseInt(role);
        }
        catch (Exception expected){

        }
        if(newPassword != null && !newPassword.equals("")){
            member.setPassword(MD5Generator.getMD5(newPassword));
        }
        MemberRole newRole = new MemberRole();
        if(currentPermissionFromTree != null)
            newRole.setPermissions(currentPermissionFromTree);
        else
            newRole.setPermissions(member.getPermissions());
        for(MemberRole memberRole:memberRoles){
            if(memberRole.getID() == index){
                newRole = memberRole;
            }
        }
        member.setPermissions(newRole.getPermissions());
        if(currentMember.getUsername() == null || currentMember.getUsername().equals(""))
            member.setUsername(UserFactory.generateUsername(currentMember.getFirstname(),currentMember.getLastname()));
        else
            member.setUsername(currentMember.getUsername());
        ApplicationCacheController.getInstance().getMembers().updateEntry(member);
        this.setNewPassword("");

    }

    /**
     * Loescht einen Benutzer
     * @param member der zu loeschende Benutzer
     */
    public void deleteUser(Member member) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_DELETE)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_DELETE);
        }
        ApplicationCacheController.getInstance().getMembers().removeEntry(member);
        members.remove(member);
    }

    /**
     * Getter fuer den aktuell bearbeitenden Benutzer
     * @return
     */
    public Member getCurrentMember() {
        return currentMember;
    }


    public void addCurrentMember() throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        currentMember = new Member();
        currentMember.setPassword(MD5Generator.getMD5(DEFAULT_PASSWORD));
        currentMember.setImageUrl(UserFactory.getDefaultProfilePicture());
        ApplicationCacheController.getInstance().getMembers().addEntry(currentMember);
        members.add(currentMember);
    }

    /**
     * Setter fuer den aktuell zu bearbeitenden Benutzer
     * @param member der aktuell zu bearbeitenden Benutzer
     */
    public void setCurrentMember(Member member){
        this.currentMember = member;
        this.currentPermissionFromTree = currentMember.getPermissions();
        try {
            UserFactory.setPermissionTreePermissions(permissionTree, currentMember.getPermissions());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Getter fuer die Benutzerrollen
     * @return die Benutzerrollen
     */
    public List<MemberRole> getMemberRoles() {
        return memberRoles;
    }

    /**
     * Getter fuer die zu bearbeitende Benutzerrolle
     * @return die zu bearbeitende Benutzerrolle
     */
    public MemberRole getCurrentRole() {
        return currentRole;
    }

    /**
     * Setter fuer die zu bearbeitende Benutzerrolle
     * @param currentRole die zu bearbeitende Benutzerrolle
     */
    public void setCurrentRole(MemberRole currentRole) {
        this.currentRole = currentRole;
        this.currentPermissionFromTree = currentRole.getPermissions();
        try {
            UserFactory.setPermissionTreePermissions(permissionTree, currentRole.getPermissions());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }



    /**
     * Loescht eine Benutzerrolle
     * @param role die zu loeschende Benutzerrolle
     */
    public void deleteRole(MemberRole role) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_DELETE)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_DELETE);
        }
        memberRoles.remove(role);
        ApplicationCacheController.getInstance().getMemberRoles().removeEntry(role);
    }

    /**
     * Setzt fuer eine Rolle den aktuellen PermissionTree
     * @param role die Rolle welche bearbeitet werden soll
     */
    public void editRole(MemberRole role) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        if(role == null){
            return;
        }
        if(currentPermissionFromTree != null)
            role.setPermissions(currentPermissionFromTree);
        ApplicationCacheController.getInstance().getMemberRoles().updateEntry(role);
    }


    /**
     * Fuegt eine neue Rolle hinzu
     */
    public void addRole() throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        this.currentRole = new MemberRole();
        memberRoles.add(currentRole);
        ApplicationCacheController.getInstance().getMemberRoles().addEntry(currentRole);
    }


    /**
     * Getter fuer den PermissionTree
     * @return den PermissionTree
     */
    public Node getPermissionTree() {
        return permissionTree;
    }





    @Override
    public void processValueChange(TreeNodeSelectionEvent treeNodeSelectionEvent) {

    }

    /**
     * Methode welche aufgerufen wird wenn ein Knoten im Baum ausgewaehlt wird
     * @param treeNodeCheckedEvent der ausgewaehlte Knoten
     */
    @Override
    public void processValueChecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {
        Node node = (DefaultNodeImpl) treeNodeCheckedEvent.getNode();
        node.setChecked(true);
        try {
            addPermissionNodeToCurrent(node);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    private void addPermissionNodeToCurrent(Node node) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            //throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        if(node.getData() != null){
            if(currentPermissionFromTree == null)
                currentPermissionFromTree = node.getData();
            else
                currentPermissionFromTree+= "|"+node.getData();
        }
    }

    private void removePermissionNodeFromCurrent(Node node) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_DELETE)){
            //throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_DELETE);
        }
        if(node.getData() != null){
            if(currentPermissionFromTree != null){
                currentPermissionFromTree = currentPermissionFromTree.replace(node.getData(),"");
            }

        }
    }

    /**
     * Methode welche aufgerufen wird wenn ein Knoten im Baum abgewaehlt wird
     * @param treeNodeCheckedEvent der abgewaehlte Knoten
     */
    @Override
    public void processValueUnchecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {
        Node node = (DefaultNodeImpl) treeNodeCheckedEvent.getNode();
        node.setChecked(true);
        try {
            removePermissionNodeFromCurrent(node);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode welche bestimmt ob ein bestimmter Knoten selektiert ist
     * @param node der zu analysierende Knoten
     * @return true wenn selektiert und false wenn nicht
     */
    @Override
    public boolean isValueSelected(Node node) {
        return false;
    }

    /**
     * Methode welche bestimmt ob ein bestimmter Knoten ausgewaehlt ist
     * @param node der zu analysierende Knoten
     * @return true wenn ausgewaehlt und false wenn nicht
     */
    @Override
    public boolean isValueChecked(Node node) {
        return false;
    }

    /**
     * Getter fuer bearbeitbare Seiten
     * @return die bearbeitbaren Seiten
     */
    public List<EditablePage> getEditPages() {
        return editPages;
    }

    /**
     * Bearbeitet eine Seite (setzen der currentEditPage)
     * @param page die zu bearbeitende Seite
     */
    public void editPage(EditablePage page){
        currentEditPage = page;
        setCurrentEditPageContent(
                EditablePageFactory.pageContentWithoutJSF(new String(currentEditPage.getContent(), Charset.defaultCharset())));
    }

    /**
     * Speichert den Inhalt einer bearbeitbaren Seite
     * @param content den neuen Inhalt der Seite
     */
    public void savePageContent(String content){
        if(currentEditPage != null) {
            String fullContent = EditablePageFactory.pageContentWithJSF(content);
            currentEditPage.setContent(fullContent.getBytes(Charset.defaultCharset()));
            EditablePageFactory.setContentForPage(currentEditPage, fullContent.getBytes(Charset.defaultCharset()));
            ApplicationCacheController.getInstance().getEditablePages().updateEntry(currentEditPage);
        }
    }

    /**
     * Getter fuer den Inhalt der aktuelle bearbeitbare Seite
     * @return die aktuelle bearbeitbare Seite
     */
    public String getCurrentEditPageContent(){
        return this.currentEditPageContent;
    }

    /**
     * Setter fuer den Inhalt der aktuelle bearbeitbare Seite
     * @param content den Inhalt der aktuelle bearbeitbare Seite
     */
    public void setCurrentEditPageContent(String content){
        this.currentEditPageContent = content;
    }

    /**
     * Setter fuer das neue Passwort eines Benutzers
     * @param newPassword das neue Passwort eines Benutzers
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Setter fuer das neue Passwort eines Benutzers
     * @return das neue Passwort eines Benutzers
     */
    public String getNewPassword(){
        return this.newPassword;
    }

    /**
     * Getter fuer den aktuellen RegisterCredit
     * @return den aktuellen RegisterCredit
     */
    public RegisterCredit getCurrentRegister() {
        return currentRegister;
    }

    /**
     * Setter fuer den aktuellen RegisterCredit
     * @param currentRegister den aktuellen RegisterCredit
     */
    public void setCurrentRegister(RegisterCredit currentRegister) {
        this.currentRegister = currentRegister;
        this.currentPermissionFromTree = currentRegister.getPermissions();
        try {
            UserFactory.setPermissionTreePermissions(permissionTree, currentRegister.getPermissions());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Fuegt einen neuen RegisterCredit hinzu
     * @throws AuthenticationException wenn der Benutzer die Rechte dafuer nicht besitzt
     */
    public void addRegisterCredit() throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_ADD)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_ADD);
        }
        currentRegister = new RegisterCredit();
        currentRegister.setPassword(RandomStringGenerator.getRandomString());
        registerCredits.add(currentRegister);
        ApplicationCacheController.getInstance().getRegisterCredits().addEntry(currentRegister);
    }

    /**
     * Löscht einen RegisterCredit
     * @param registerCredit den zu loeschenden RegisterCredit
     * @throws AuthenticationException wenn der Benutzer die Rechte dafuer nicht besitzt
     */
    public void deleteRegister(RegisterCredit registerCredit) throws AuthenticationException {
        if(!mainApplication.getAuthenticationController().isAuthenticated(UserPermissions.User.PERMISSION_USER_DELETE)){
            throw new AuthenticationException(UserPermissions.User.PERMISSION_USER_DELETE);
        }
        registerCredits.remove(registerCredit);
        ApplicationCacheController.getInstance().getRegisterCredits().removeEntry(registerCredit);
    }

    /**
     * Bearbeitet einen RegisterCredit
     * @param registerCredit den zu bearbeitenden RegisterCredit
     */
    public void editRegister(RegisterCredit registerCredit){
        currentRegister = registerCredit;
        if(currentRegister == null){
            return;
        }
        if(currentPermissionFromTree != null)
            currentRegister.setPermissions(currentPermissionFromTree);
        ApplicationCacheController.getInstance().getRegisterCredits().updateEntry(registerCredit);
    }


    /**
     * Getter fuer alle RegisterCredits
     * @return alle RegisterCredits
     */
    public List<RegisterCredit> getRegisterCredits() {
        return registerCredits;
    }

}
