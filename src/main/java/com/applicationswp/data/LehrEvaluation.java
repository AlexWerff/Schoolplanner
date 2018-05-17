package com.applicationswp.data;

import javax.persistence.*;

/**
 * Created by alexandre on 20.02.17.
 */
@Entity
public class LehrEvaluation extends BasicEntry{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    @Column(length=1000000)
    private String titels;
    @Lob
    @Column(length=1000000)
    private String questions;

    public LehrEvaluation(){
        id =-1;
    }

    public String getTitels() {
        return titels;
    }

    public void setTitels(String titels) {
        this.titels = titels;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    @Override
    public long getID() {
        return id;
    }
}
