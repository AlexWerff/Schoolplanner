package com.applicationswp.datacontroller;

import com.applicationswp.data.Member;
import com.applicationswp.data.Message;

import javax.persistence.Query;
import java.util.List;

/**
 * Diese Klasse repraesentiert einen Datencontroller zum Verwalten von Nachrichten.
 * Ist synchron mit der Datenbank. Diese Klasse fuehrt somit auch Datenbankoperationen aus.
 */
public class Messages extends BasicEntryList<Message> {

    /**
     * Getter fuer den Klassentypen des DatenControllers
     * @return den Klassentypen des DatenControllers
     */
    @Override
    protected Class<Message> getEntityClass() {
        return Message.class;
    }


    public void load(long userID){
        Query q = getEntityManager().createQuery(
                String.format("select u from Message u where u.senderID=%s or u.recieverID=%s",userID,userID));
        List<Message> result = q.getResultList();
        for(Message u:result){
            add(u);
        }
    }
}
