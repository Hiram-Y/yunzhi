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
public class StudentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "course_school_id")
    private int courseSchoolId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "course_id")
    private int courseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "course_start_date")
    @Temporal(TemporalType.DATE)
    private Date courseStartDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "course_end_date")
    @Temporal(TemporalType.DATE)
    private Date courseEndDate;

    public StudentPK() {
    }

    public StudentPK(String userId, int courseSchoolId, int courseId, Date courseStartDate, Date courseEndDate) {
        this.userId = userId;
        this.courseSchoolId = courseSchoolId;
        this.courseId = courseId;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCourseSchoolId() {
        return courseSchoolId;
    }

    public void setCourseSchoolId(int courseSchoolId) {
        this.courseSchoolId = courseSchoolId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (int) courseSchoolId;
        hash += (int) courseId;
        hash += (courseStartDate != null ? courseStartDate.hashCode() : 0);
        hash += (courseEndDate != null ? courseEndDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentPK)) {
            return false;
        }
        StudentPK other = (StudentPK) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        if (this.courseSchoolId != other.courseSchoolId) {
            return false;
        }
        if (this.courseId != other.courseId) {
            return false;
        }
        if ((this.courseStartDate == null && other.courseStartDate != null) || (this.courseStartDate != null && !this.courseStartDate.equals(other.courseStartDate))) {
            return false;
        }
        if ((this.courseEndDate == null && other.courseEndDate != null) || (this.courseEndDate != null && !this.courseEndDate.equals(other.courseEndDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StudentPK[ userId=" + userId + ", courseSchoolId=" + courseSchoolId + ", courseId=" + courseId + ", courseStartDate=" + courseStartDate + ", courseEndDate=" + courseEndDate + " ]";
    }
    
}
