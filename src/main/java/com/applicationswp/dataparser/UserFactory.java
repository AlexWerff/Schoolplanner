package com.applicationswp.dataparser;

import com.applicationswp.data.Member;
import com.applicationswp.data.MemberRole;
import com.applicationswp.data.UserPermissions;
import com.applicationswp.security.MD5Generator;
import net.bootsfaces.component.tree.model.DefaultNodeImpl;
import net.bootsfaces.component.tree.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating Member objects
 */
public final class UserFactory {

    private UserFactory(){

    }

    /**
     * Erstellt einen Admin Benutzer und setzt den Benutzernamen und das Passwort
     * @param username der Benutzername
     * @param password das Passwort
     * @return der neue Admin Benutzer
     */
    public static Member getAdminUser(String username,String password){
        Member member = new Member();
        member.setUsername(username);
        member.setFirstname("Admin");
        member.setLastname("Admin");
        member.setPermissions(new UserPermissions().getAllUserPermissions());
        member.setPhoneNumber("12345678");
        member.setImageUrl(getDefaultProfilePicture());
        member.setPassword(password);
        return member;
    }

    public static Member getNewDefaultMember(String username,String password){
        Member member = new Member();
        member.setUsername(username);
        member.setFirstname("User");
        member.setLastname("User");
        member.setPhoneNumber("12345678");
        member.setImageUrl(getDefaultProfilePicture());
        member.setPassword(password);
        return member;
    }

    /**
     * Generiert einen Benuternamen mithilfe der Vor- und Nachnamens
     * @param firstname der Vorname
     * @param lastname der Nachname
     * @return der neue Benutzername
     */
    public static String generateUsername(String firstname,String lastname){
        return (firstname+""+lastname).replace(" ","");
    }


    public static List<MemberRole> getDefaultMemberRoles(){
        List<MemberRole> roles = new ArrayList<>();
        MemberRole adminRole = new MemberRole();
        adminRole.setTitle("Admin");
        adminRole.setDescription("Admin Rolle");
        adminRole.setPermissions(new UserPermissions().getAllUserPermissions());
        roles.add(adminRole);

        MemberRole teacherRole = new MemberRole();
        teacherRole.setTitle("Lehrer");
        teacherRole.setDescription("Lehrer Rolle");
        String permissionsTeacher = "APPOINTMENT_ADD|APPOINTMENT_DELETE|APPOINTMENT_VIEW|COURSE_ADD|COURSE_DELETE|COURSE_VIEW|PROFILE_EDIT|PROFILE_VIEW|SUBSTITUTION_ADD|SUBSTITUTION_DELETE|SUBSTITUTION_VIEW|EVALUATION_ADD|EVALUATION_DELETE|EVALUATION_VIEW|MESSAGE_ADD|MESSAGE_DELETE|MESSAGE_VIEW|EXCUSENOTE_ADD|EXCUSENOTE_DELETE|EXCUSENOTE_VIEW|TASK_ADD|TASK_DELETE|TASK_VIEW";
        teacherRole.setPermissions(permissionsTeacher);
        roles.add(teacherRole);

        MemberRole studentRole = new MemberRole();
        studentRole.setTitle("Schüler");
        studentRole.setDescription("Schüler Rolle");
        String permissionsStudent = "APPOINTMENT_ADD|APPOINTMENT_DELETE|APPOINTMENT_VIEW|COURSE_ADD|COURSE_DELETE|COURSE_VIEW|PROFILE_EDIT|PROFILE_VIEW|SUBSTITUTION_VIEW|EVALUATION_ADD|EVALUATION_DELETE|EVALUATION_VIEW|MESSAGE_ADD|MESSAGE_DELETE|MESSAGE_VIEW|EXCUSENOTE_ADD|EXCUSENOTE_DELETE|EXCUSENOTE_VIEW|TASK_ADD|TASK_DELETE|TASK_VIEW";
        studentRole.setPermissions(permissionsStudent);
        roles.add(studentRole);

        return roles;
    }


