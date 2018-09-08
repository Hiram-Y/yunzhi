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

/**
 *
 * @author Dawson
 */
@Embeddable
public class CoursePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "school_id")
    private int schoolId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
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

    public CoursePK() {
    }

    public CoursePK(int schoolId, int id, Date startDate, Date endDate) {
        this.schoolId = schoolId;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) schoolId;
        hash += (int) id;
        hash += (startDate != null ? startDate.hashCode() : 0);
        hash += (endDate != null ? endDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoursePK)) {
            return false;
        }
        CoursePK other = (CoursePK) object;
        if (this.schoolId != other.schoolId) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if ((this.startDate == null && other.startDate != null) || (this.startDate != null && !this.startDate.equals(other.startDate))) {
            return false;
        }
        if ((this.endDate == null && other.endDate != null) || (this.endDate != null && !this.endDate.equals(other.endDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CoursePK[ schoolId=" + schoolId + ", id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + " ]";
    }
    
}
