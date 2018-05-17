package com.applicationswp.security;

import com.applicationswp.data.UserPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory fuer alle gesicherten Seiten und deren notwendige Berechtigungen
 */
public final class SecurePageFactory {

    private SecurePageFactory(){

    }

    /**
     * Getter fuer alle gesicherten Seiten
     * @return alle gesicherten Seiten
     */
    public static List<SecurePage> getSecurePages(){
        List<SecurePage> result = new ArrayList<>();
        /*result.add(new SecurePage().setServletPath("/events.xhtml")
                .setPermissionsRequired(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW));*/

        result.add(new SecurePage().setServletPath("/dashboard.xhtml")
                .setPermissionsRequired(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW));

        result.add(new SecurePage().setServletPath("/courses.xhtml")
                .setPermissionsRequired(UserPermissions.Course.PERMISSION_COURSE_VIEW));

        result.add(new SecurePage().setServletPath("/courseView.xhtml")
                .setPermissionsRequired(UserPermissions.Course.PERMISSION_COURSE_VIEW));

        result.add(new SecurePage().setServletPath("/messages.xhtml")
                .setPermissionsRequired(UserPermissions.Profile.PERMISSION_PROFILE_VIEW));

        result.add(new SecurePage().setServletPath("/profile.xhtml")
                .setPermissionsRequired(UserPermissions.Profile.PERMISSION_PROFILE_VIEW));

        result.add(new SecurePage().setServletPath("/tasks.xhtml")
                .setPermissionsRequired(UserPermissions.Tasks.PERMISSION_TASK_VIEW));

        result.add(new SecurePage().setServletPath("/excusenotes.xhtml")
                .setPermissionsRequired(UserPermissions.ExcuseNote.PERMISSION_EXCUSENOTE_VIEW));

        result.add(new SecurePage().setServletPath("/teacherlist.xhtml")
                .setPermissionsRequired(UserPermissions.Profile.PERMISSION_PROFILE_VIEW));

        result.add(new SecurePage().setServletPath("/timetable.xhtml")
                .setPermissionsRequired(UserPermissions.Appointment.PERMISSION_APPOINTMENT_VIEW));

        result.add(new SecurePage().setServletPath("/evaluation.xhtml")
                .setPermissionsRequired(UserPermissions.Evaluation.PERMISSION_EVALUATION_VIEW));

        result.add(new SecurePage().setServletPath("/substitutionSchedule.xhtml")
                .setPermissionsRequired(UserPermissions.Substitution.PERMISSION_SUBSTITUTION_VIEW));

        result.add(new SecurePage().setServletPath("/admin.xhtml")
                .setPermissionsRequired(UserPermissions.User.PERMISSION_USER_ADD));
        return result;
    }
}
