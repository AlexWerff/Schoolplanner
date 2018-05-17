package com.applicationswp.controller;

import com.applicationswp.data.*;
import com.applicationswp.datacontroller.Evaluations;
import com.applicationswp.datacontroller.LehrEvaluations;
import com.applicationswp.dataparser.TimeParser;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Diese Klasse ist der Controller fuer die Selbsteinschaetzung-Seite und verwaltet diese.
 * Die Funktionen der Selbsteinschaetzung-Seite werden hier definiert, angelegt und ausgefuehrt.
 * Diese Klasse ist ein ManagedBean fuer die Selbsteinschaetzung-Seite.
 */
@ManagedBean(eager = true)
@ViewScoped
public class EvaluationController{

    private  Evaluations evaluations;
    private  Evaluation evaluation;
    private  Evaluation actuelEvaluation;
    private   LehrEvaluations lehrEvaluations ;
    private  LehrEvaluation lehrEvaluation;

    private  ArrayList<String> punkte ;

    @ManagedProperty(value = "#{MainApplication}")
    private MainApplication mainApplication;


    public EvaluationController() {
        this.evaluations = new Evaluations();
        this.lehrEvaluations = new LehrEvaluations();
        this.lehrEvaluation = new LehrEvaluation();

        this.evaluation = new Evaluation();
        this.punkte = new ArrayList<String>();

    }

    @PostConstruct
    public void init() {
        evaluations.load();
        lehrEvaluations.load();
    }

    /**
     * gibt die liste von alle Bewertungen der Einschätzung
     * @return
     */
    public ArrayList<String> getPunkte() {
        return punkte;
    }

    /**
     *  setzt die liste von alle Bewertungen der Einschätzung
     * @param punkte
     */
    public void setPunkte(ArrayList<String> punkte) {
        this.punkte = punkte;
    }

    /**
     * gibt die Liste mit alle Einschätzungen aus der Datenbank
     * @return
     */
    public Evaluations getEvaluations() {
        return evaluations;
    }

