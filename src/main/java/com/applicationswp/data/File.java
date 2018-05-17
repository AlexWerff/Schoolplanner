package com.applicationswp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert eine Datei.
 */
@Entity
public class File extends BasicEntry{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;

    private String path;
    private String name;

    @ManyToMany
    private List<Course> courses;


    public File(){
        this.courses = new ArrayList<>();
    }


    /**
     * Getter fuer den Pfad
     * @return den Pfad
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter fuer den Pfad
     * @param path den Pfad
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter fuer den Namen
     * @return den Namen
     */
    public String getName() {
        return name;
    }

    /**
     * Setter fuer den Namen
     * @param name den Namen
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter fuer alle Kurse in der die Datei verfuegbar ist
     * @return alle Kurse in der die Datei verfuegbar ist
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Getter fuer die ID
     * @return die ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Setter fuer die ID
     * @param ID die ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }
}
