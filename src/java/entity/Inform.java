/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "inform")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inform.findAll", query = "SELECT i FROM Inform i")
    , @NamedQuery(name = "Inform.findByUserid", query = "SELECT i FROM Inform i WHERE i.informPK.userid = :userid")
    , @NamedQuery(name = "Inform.findByTalkTo", query = "SELECT i FROM Inform i WHERE i.informPK.talkTo = :talkTo")
    , @NamedQuery(name = "Inform.findByNewMessage", query = "SELECT i FROM Inform i WHERE i.newMessage = :newMessage")})
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InformPK informPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "newMessage")
    private short newMessage;
    @JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Inform() {
    }

    public Inform(InformPK informPK) {
        this.informPK = informPK;
    }

    public Inform(InformPK informPK, short newMessage) {
        this.informPK = informPK;
        this.newMessage = newMessage;
    }

    public Inform(String userid, String talkTo) {
        this.informPK = new InformPK(userid, talkTo);
    }

    public InformPK getInformPK() {
        return informPK;
    }

    public void setInformPK(InformPK informPK) {
        this.informPK = informPK;
    }

    public short getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(short newMessage) {
        this.newMessage = newMessage;
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
        hash += (informPK != null ? informPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inform)) {
            return false;
        }
        Inform other = (Inform) object;
        if ((this.informPK == null && other.informPK != null) || (this.informPK != null && !this.informPK.equals(other.informPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Inform[ informPK=" + informPK + " ]";
    }
    
}