    /**
     * Setzt die Liste der Einschätzungen mit eine neue Liste
     * @param evaluations
     */
    public void setEvaluations(Evaluations evaluations) {
        this.evaluations = evaluations;
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Die Methode für zu die Seite zum Editieren der Einschätzung
     */
    public void edit(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("editEvaluation.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode bekommt die Id einer  Selbsteinschätzung und führt zu einer anderen Seite, die die Selbsteinschätzung mit bewertung anzeigt
     * @param id
     */
    public void view(long id){
        actuelEvaluation = getEvaluationById(id);
        for(String e : getTitelsAsList(actuelEvaluation.getTitels())){
            System.err.println(e);
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("evaluationView.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode bekomme ein String und gibt die dazu eine Liste gekorperte Fragen.
     * @param temp
     * @return
     */
    public ArrayList<String> getQuestionsTitelFromActuel(String temp){
        if(actuelEvaluation != null && temp != null && actuelEvaluation.getTitels() != null){
            int pos = getTitelsAsList(actuelEvaluation.getTitels()).indexOf(temp);
            if(pos != -1 ) {
                return getQuestionsasList(actuelEvaluation.getQuestions()).get(pos);
            }
            return null;
        }
        return null;
    }

    /**
     * Fügt eine Einschätzung in dr Datenbank hinzu.
     * @param titel
     * @param question1
     * @param question2
     * @param question3
     * @param question4
     * @param question5
     * @param question6
     * @param question7
     * @param question8
     * @param question9
     * @param question10
     */
    public  void fillQuestionList(String titel, String question1, String question2, String question3,
                                  String question4, String question5, String question6,
                                  String question7, String question8, String question9, String question10){
        ArrayList<String> list = new ArrayList<>();
        list.add(0,question1);
        list.add(1,question2);
        list.add(2,question3);
        list.add(3,question4);
        list.add(4,question5);
        list.add(5,question6);
        list.add(6,question7);
        list.add(7,question8);
        list.add(8,question9);
        list.add(9,question10);

        if(titel.trim().length() != 0 && list.size() > 0 ){
            if(getLehrEvaluation() != null && getLehrEvaluation().getTitels() != null) {
                lehrEvaluation.setQuestions(getLehrEvaluation().getQuestions()+"#"+allTitelParser(list));
                lehrEvaluation.setTitels(getLehrEvaluation().getTitels()+";"+titel);
                lehrEvaluations.updateEntry(lehrEvaluation);
            }else{
                lehrEvaluation.setQuestions(allTitelParser(list));
                lehrEvaluation.setTitels(titel);
                lehrEvaluations.addEntry(lehrEvaluation);
            }
        }

    }

    public ArrayList<String> getQuestionsTitel(String temp){
        if(getLehrEvaluation() != null && temp != null && lehrEvaluation.getTitels() != null){
            int pos = getTitelsAsList(lehrEvaluation.getTitels()).indexOf(temp);
            if(pos != -1 ) {
                return getQuestionsasList(lehrEvaluation.getQuestions()).get(pos);
            }
            return null;
        }
        return null;
    }

    public void setTitelandQuestionsOfLehrEval (String titel, ArrayList<String> questions){

        if(titel != null && questions != null) {

            if (getTitelsAsList(lehrEvaluation.getTitels()).contains(titel)) {

                int pos = getTitelsAsList(lehrEvaluation.getTitels()).indexOf(titel);
                ArrayList<ArrayList<String>> temp = getQuestionsasList(lehrEvaluation.getQuestions());
                temp.add(pos, questions);

                lehrEvaluation.setQuestions(allQuestionsParser(temp));
            } else {
                lehrEvaluation.setTitels(lehrEvaluation.getTitels() + ";" + titel);
                lehrEvaluation.setQuestions(lehrEvaluation.getQuestions() + "#" + allTitelParser(questions));
            }
        }
    }

    /**
     * Die Methode bekommt ein Id  und löscht den Dazugehörigen Selbsteinschätzung .
     * @param evalId
     */
    public void deleteEvaluation (long evalId){
        evaluations.removeEntry(getEvaluationById(evalId));
    }

    /**
     * Löscht Alle Einschätzung aus der Datenbank.
     */
    public void deleteLehr () {
        if(lehrEvaluations.size() > 0){
                lehrEvaluations.removeEntry(lehrEvaluation);
        }
    }

    /**
     * Durchläuft Alle Selbsteinschätzungen und löscht dann die mit der eingegebene ID.
     * @param id
     * @return
     */
    public Evaluation getEvaluationById(long id){

        if(evaluations != null && !evaluations.isEmpty()){
            for(Evaluation ev : evaluations){
                if(ev.getID() == id){
                    return ev;
                }
            }
        }
        return null;
    }

    /**
     * Die Methode bekommt ein 2-dimensionale Liste und parse den Inhalt zu einem String
     * @param test2
     * @return
     */
    public  String allQuestionsParser(ArrayList<ArrayList<String>> test2){
        String temp = "";
        String temp2 = "";
        String parser2 = "#";
        if(!test2.isEmpty()) {
            temp = allTitelParser(test2.get(0));
            for (int i = 1; i < test2.size(); i++) {
                if (!test2.get(i).isEmpty()) {
                    temp2 = allTitelParser(test2.get(i));
                    temp = temp + parser2 + temp2;
                }
            }
        }
        return temp ;
    }

    /**
     * Die Methode bekommt ein Arraylist als Parameter und parse die zu einem String und gibt die dann zurück.
     * @param test
     * @return
     */
    public  String allTitelParser(ArrayList<String> test){
        String parser1 = ";";
        String temp = "";
        if(test != null && !test.isEmpty()) {
            temp = test.get(0);
            for(int i=1;i<test.size();i++){
                if (!test.get(i).isEmpty()) {
                    temp = temp + parser1 + test.get(i);
                }
            }
        }
        return temp;
    }

    /**
     * Bekommt ein String und gibt die als eine 2-Dimensionale Arraylist nachdem er die gesplittet hat .
     * @param question
     * @return
     */
    public ArrayList<ArrayList<String>> getQuestionsasList(String question){
        ArrayList<ArrayList<String>>  temp = new ArrayList<ArrayList<String>>();
        if(question != null){
            String[] quest = question.split("#");
            if(quest != null){
                for (int i = 0; i < quest.length; i++) {
                    temp.add(i, new ArrayList<>(Arrays.asList(quest[i].split(";"))));
                }
                return  temp;
            }
            return null;
        }
        return null;
    }

    /**
     * Bekommt ein Titel
     * @param titel
     * @return
     */
    public ArrayList<String> getTitelsAsList (String titel){
        if(titel != null){
            return  new ArrayList<String>(Arrays.asList(titel.split(";")));
        }
        return null;
    }

    public LehrEvaluations getLehrEvaluations() {
        return lehrEvaluations;
    }

    public void setLehrEvaluations(LehrEvaluations lehrEvaluations) {
        this.lehrEvaluations = lehrEvaluations;
    }

    public LehrEvaluation getLehrEvaluation() {
        return lehrEvaluation;
    }

    public void setLehrEvaluation(LehrEvaluation lehrEvaluation) {
        this.lehrEvaluation = lehrEvaluation;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void addCurEvaluation (String von, String bis, String text){
        try {
            if (lehrEvaluations.size() > 0) {
                if (lehrEvaluation.getQuestions() != null && von.length() > 0 && bis.length() > 0 && lehrEvaluation.getQuestions().length() > 0 && text.trim().length() > 0) {
                    Evaluation ev = new Evaluation();
                    ev.setBeginn(TimeParser.timeStringToTimestamp(von));
                    ev.setEnde(TimeParser.timeStringToTimestamp(bis));
                    ev.setRating(allTitelParser(punkte));
                    ev.setQuestions(lehrEvaluation.getQuestions());
                    ev.setTitels(lehrEvaluation.getTitels());
                    ev.setText(text);

                    evaluations.addEntry(ev);
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public String getParserTime(long sub){
        return TimeParser.timestampToTimestring(sub);
    }

    public Evaluation getActuelEvaluation() {
        return actuelEvaluation;
    }

    public void setActuelEvaluation(Evaluation actuelEvaluation) {
        this.actuelEvaluation = actuelEvaluation;
    }
}
