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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dawson
 */
@Entity
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q")
    , @NamedQuery(name = "Question.findBySequenceNumber", query = "SELECT q FROM Question q WHERE q.questionPK.sequenceNumber = :sequenceNumber")
    , @NamedQuery(name = "Question.findByChapterCourseSchoolId", query = "SELECT q FROM Question q WHERE q.questionPK.chapterCourseSchoolId = :chapterCourseSchoolId")
    , @NamedQuery(name = "Question.findByChapterCourseId", query = "SELECT q FROM Question q WHERE q.questionPK.chapterCourseId = :chapterCourseId")
    , @NamedQuery(name = "Question.findByChapterCourseStartDate", query = "SELECT q FROM Question q WHERE q.questionPK.chapterCourseStartDate = :chapterCourseStartDate")
    , @NamedQuery(name = "Question.findByChapterCourseEndDate", query = "SELECT q FROM Question q WHERE q.questionPK.chapterCourseEndDate = :chapterCourseEndDate")
    , @NamedQuery(name = "Question.findByChapterSequenceNumber", query = "SELECT q FROM Question q WHERE q.questionPK.chapterSequenceNumber = :chapterSequenceNumber")
    , @NamedQuery(name = "Question.findByTitle", query = "SELECT q FROM Question q WHERE q.title = :title")
    , @NamedQuery(name = "Question.findByA", query = "SELECT q FROM Question q WHERE q.a = :a")
    , @NamedQuery(name = "Question.findByB", query = "SELECT q FROM Question q WHERE q.b = :b")
    , @NamedQuery(name = "Question.findByC", query = "SELECT q FROM Question q WHERE q.c = :c")
    , @NamedQuery(name = "Question.findByD", query = "SELECT q FROM Question q WHERE q.d = :d")
    , @NamedQuery(name = "Question.findByAnswer", query = "SELECT q FROM Question q WHERE q.answer = :answer")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QuestionPK questionPK;
    @Size(max = 450)
    @Column(name = "title")
    private String title;
    @Size(max = 450)
    @Column(name = "a")
    private String a;
    @Size(max = 450)
    @Column(name = "b")
    private String b;
    @Size(max = 450)
    @Column(name = "c")
    private String c;
    @Size(max = 450)
    @Column(name = "d")
    private String d;
    @Size(max = 45)
    @Column(name = "answer")
    private String answer;
    @JoinColumns({
        @JoinColumn(name = "chapter_course_school_id", referencedColumnName = "course_school_id", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_start_date", referencedColumnName = "course_start_date", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_course_end_date", referencedColumnName = "course_end_date", insertable = false, updatable = false)
        , @JoinColumn(name = "chapter_sequence_number", referencedColumnName = "sequence_number", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Chapter chapter;

    public Question() {
    }

    public Question(QuestionPK questionPK) {
        this.questionPK = questionPK;
    }

    public Question(int sequenceNumber, int chapterCourseSchoolId, int chapterCourseId, Date chapterCourseStartDate, Date chapterCourseEndDate, int chapterSequenceNumber) {
        this.questionPK = new QuestionPK(sequenceNumber, chapterCourseSchoolId, chapterCourseId, chapterCourseStartDate, chapterCourseEndDate, chapterSequenceNumber);
    }

    public QuestionPK getQuestionPK() {
        return questionPK;
    }

    public void setQuestionPK(QuestionPK questionPK) {
        this.questionPK = questionPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
        hash += (questionPK != null ? questionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionPK == null && other.questionPK != null) || (this.questionPK != null && !this.questionPK.equals(other.questionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Question[ questionPK=" + questionPK + " ]";
    }
    
}
