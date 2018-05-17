package com.applicationswp.datacontroller;

import com.applicationswp.data.BasicEntry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstrakter DatenController
 * Besitzt einen EntityManager welcher die JPA bedient
 * Basisklasse fuer alle DatenController
 */
public abstract class BasicEntryList<T extends BasicEntry> extends ArrayList<T> implements IEntryList<T> {
    private final EntityManagerFactory factory;
    private static EntityManager entityManager;

    public BasicEntryList(){
        factory = Persistence.createEntityManagerFactory("schulplaner");
        if(entityManager == null) {
            entityManager = factory.createEntityManager();
        }
    }


    /**
     * Abstrakte Methode um Daten der Liste mithilfe der JPA zu speichern
     */
    public void save(){
        for(T entry:this){
            updateEntry(entry);
        }
    }

    /**
     * Abstrakte Methodeum Daten der Liste mithilfe der JPA zu laden
     */
    public void load(){
        Query q = getEntityManager().createQuery("select u from "+getTableName()+" u");
        List<T> result = q.getResultList();
        for(T u:result){
            add(u);
        }
    }



    /**
     * Abstrakte Methode um den Datentypen der Liste zu ermitteln
     * @return den Datentypen der Liste
     */
    protected abstract Class<T> getEntityClass();

    protected String getTableName(){
        return getEntityClass().getSimpleName();
    }


    /**
     * Fuegt der Liste einen Eintrag hinzu.
     * Wird der mithilfe der JPA gespeichert
     * @param value der hinzuzufuegende Eintrag
     * @return true wenn erfolgreich und false wenn nicht
     */
    @Override
    public boolean addEntry(T value){
        entityManager.getTransaction().begin();
        entityManager.persist(value);
        entityManager.getTransaction().commit();
        return add(value);
    }

    /**
     * Loescht einen Eintrag aus der Liste mithilfe der JPA
     * @param value der zu loeschende Eintrag
     * @return true wenn erfolgreich und false wenn nicht
     */
    @Override
    public boolean removeEntry(T value) {
        T toBeRemovedID = entityManager.getReference(getEntityClass(), value.getID());
        entityManager.getTransaction().begin();
        entityManager.remove(toBeRemovedID);
        entityManager.getTransaction().commit();
        return remove(value);
    }

    /**
     * Aktualisiert einen Eintrag aus der Liste mithilfe der JPA
     * @param value der zu aktualisierende Eintrag
     * @return true wenn erfolgreich und false wenn nicht
     */
    @Override
    public boolean updateEntry(T value) {
        entityManager.getTransaction().begin();
        entityManager.merge(value);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Getter fuer alle Eintraege der Liste
     * Implementierung IEntryList
     * @return alle Eintraege der Liste
     */
    @Override
    public List<T> getEntries() {
        return this;
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }

}
