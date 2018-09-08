/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
    , @NamedQuery(name = "Course.findBySchoolId", query = "SELECT c FROM Course c WHERE c.coursePK.schoolId = :schoolId")
    , @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.coursePK.id = :id")
    , @NamedQuery(name = "Course.findByStartDate", query = "SELECT c FROM Course c WHERE c.coursePK.startDate = :startDate")
    , @NamedQuery(name = "Course.findByEndDate", query = "SELECT c FROM Course c WHERE c.coursePK.endDate = :endDate")
    , @NamedQuery(name = "Course.findByName", query = "SELECT c FROM Course c WHERE c.name = :name")
    , @NamedQuery(name = "Course.findByCost", query = "SELECT c FROM Course c WHERE c.cost = :cost")
    , @NamedQuery(name = "Course.findByLogo", query = "SELECT c FROM Course c WHERE c.logo = :logo")})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CoursePK coursePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cost")
    private String cost;
    @Size(max = 450)
    @Column(name = "logo")
    private String logo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Chapter> chapterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Student> studentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Teacher> teacherCollection;
    @JoinColumn(name = "school_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private School school;
    @JoinColumn(name = "liveroom_id", referencedColumnName = "id")
    @ManyToOne
    private Liveroom liveroomId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Notice> noticeCollection;

    public Course() {
    }

    public Course(CoursePK coursePK) {
        this.coursePK = coursePK;
    }

    public Course(CoursePK coursePK, String name, String cost) {
        this.coursePK = coursePK;
        this.name = name;
        this.cost = cost;
    }

    public Course(int schoolId, int id, Date startDate, Date endDate) {
        this.coursePK = new CoursePK(schoolId, id, startDate, endDate);
    }

    public CoursePK getCoursePK() {
        return coursePK;
    }

    public void setCoursePK(CoursePK coursePK) {
        this.coursePK = coursePK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @XmlTransient
    public Collection<Chapter> getChapterCollection() {
        return chapterCollection;
    }

    public void setChapterCollection(Collection<Chapter> chapterCollection) {
        this.chapterCollection = chapterCollection;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @XmlTransient
    public Collection<Teacher> getTeacherCollection() {
        return teacherCollection;
    }

    public void setTeacherCollection(Collection<Teacher> teacherCollection) {
        this.teacherCollection = teacherCollection;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Liveroom getLiveroomId() {
        return liveroomId;
    }

    public void setLiveroomId(Liveroom liveroomId) {
        this.liveroomId = liveroomId;
    }

    @XmlTransient
    public Collection<Notice> getNoticeCollection() {
        return noticeCollection;
    }

    public void setNoticeCollection(Collection<Notice> noticeCollection) {
        this.noticeCollection = noticeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coursePK != null ? coursePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.coursePK == null && other.coursePK != null) || (this.coursePK != null && !this.coursePK.equals(other.coursePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Course[ coursePK=" + coursePK + " ]";
    }
    
}
