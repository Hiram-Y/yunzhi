/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "friend")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friend.findAll", query = "SELECT f FROM Friend f")
    , @NamedQuery(name = "Friend.findByUserId1", query = "SELECT f FROM Friend f WHERE f.friendPK.userId1 = :userId1")
    , @NamedQuery(name = "Friend.findByUserId2", query = "SELECT f FROM Friend f WHERE f.friendPK.userId2 = :userId2")
    , @NamedQuery(name = "Friend.findByFriendShip", query = "SELECT f FROM Friend f WHERE f.friendShip = :friendShip")})
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FriendPK friendPK;
    @Size(max = 45)
    @Column(name = "friendShip")
    private String friendShip;
    @JoinColumn(name = "userId1", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "userId2", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;

    public Friend() {
    }

    public Friend(FriendPK friendPK) {
        this.friendPK = friendPK;
    }

    public Friend(String userId1, String userId2) {
        this.friendPK = new FriendPK(userId1, userId2);
    }

    public FriendPK getFriendPK() {
        return friendPK;
    }

    public void setFriendPK(FriendPK friendPK) {
        this.friendPK = friendPK;
    }

    public String getFriendShip() {
        return friendShip;
    }

    public void setFriendShip(String friendShip) {
        this.friendShip = friendShip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendPK != null ? friendPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friend)) {
            return false;
        }
        Friend other = (Friend) object;
        if ((this.friendPK == null && other.friendPK != null) || (this.friendPK != null && !this.friendPK.equals(other.friendPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Friend[ friendPK=" + friendPK + " ]";
    }
    
}
