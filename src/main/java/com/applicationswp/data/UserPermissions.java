package com.applicationswp.data;

/**
 *Diese Klasse ist eine Ansammlung von Rechten eines Benutzers.
 */
public class UserPermissions {

    /**
     * Getter fuer alle Permissions im CSV Format (statt ; wird ein | verwendet)
     * @return alle Permissions im CSV Format
     */
    public String getAllUserPermissions(){
        String result = "";
        result += new Appointment().getAll()+"|";
        result += new User().getAll()+"|";
        result += new Course().getAll()+"|";
        result += new Profile().getAll()+"|";
        result += new Substitution().getAll()+"|";
        result += new Evaluation().getAll()+"|";
        result += new Messages().getAll()+"|";
        result += new ExcuseNote().getAll()+"|";
        result += new Tasks().getAll();
        return result;
    }

    /**
     * Klasse fuer alle Terminberechtigungen
     */
    public class Appointment{
        public static final String PERMISSION_APPOINTMENT_ADD = "APPOINTMENT_ADD";
        public static final String PERMISSION_APPOINTMENT_DELETE = "APPOINTMENT_DELETE";
        public static final String PERMISSION_APPOINTMENT_VIEW = "APPOINTMENT_VIEW";

        public String getAll(){
            return "APPOINTMENT_ADD|APPOINTMENT_DELETE|APPOINTMENT_VIEW";
        }
    }


    public class Substitution{
        public static final String PERMISSION_SUBSTITUTION_ADD = "SUBSTITUTION_ADD";
        public static final String PERMISSION_SUBSTITUTION_DELETE = "SUBSTITUTION_DELETE";
        public static final String PERMISSION_SUBSTITUTION_VIEW = "SUBSTITUTION_VIEW";

        public String getAll(){
            return "SUBSTITUTION_ADD|SUBSTITUTION_DELETE|SUBSTITUTION_VIEW";
        }
    }


    public class Evaluation{
        public static final String PERMISSION_EVALUATION_ADD = "EVALUATION_ADD";
        public static final String PERMISSION_EVALUATION_DELETE = "EVALUATION_DELETE";
        public static final String PERMISSION_EVALUATION_VIEW = "EVALUATION_VIEW";

        public String getAll(){
            return "EVALUATION_ADD|EVALUATION_DELETE|EVALUATION_VIEW";
        }
    }

    /**
     * Klasse fuer alle Benutzerberechtigungen
     */
    public class User{
        public static final String PERMISSION_USER_ADD = "USER_ADD";
        public static final String PERMISSION_USER_DELETE ="USER_DELETE";
        public static final String PERMISSION_USER_VIEW ="USER_VIEW";
        public String getAll(){
            return "USER_ADD|USER_DELETE|USER_VIEW";
        }
    }

    public class ExcuseNote{
        public static final String PERMISSION_EXCUSENOTE_ADD = "EXCUSENOTE_ADD";
        public static final String PERMISSION_EXCUSENOTE_DELETE ="EXCUSENOTE_DELETE";
        public static final String PERMISSION_EXCUSENOTE_VIEW ="EXCUSENOTE_VIEW";
        public String getAll(){
            return "EXCUSENOTE_ADD|EXCUSENOTE_DELETE|EXCUSENOTE_VIEW";
        }
    }


    /**
     * Klasse fuer alle Kursberechtigungen
     */
    public class Course{
        public static final String PERMISSION_COURSE_ADD = "COURSE_ADD";
        public static final String PERMISSION_COURSE_DELETE ="COURSE_DELETE";
        public static final String PERMISSION_COURSE_VIEW ="COURSE_VIEW";
        public String getAll(){
            return "COURSE_ADD|COURSE_DELETE|COURSE_VIEW";
        }
    }

    /**
     * Klasse fuer alle Profilberechtigungen
     */
    public class Profile{
        public static final String PERMISSION_PROFILE_EDIT = "PROFILE_EDIT";
        public static final String PERMISSION_PROFILE_VIEW ="PROFILE_VIEW";
        public String getAll(){
            return "PROFILE_EDIT|PROFILE_VIEW";
        }
    }

    /**
     * Klasse fuer alle Aufgabenberechtigungen
     */
    public class Tasks{
        public static final String PERMISSION_TASK_ADD = "TASK_ADD";
        public static final String PERMISSION_TASK_DELETE ="TASK_DELETE";
        public static final String PERMISSION_TASK_VIEW ="TASK_VIEW";
        public String getAll(){
            return "TASK_ADD|TASK_DELETE|TASK_VIEW";
        }
    }

    /**
     * Klasse fuer alle Nachrichtenberechtigungen
     */
    public class Messages{
        public static final String PERMISSION_MESSAGE_WRITE = "MESSAGE_ADD";
        public static final String PERMISSION_MESSAGE_DELETE ="MESSAGE_DELETE";
        public static final String PERMISSION_MESSAGE_VIEW ="MESSAGE_VIEW";
        public String getAll(){
            return "MESSAGE_ADD|MESSAGE_DELETE|MESSAGE_VIEW";
        }
    }
}
