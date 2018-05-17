package com.applicationswp.PdfForEvaluation;

import java.io.File;
import java.io.IOException;

/**
 * Created by alexandre on 24.11.16.
 */
public class Utily {

    public static boolean checkFile(File file) {
        if (file != null) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating " + file.toString());
            }
            if (file.isFile() && file.canWrite() && file.canRead())
                return true;
        }
        return false;
    }
//    public static void main(String[] args) {
//        Utily da = new Utily();
//        String dat = "pdf/test.txt";
//        if(da.checkFile(new File(dat)))
//            System.out.println(dat + " erzeugt");
//    }

}
