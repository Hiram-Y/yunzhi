/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dawson
 */
@Embeddable
public class InformPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userid")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "talkTo")
    private String talkTo;

    public InformPK() {
    }

    public InformPK(String userid, String talkTo) {
        this.userid = userid;
        this.talkTo = talkTo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTalkTo() {
        return talkTo;
    }

    public void setTalkTo(String talkTo) {
        this.talkTo = talkTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        hash += (talkTo != null ? talkTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformPK)) {
            return false;
        }
        InformPK other = (InformPK) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        if ((this.talkTo == null && other.talkTo != null) || (this.talkTo != null && !this.talkTo.equals(other.talkTo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InformPK[ userid=" + userid + ", talkTo=" + talkTo + " ]";
    }
    
}
