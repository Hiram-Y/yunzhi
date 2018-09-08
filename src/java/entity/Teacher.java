/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "teacher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t")
    , @NamedQuery(name = "Teacher.findByUserId", query = "SELECT t FROM Teacher t WHERE t.teacherPK.userId = :userId")
    , @NamedQuery(name = "Teacher.findByCourseSchoolId", query = "SELECT t FROM Teacher t WHERE t.teacherPK.courseSchoolId = :courseSchoolId")
    , @NamedQuery(name = "Teacher.findByCourseId", query = "SELECT t FROM Teacher t WHERE t.teacherPK.courseId = :courseId")
    , @NamedQuery(name = "Teacher.findByCourseStartDate", query = "SELECT t FROM Teacher t WHERE t.teacherPK.courseStartDate = :courseStartDate")
    , @NamedQuery(name = "Teacher.findByCourseEndDate", query = "SELECT t FROM Teacher t WHERE t.teacherPK.courseEndDate = :courseEndDate")
    , @NamedQuery(name = "Teacher.findByMark", query = "SELECT t FROM Teacher t WHERE t.mark = :mark")})
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TeacherPK teacherPK;
    @Column(name = "mark")
    private Integer mark;
    @JoinColumns({
        @JoinColumn(name = "course_school_id", referencedColumnName = "school_id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_start_date", referencedColumnName = "start_date", insertable = false, updatable = false)
        , @JoinColumn(name = "course_end_date", referencedColumnName = "end_date", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Teacher() {
    }

    public Teacher(TeacherPK teacherPK) {
        this.teacherPK = teacherPK;
    }

    public Teacher(String userId, int courseSchoolId, int courseId, Date courseStartDate, Date courseEndDate) {
        this.teacherPK = new TeacherPK(userId, courseSchoolId, courseId, courseStartDate, courseEndDate);
    }

    public TeacherPK getTeacherPK() {
        return teacherPK;
    }

    public void setTeacherPK(TeacherPK teacherPK) {
        this.teacherPK = teacherPK;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        hash += (teacherPK != null ? teacherPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.teacherPK == null && other.teacherPK != null) || (this.teacherPK != null && !this.teacherPK.equals(other.teacherPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Teacher[ teacherPK=" + teacherPK + " ]";
    }
    
}
