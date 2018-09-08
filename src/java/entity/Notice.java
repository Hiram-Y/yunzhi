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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "notice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notice.findAll", query = "SELECT n FROM Notice n")
    , @NamedQuery(name = "Notice.findByCourseSchoolId", query = "SELECT n FROM Notice n WHERE n.noticePK.courseSchoolId = :courseSchoolId")
    , @NamedQuery(name = "Notice.findByCourseId", query = "SELECT n FROM Notice n WHERE n.noticePK.courseId = :courseId")
    , @NamedQuery(name = "Notice.findByCourseStartDate", query = "SELECT n FROM Notice n WHERE n.noticePK.courseStartDate = :courseStartDate")
    , @NamedQuery(name = "Notice.findByCourseEndDate", query = "SELECT n FROM Notice n WHERE n.noticePK.courseEndDate = :courseEndDate")
    , @NamedQuery(name = "Notice.findByPublishDatetime", query = "SELECT n FROM Notice n WHERE n.noticePK.publishDatetime = :publishDatetime")
    , @NamedQuery(name = "Notice.findByTitle", query = "SELECT n FROM Notice n WHERE n.title = :title")
    , @NamedQuery(name = "Notice.findByUploadFileUrl", query = "SELECT n FROM Notice n WHERE n.uploadFileUrl = :uploadFileUrl")})
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NoticePK noticePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "body")
    private String body;
    @Size(max = 450)
    @Column(name = "upload_file_url")
    private String uploadFileUrl;
    @JoinColumns({
        @JoinColumn(name = "course_school_id", referencedColumnName = "school_id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "course_start_date", referencedColumnName = "start_date", insertable = false, updatable = false)
        , @JoinColumn(name = "course_end_date", referencedColumnName = "end_date", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Course course;

    public Notice() {
    }

    public Notice(NoticePK noticePK) {
        this.noticePK = noticePK;
    }

    public Notice(NoticePK noticePK, String title, String body) {
        this.noticePK = noticePK;
        this.title = title;
        this.body = body;
    }

    public Notice(int courseSchoolId, int courseId, Date courseStartDate, Date courseEndDate, Date publishDatetime) {
        this.noticePK = new NoticePK(courseSchoolId, courseId, courseStartDate, courseEndDate, publishDatetime);
    }

    public NoticePK getNoticePK() {
        return noticePK;
    }

    public void setNoticePK(NoticePK noticePK) {
        this.noticePK = noticePK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUploadFileUrl() {
        return uploadFileUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        this.uploadFileUrl = uploadFileUrl;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noticePK != null ? noticePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notice)) {
            return false;
        }
        Notice other = (Notice) object;
        if ((this.noticePK == null && other.noticePK != null) || (this.noticePK != null && !this.noticePK.equals(other.noticePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Notice[ noticePK=" + noticePK + " ]";
    }
    
}
