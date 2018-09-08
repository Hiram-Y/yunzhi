/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dawson
 */
@Stateless
public class MessageFacade extends AbstractFacade<Message> {

    @PersistenceContext(unitName = "yunzhiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessageFacade() {
        super(Message.class);
    }

    /**
     *
     * @param userId
     * @param talkTo
     * @return
     */
    public List<Message> findByUserIdAndTalkTo(String userId, String talkTo) {
        Query q = em.createQuery("SELECT m FROM Message m WHERE m.messagePK.userid = :userid AND m.messagePK.talkTo = :talkTo");
        q.setParameter("userid", userId);
        q.setParameter("talkTo", talkTo);
        List<Message> messageList = q.getResultList();
        return messageList;
    }

}
