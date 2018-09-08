/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findByUserid", query = "SELECT m FROM Message m WHERE m.messagePK.userid = :userid")
    , @NamedQuery(name = "Message.findByTalkTo", query = "SELECT m FROM Message m WHERE m.messagePK.talkTo = :talkTo")
    , @NamedQuery(name = "Message.findByDatetime", query = "SELECT m FROM Message m WHERE m.messagePK.datetime = :datetime")
    , @NamedQuery(name = "Message.findByWhospeak", query = "SELECT m FROM Message m WHERE m.whospeak = :whospeak")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MessagePK messagePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "whospeak")
    private String whospeak;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "msg")
    private String msg;
    @JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Message() {
    }

    public Message(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public Message(MessagePK messagePK, String whospeak, String msg) {
        this.messagePK = messagePK;
        this.whospeak = whospeak;
        this.msg = msg;
    }

    public Message(String userid, String talkTo, Date datetime) {
        this.messagePK = new MessagePK(userid, talkTo, datetime);
    }

    public MessagePK getMessagePK() {
        return messagePK;
    }

    public void setMessagePK(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public String getWhospeak() {
        return whospeak;
    }

    public void setWhospeak(String whospeak) {
        this.whospeak = whospeak;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messagePK != null ? messagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messagePK == null && other.messagePK != null) || (this.messagePK != null && !this.messagePK.equals(other.messagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Message[ messagePK=" + messagePK + " ]";
    }
    
}