    public static void setPermissionTreePermissions(Node treePermissions,String userPermissions){
        String[] split = userPermissions.split("\\|");
        for(Node node:treePermissions.getChilds()){
            node.setChecked(false);
        }
        for(Node node:treePermissions.getChilds()){
            for(String permission:split) {
                if (node.getData() != null && node.getData().equals(permission)){
                    node.setChecked(true);
                }
            }
            setPermissionTreePermissions(node,userPermissions);
        }
    }
    
    
    public static void generatePermissionTree(Node permissionTree){
        permissionTree.getChilds().add(new DefaultNodeImpl("Events"));
        permissionTree.getChilds().get(0).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("APPOINTMENT_ADD"));
        permissionTree.getChilds().get(0).getChilds().add(new DefaultNodeImpl("Loeschen").withData("APPOINTMENT_DELETE"));
        permissionTree.getChilds().get(0).getChilds().add(new DefaultNodeImpl("Ansehen").withData("APPOINTMENT_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Benutzer"));
        permissionTree.getChilds().get(1).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("USER_ADD"));
        permissionTree.getChilds().get(1).getChilds().add(new DefaultNodeImpl("Loeschen").withData("USER_DELETE"));
        permissionTree.getChilds().get(1).getChilds().add(new DefaultNodeImpl("Ansehen").withData("USER_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Kurse"));
        permissionTree.getChilds().get(2).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("COURSE_ADD"));
        permissionTree.getChilds().get(2).getChilds().add(new DefaultNodeImpl("Loeschen").withData("COURSE_DELETE"));
        permissionTree.getChilds().get(2).getChilds().add(new DefaultNodeImpl("Ansehen").withData("COURSE_VIEW"));


        permissionTree.getChilds().add(new DefaultNodeImpl("Profil"));
        permissionTree.getChilds().get(3).getChilds().add(new DefaultNodeImpl("Bearbeiten").withData("PROFILE_EDIT"));
        permissionTree.getChilds().get(3).getChilds().add(new DefaultNodeImpl("Ansehen").withData("PROFILE_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Aufgaben"));
        permissionTree.getChilds().get(4).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("TASK_ADD"));
        permissionTree.getChilds().get(4).getChilds().add(new DefaultNodeImpl("Loeschen").withData("TASK_DELETE"));
        permissionTree.getChilds().get(4).getChilds().add(new DefaultNodeImpl("Ansehen").withData("TASK_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Vertretungen"));
        permissionTree.getChilds().get(5).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("SUBSTITUTION_ADD"));
        permissionTree.getChilds().get(5).getChilds().add(new DefaultNodeImpl("Loeschen").withData("SUBSTITUTION_DELETE"));
        permissionTree.getChilds().get(5).getChilds().add(new DefaultNodeImpl("Ansehen").withData("SUBSTITUTION_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Selbsteinschätzung"));
        permissionTree.getChilds().get(6).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("EVALUATION_ADD"));
        permissionTree.getChilds().get(6).getChilds().add(new DefaultNodeImpl("Loeschen").withData("EVALUATION_DELETE"));
        permissionTree.getChilds().get(6).getChilds().add(new DefaultNodeImpl("Ansehen").withData("EVALUATION_VIEW"));


        permissionTree.getChilds().add(new DefaultNodeImpl("Nachrichten"));
        permissionTree.getChilds().get(7).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("MESSAGE_ADD"));
        permissionTree.getChilds().get(7).getChilds().add(new DefaultNodeImpl("Loeschen").withData("MESSAGE_DELETE"));
        permissionTree.getChilds().get(7).getChilds().add(new DefaultNodeImpl("Ansehen").withData("MESSAGE_VIEW"));

        permissionTree.getChilds().add(new DefaultNodeImpl("Fehlzeiten"));
        permissionTree.getChilds().get(8).getChilds().add(new DefaultNodeImpl("Hinzufuegen").withData("EXCUSENOTE_ADD"));
        permissionTree.getChilds().get(8).getChilds().add(new DefaultNodeImpl("Loeschen").withData("EXCUSENOTE_DELETE"));
        permissionTree.getChilds().get(8).getChilds().add(new DefaultNodeImpl("Ansehen").withData("EXCUSENOTE_VIEW"));


    }


    public static String getDefaultProfilePicture(){
        return "https://thebenclark.files.wordpress.com/2014/03/facebook-default-no-profile-pic.jpg";
    }

}
