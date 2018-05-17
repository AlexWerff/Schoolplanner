package com.applicationswp.data;

import javax.persistence.*;

/**
 *Diese Klasse repraesentiert eine Nachricht.
 */
@Entity
public class Message extends BasicEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private String message;
    private long timestamp;
    private long recieverID;
    private long senderID;
    //private boolean read;


    /**
     * Getter fuer die ID
     * @return die ID
     */
    @Override
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

    /**
     * Getter fuer die Nachricht
     * @return die Nachricht
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter fuer die Nachricht
     * @param message die Nachricht
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter fuer den Timestamp
     * @return den Timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /***
     * Setter fuer den Timestamp
     * @param timestamp den Timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter fuer die EmpfaengerID
     * @return die EmpfaengerID
     */
    public long getRecieverID() {
        return recieverID;
    }

    /**
     * Setter fuer die EmpfaengerID
     * @param recieverID die EmpfaengerID
     */
    public void setRecieverID(long recieverID) {
        this.recieverID = recieverID;
    }

    /**
     * Getter fuer die SenderID
     * @return die SenderID
     */
    public long getSenderID() {
        return senderID;
    }

    /**
     * Setter fuer die SenderID
     * @param senderID die SenderID
     */
    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    /*public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }*/
}
