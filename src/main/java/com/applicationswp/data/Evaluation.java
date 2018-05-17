package com.applicationswp.data;

import javax.persistence.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Diese Klasse repraesentiert eine Selbsteinschaetzung.
 */
@Entity
public class Evaluation extends BasicEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private long beginn;
    private long ende;
    private String rating;
    private String text;
    @Lob
    @Column(length=1000000)
    private String questions;
    @Lob
    @Column(length=1000000)
    private String titels;
    private int userID;

    public Evaluation(){
        ID =-1;
    }

    @Override
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getBeginn() {
        return beginn;
    }

    public void setBeginn(long beginn) {
        this.beginn = beginn;
    }

    public long getEnde() {
        return ende;
    }

    public void setEnde(long ende) {
        this.ende = ende;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getTitels() {
        return titels;
    }

    public void setTitels(String titels) {
        this.titels = titels;
    }
}
