/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "chapter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chapter.findAll", query = "SELECT c FROM Chapter c")
    , @NamedQuery(name = "Chapter.findByCourseSchoolId", query = "SELECT c FROM Chapter c WHERE c.chapterPK.courseSchoolId = :courseSchoolId")
    , @NamedQuery(name = "Chapter.findByCourseId", query = "SELECT c FROM Chapter c WHERE c.chapterPK.courseId = :courseId")
    , @NamedQuery(name = "Chapter.findByCourseStartDate", query = "SELECT c FROM Chapter c WHERE c.chapterPK.courseStartDate = :courseStartDate")
    , @NamedQuery(name = "Chapter.findByCourseEndDate", query = "SELECT c FROM Chapter c WHERE c.chapterPK.courseEndDate = :courseEndDate")
    , @NamedQuery(name = "Chapter.findBySequenceNumber", query = "SELECT c FROM Chapter c WHERE c.chapterPK.sequenceNumber = :sequenceNumber")
    , @NamedQuery(name = "Chapter.findByName", query = "SELECT c FROM Chapter c WHERE c.name = :name")})
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChapterPK chapterPK;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @JoinColumns({
        @JoinColumn(name = "course_school_id", referencedColumnName = "school_id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_start_date", referencedColumnName = "start_date", insertable = false, updatable = false)
        , @JoinColumn(name = "course_end_date", referencedColumnName = "end_date", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private Collection<Question> questionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private Collection<Vedio> vedioCollection;

    public Chapter() {
    }

    public Chapter(ChapterPK chapterPK) {
        this.chapterPK = chapterPK;
    }

    public Chapter(int courseSchoolId, int courseId, Date courseStartDate, Date courseEndDate, int sequenceNumber) {
        this.chapterPK = new ChapterPK(courseSchoolId, courseId, courseStartDate, courseEndDate, sequenceNumber);
    }

    public ChapterPK getChapterPK() {
        return chapterPK;
    }

    public void setChapterPK(ChapterPK chapterPK) {
        this.chapterPK = chapterPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @XmlTransient
    public Collection<Vedio> getVedioCollection() {
        return vedioCollection;
    }

    public void setVedioCollection(Collection<Vedio> vedioCollection) {
        this.vedioCollection = vedioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chapterPK != null ? chapterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chapter)) {
            return false;
        }
        Chapter other = (Chapter) object;
        if ((this.chapterPK == null && other.chapterPK != null) || (this.chapterPK != null && !this.chapterPK.equals(other.chapterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Chapter[ chapterPK=" + chapterPK + " ]";
    }
    
}
