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
import javax.persistence.Lob;
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
@Table(name = "vedio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vedio.findAll", query = "SELECT v FROM Vedio v")
    , @NamedQuery(name = "Vedio.findBySequenceNumber", query = "SELECT v FROM Vedio v WHERE v.vedioPK.sequenceNumber = :sequenceNumber")
    , @NamedQuery(name = "Vedio.findByChapterCourseSchoolId", query = "SELECT v FROM Vedio v WHERE v.vedioPK.chapterCourseSchoolId = :chapterCourseSchoolId")
    , @NamedQuery(name = "Vedio.findByChapterCourseId", query = "SELECT v FROM Vedio v WHERE v.vedioPK.chapterCourseId = :chapterCourseId")
    , @NamedQuery(name = "Vedio.findByChapterCourseStartDate", query = "SELECT v FROM Vedio v WHERE v.vedioPK.chapterCourseStartDate = :chapterCourseStartDate")
    , @NamedQuery(name = "Vedio.findByChapterCourseEndDate", query = "SELECT v FROM Vedio v WHERE v.vedioPK.chapterCourseEndDate = :chapterCourseEndDate")
    , @NamedQuery(name = "Vedio.findByChapterSequenceNumber", query = "SELECT v FROM Vedio v WHERE v.vedioPK.chapterSequenceNumber = :chapterSequenceNumber")
    , @NamedQuery(name = "Vedio.findByName", query = "SELECT v FROM Vedio v WHERE v.name = :name")})
public class Vedio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VedioPK vedioPK;
    @Size(max = 450)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 16777215)
    @Column(name = "url")
    private String url;
    @JoinColumns({
        @JoinColumn(name = "chapter_course_school_id", referencedColumnName = "course_school_id", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_start_date", referencedColumnName = "course_start_date", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_end_date", referencedColumnName = "course_end_date", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_sequence_number", referencedColumnName = "sequence_number", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Chapter chapter;

    public Vedio() {
    }

    public Vedio(VedioPK vedioPK) {
        this.vedioPK = vedioPK;
    }

    public Vedio(int sequenceNumber, int chapterCourseSchoolId, int chapterCourseId, Date chapterCourseStartDate, Date chapterCourseEndDate, int chapterSequenceNumber) {
        this.vedioPK = new VedioPK(sequenceNumber, chapterCourseSchoolId, chapterCourseId, chapterCourseStartDate, chapterCourseEndDate, chapterSequenceNumber);
    }

    public VedioPK getVedioPK() {
        return vedioPK;
    }

    public void setVedioPK(VedioPK vedioPK) {
        this.vedioPK = vedioPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vedioPK != null ? vedioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vedio)) {
            return false;
        }
        Vedio other = (Vedio) object;
        if ((this.vedioPK == null && other.vedioPK != null) || (this.vedioPK != null && !this.vedioPK.equals(other.vedioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Vedio[ vedioPK=" + vedioPK + " ]";
    }
    
}
