package com.applicationswp.upload;

/**
 * Listener fuer den Fortschritt des Uploads
 */
public interface IUploadProgressListener {
    void onStart(String filename);
    void onProgress(int progress,int max);
    void onFinish(String filename);
}
