package com.applicationswp.data;

import javax.persistence.*;

/**
 * Created by alexwerff on 19.02.17.
 */
@Entity
public class EditablePage extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String title;
    private String description;
    private String file;
    @Lob
    @Column(length=1000000)
    private byte[] content;

    public EditablePage(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
