/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "liveroom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liveroom.findAll", query = "SELECT l FROM Liveroom l")
    , @NamedQuery(name = "Liveroom.findById", query = "SELECT l FROM Liveroom l WHERE l.id = :id")
    , @NamedQuery(name = "Liveroom.findByRtmp", query = "SELECT l FROM Liveroom l WHERE l.rtmp = :rtmp")
    , @NamedQuery(name = "Liveroom.findByStream", query = "SELECT l FROM Liveroom l WHERE l.stream = :stream")
    , @NamedQuery(name = "Liveroom.findBySrc", query = "SELECT l FROM Liveroom l WHERE l.src = :src")
    , @NamedQuery(name = "Liveroom.findByTitle", query = "SELECT l FROM Liveroom l WHERE l.title = :title")})
public class Liveroom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "rtmp")
    private String rtmp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "stream")
    private String stream;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "src")
    private String src;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "liveroomId")
    private Collection<Course> courseCollection;

    public Liveroom() {
    }

    public Liveroom(String id) {
        this.id = id;
    }

    public Liveroom(String id, String rtmp, String stream, String src, String title) {
        this.id = id;
        this.rtmp = rtmp;
        this.stream = stream;
        this.src = src;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liveroom)) {
            return false;
        }
        Liveroom other = (Liveroom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Liveroom[ id=" + id + " ]";
    }
    
}
