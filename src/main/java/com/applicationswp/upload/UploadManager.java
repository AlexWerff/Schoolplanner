package com.applicationswp.upload;

import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Hilfsklasse welche Uploads verwaltet und durchfuehrt
 */
public final class UploadManager {
    private UploadManager(){

    }

    /**
     * Laedt eine Datei hoch
     * @param uploadFile der Payload der Datei
     * @param outputFilename der Name wie die Datei auf dem Server gespeichert werden soll
     * @return den Pfad auf dem Server
     * @throws UploadException wenn ein Fehler beim Upload passiert
     */
    public static String uploadFile(Part uploadFile,String outputFilename) throws UploadException{
        return uploadFile(uploadFile,outputFilename,null);
    }

    /**
     * Laedt eine Datei hoch
     * @param uploadFile der Payload der Datei
     * @param outputFilename der Name wie die Datei auf dem Server gespeichert werden soll
     * @param listener der Listener fuer den Fortschritt
     * @return den Pfad auf dem Server
     * @throws UploadException wenn ein Fehler beim Upload passiert
     */
    public static String uploadFile(Part uploadFile,String outputFilename,IUploadProgressListener listener) throws UploadException {
        if(uploadFile == null){
            throw new UploadException("Uploadfile is null");
        }
        if(listener != null){
            listener.onStart(outputFilename);
        }

        String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String uploadsFolder = absoluteWebPath + "" + Paths.UPLOAD_PATH;
        File u = new File(uploadsFolder);
        u.mkdirs();
        String urlPath = String.format("%s/%s", Paths.UPLOAD_PATH, outputFilename);
        String outputPath = String.format("%s/%s", uploadsFolder, outputFilename);

        try {
            long fileSize = uploadFile.getSize();
            InputStream inputStream = uploadFile.getInputStream();


            FileOutputStream outputStream = new FileOutputStream(outputPath);

            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (true) {
                bytesRead = inputStream.read(buffer);
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                    if (listener != null) {
                        listener.onProgress(bytesRead, (int) fileSize);
                    }
                } else {
                    break;
                }
            }
            outputStream.close();
            inputStream.close();
        }
        catch (Exception ex){
            throw new UploadException(ex.toString());
        }
        if(listener != null){
            listener.onFinish(outputFilename);
        }
        return urlPath;
    }


    /**
     * Loescht eine hochgeladene Datei auf dem Server
     * @param filePath Pfad zu der zu loeschenden Datei
     * @return
     */
    public static boolean removeUploadedFile(String filePath){
        String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String path = String.format("%s%s",absoluteWebPath,filePath);
        File deleteFile = new File(path);
        return deleteFile.delete();
    }

    /**
     * Gibt den Dateinamen fuer einen Datei Payload zurueck
     * @param part den Datei Payload
     * @return den Dateinamen fuer den Datei Payload
     */
    public static String getFullFilename(Part part){
        return getFilename(part);
    }

    /**
     * Gibt den Dateinamen fuer einen Datei Payload zurueck
     * @param part den Datei Payload
     * @return den Dateinamen fuer den Datei Payload
     */
    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
