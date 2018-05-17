package com.applicationswp.upload;

/**
 * Exception fuer den Upload
 */
public class UploadException extends Exception {
    public UploadException(String reason){
        super(reason);
    }
}
