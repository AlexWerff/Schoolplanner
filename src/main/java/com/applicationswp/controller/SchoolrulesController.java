package com.applicationswp.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Diese Klasse ist der Controller fuer die Schulregeln-Seite und verwaltet diese.
 * Die Funktionen der Schulregeln-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Schulregeln-Seite.
 */
@ManagedBean
@ViewScoped
public class SchoolrulesController {

    @ManagedProperty(value="#{MainApplication}")
    private MainApplication mainApplication;



    public SchoolrulesController(){
    }

    /**
     * Post Konstruktor
     * Dient der Inititialisierung beim Aufruf des Managed Beans
     * Wird automatisch aufgerufen
     */
    @PostConstruct
    public void init(){
    }

    /**
     * Getter fuer die MainApplication
     * @return die MainApplication
     */
    public MainApplication getMainApplication() {
        return mainApplication;
    }

    /**
     * Setter fuer die MainApplication
     * @param mainApplication die MainApplication
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
}
