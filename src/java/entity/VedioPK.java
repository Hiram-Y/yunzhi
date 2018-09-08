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
public class VedioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "sequence_number")
    private int sequenceNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chapter_course_school_id")
    private int chapterCourseSchoolId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chapter_course_id")
    private int chapterCourseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chapter_course_start_date")
    @Temporal(TemporalType.DATE)
    private Date chapterCourseStartDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chapter_course_end_date")
    @Temporal(TemporalType.DATE)
    private Date chapterCourseEndDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chapter_sequence_number")
    private int chapterSequenceNumber;

    public VedioPK() {
    }

    public VedioPK(int sequenceNumber, int chapterCourseSchoolId, int chapterCourseId, Date chapterCourseStartDate, Date chapterCourseEndDate, int chapterSequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        this.chapterCourseSchoolId = chapterCourseSchoolId;
        this.chapterCourseId = chapterCourseId;
        this.chapterCourseStartDate = chapterCourseStartDate;
        this.chapterCourseEndDate = chapterCourseEndDate;
        this.chapterSequenceNumber = chapterSequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getChapterCourseSchoolId() {
        return chapterCourseSchoolId;
    }

    public void setChapterCourseSchoolId(int chapterCourseSchoolId) {
        this.chapterCourseSchoolId = chapterCourseSchoolId;
    }

    public int getChapterCourseId() {
        return chapterCourseId;
    }

    public void setChapterCourseId(int chapterCourseId) {
        this.chapterCourseId = chapterCourseId;
    }

    public Date getChapterCourseStartDate() {
        return chapterCourseStartDate;
    }

    public void setChapterCourseStartDate(Date chapterCourseStartDate) {
        this.chapterCourseStartDate = chapterCourseStartDate;
    }

    public Date getChapterCourseEndDate() {
        return chapterCourseEndDate;
    }

    public void setChapterCourseEndDate(Date chapterCourseEndDate) {
        this.chapterCourseEndDate = chapterCourseEndDate;
    }

    public int getChapterSequenceNumber() {
        return chapterSequenceNumber;
    }

    public void setChapterSequenceNumber(int chapterSequenceNumber) {
        this.chapterSequenceNumber = chapterSequenceNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) sequenceNumber;
        hash += (int) chapterCourseSchoolId;
        hash += (int) chapterCourseId;
        hash += (chapterCourseStartDate != null ? chapterCourseStartDate.hashCode() : 0);
        hash += (chapterCourseEndDate != null ? chapterCourseEndDate.hashCode() : 0);
        hash += (int) chapterSequenceNumber;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VedioPK)) {
            return false;
        }
        VedioPK other = (VedioPK) object;
        if (this.sequenceNumber != other.sequenceNumber) {
            return false;
        }
        if (this.chapterCourseSchoolId != other.chapterCourseSchoolId) {
            return false;
        }
        if (this.chapterCourseId != other.chapterCourseId) {
            return false;
        }
        if ((this.chapterCourseStartDate == null && other.chapterCourseStartDate != null) || (this.chapterCourseStartDate != null && !this.chapterCourseStartDate.equals(other.chapterCourseStartDate))) {
            return false;
        }
        if ((this.chapterCourseEndDate == null && other.chapterCourseEndDate != null) || (this.chapterCourseEndDate != null && !this.chapterCourseEndDate.equals(other.chapterCourseEndDate))) {
            return false;
        }
        if (this.chapterSequenceNumber != other.chapterSequenceNumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VedioPK[ sequenceNumber=" + sequenceNumber + ", chapterCourseSchoolId=" + chapterCourseSchoolId + ", chapterCourseId=" + chapterCourseId + ", chapterCourseStartDate=" + chapterCourseStartDate + ", chapterCourseEndDate=" + chapterCourseEndDate + ", chapterSequenceNumber=" + chapterSequenceNumber + " ]";
    }
    
}
