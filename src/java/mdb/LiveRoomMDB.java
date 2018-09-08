/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Dawson
 */

@MessageDriven(mappedName = "jms/yunzhiQueue/liveRoom", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LiveRoomMDB implements MessageListener {

    @Inject
    private JMSContext context;
    @Resource
    private MessageDrivenContext mdc;

    public LiveRoomMDB() {
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        Destination replyDest;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                replyDest = msg.getJMSReplyTo();
                context.createProducer().send(replyDest, msg);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

}
