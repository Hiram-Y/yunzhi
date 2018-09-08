/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "resume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resume.findAll", query = "SELECT r FROM Resume r")
    , @NamedQuery(name = "Resume.findByStartDate", query = "SELECT r FROM Resume r WHERE r.resumePK.startDate = :startDate")
    , @NamedQuery(name = "Resume.findByEndDate", query = "SELECT r FROM Resume r WHERE r.resumePK.endDate = :endDate")
    , @NamedQuery(name = "Resume.findByUserId", query = "SELECT r FROM Resume r WHERE r.resumePK.userId = :userId")
    , @NamedQuery(name = "Resume.findBySchoolId", query = "SELECT r FROM Resume r WHERE r.resumePK.schoolId = :schoolId")
    , @NamedQuery(name = "Resume.findByIdentityName", query = "SELECT r FROM Resume r WHERE r.resumePK.identityName = :identityName")})
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResumePK resumePK;
    @JoinColumn(name = "identity_name", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Identity identity;
    @JoinColumn(name = "school_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private School school;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Resume() {
    }

    public Resume(ResumePK resumePK) {
        this.resumePK = resumePK;
    }

    public Resume(Date startDate, Date endDate, String userId, int schoolId, String identityName) {
        this.resumePK = new ResumePK(startDate, endDate, userId, schoolId, identityName);
    }

    public ResumePK getResumePK() {
        return resumePK;
    }

    public void setResumePK(ResumePK resumePK) {
        this.resumePK = resumePK;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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
        hash += (resumePK != null ? resumePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resume)) {
            return false;
        }
        Resume other = (Resume) object;
        if ((this.resumePK == null && other.resumePK != null) || (this.resumePK != null && !this.resumePK.equals(other.resumePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Resume[ resumePK=" + resumePK + " ]";
    }
    
}
