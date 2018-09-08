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
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findByUserId", query = "SELECT s FROM Student s WHERE s.studentPK.userId = :userId")
    , @NamedQuery(name = "Student.findByCourseSchoolId", query = "SELECT s FROM Student s WHERE s.studentPK.courseSchoolId = :courseSchoolId")
    , @NamedQuery(name = "Student.findByCourseId", query = "SELECT s FROM Student s WHERE s.studentPK.courseId = :courseId")
    , @NamedQuery(name = "Student.findByCourseStartDate", query = "SELECT s FROM Student s WHERE s.studentPK.courseStartDate = :courseStartDate")
    , @NamedQuery(name = "Student.findByCourseEndDate", query = "SELECT s FROM Student s WHERE s.studentPK.courseEndDate = :courseEndDate")
    , @NamedQuery(name = "Student.findByMark", query = "SELECT s FROM Student s WHERE s.mark = :mark")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentPK studentPK;
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

    public Student() {
    }

    public Student(StudentPK studentPK) {
        this.studentPK = studentPK;
    }

    public Student(String userId, int courseSchoolId, int courseId, Date courseStartDate, Date courseEndDate) {
        this.studentPK = new StudentPK(userId, courseSchoolId, courseId, courseStartDate, courseEndDate);
    }

    public StudentPK getStudentPK() {
        return studentPK;
    }

    public void setStudentPK(StudentPK studentPK) {
        this.studentPK = studentPK;
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
        hash += (studentPK != null ? studentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentPK == null && other.studentPK != null) || (this.studentPK != null && !this.studentPK.equals(other.studentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ studentPK=" + studentPK + " ]";
    }
    
}
