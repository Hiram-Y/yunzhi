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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dawson
 */
@Embeddable
public class ResumePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "school_id")
    private int schoolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "identity_name")
    private String identityName;

    public ResumePK() {
    }

    public ResumePK(Date startDate, Date endDate, String userId, int schoolId, String identityName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.schoolId = schoolId;
        this.identityName = identityName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (startDate != null ? startDate.hashCode() : 0);
        hash += (endDate != null ? endDate.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (int) schoolId;
        hash += (identityName != null ? identityName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResumePK)) {
            return false;
        }
        ResumePK other = (ResumePK) object;
        if ((this.startDate == null && other.startDate != null) || (this.startDate != null && !this.startDate.equals(other.startDate))) {
            return false;
        }
        if ((this.endDate == null && other.endDate != null) || (this.endDate != null && !this.endDate.equals(other.endDate))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        if (this.schoolId != other.schoolId) {
            return false;
        }
        if ((this.identityName == null && other.identityName != null) || (this.identityName != null && !this.identityName.equals(other.identityName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ResumePK[ startDate=" + startDate + ", endDate=" + endDate + ", userId=" + userId + ", schoolId=" + schoolId + ", identityName=" + identityName + " ]";
    }
    
}
