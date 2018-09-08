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
public class FriendPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userId1")
    private String userId1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userId2")
    private String userId2;

    public FriendPK() {
    }

    public FriendPK(String userId1, String userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId1 != null ? userId1.hashCode() : 0);
        hash += (userId2 != null ? userId2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendPK)) {
            return false;
        }
        FriendPK other = (FriendPK) object;
        if ((this.userId1 == null && other.userId1 != null) || (this.userId1 != null && !this.userId1.equals(other.userId1))) {
            return false;
        }
        if ((this.userId2 == null && other.userId2 != null) || (this.userId2 != null && !this.userId2.equals(other.userId2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FriendPK[ userId1=" + userId1 + ", userId2=" + userId2 + " ]";
    }
    
}
