/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Chapter;
import entity.ChapterPK;
import entity.Course;
import entity.Liveroom;
import entity.Teacher;
import entity.User;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "classroomStudyCenterController")
@ViewScoped
public class ClassroomStudyCenterController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    private Course targetCourse = new Course();
    private List<Chapter> targetChapterList = new ArrayList();
    private String showTeacherOptionDiv = "none";

    private Chapter chapter = new Chapter();
    private ChapterPK chapterPK = new ChapterPK();
    private int sequence_number;



    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    //=============================依赖注入部分=================================
    @ManagedProperty("#{userBean}")
    private UserBean userBean;
    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;

    //非常重要，这是实现依赖注入的关键@ManagedProperty("#{userBean}")
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    /**
     * Creates a new instance of ClassroomStudyCenterController
     */
    public ClassroomStudyCenterController() {
        System.out.println(this.getTimeNow() + "[ClassroomStudyCenterController()]===构造函数");

    }

    //因为在构造函数调用的时候，类还没有完成初始化，无法注入依赖项,也就是无法使用该类的属性
    @PostConstruct
    public void init() {
        this.targetCourse = userBean.getXhtmlTargetCourse();//从@ManagedProperty("#{userBean}")传入选择的课程
        if (targetCourse.getSchool() != null) {
            for (Chapter cpt : facadeBean.getChapterFacade().findAll()) {
                System.out.println(this.facadeBean.getTimeNow() + "[ClassroomStudyCenterController.init()]" + "chapter====" + cpt + "===chapter.PK" + cpt.getChapterPK());
                if (cpt.getChapterPK().getCourseSchoolId() == targetCourse.getSchool().getId()
                        && cpt.getCourse().equals(targetCourse)) {
                    this.targetChapterList.add(cpt);
                }
            }
            if (this.isUserATeacherOfTargetCourse(this.userBean.getUser(), targetCourse)) {
                showTeacherOptionDiv = "";
            }
        }
        System.out.println(this.facadeBean.getTimeNow() + "[ClassroomStudyCenterController.init()]" + "this.targetCourse====" + this.targetCourse);
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public void startLiveroom() {
//        if (this.userBean.getSelectResume()!=null 
//                && "teacher".equals(this.userBean.getSelectResume().getIdentity().getName())) {
        if (targetCourse != null
                && this.userBean.getUser().getId() != null
                && this.isUserATeacherOfTargetCourse(this.userBean.getUser(), targetCourse)) {
            //如果targetCourse还没有直播间，那就为它创建新的直播间
            if (targetCourse.getLiveroomId() == null) {
                //随机生成8位的直播间id
                int i = 0;
                String id = "";
                while (i < 10) {
                    id = this.facadeBean.GernaratePWD(FacadeBean.PasswordType.Mixed, 8);
                    if (this.facadeBean.getLiveroomFacade().find(id) == null) {
                        break;
                    }
                    i++;
                }
                //随机生成16位的直播流码
                String stream = this.facadeBean.GernaratePWD(FacadeBean.PasswordType.Mixed, 16);
                if (!stream.equals("") && !id.equals("")) {
                    Liveroom liveroom = new Liveroom();
                    liveroom.setId(id);
                    liveroom.setTitle(targetCourse.getName() + "的直播间");
                    liveroom.setRtmp("rtmp://demo.srs.com/live");
                    liveroom.setStream(stream);
                    String src = "http://10.206.11.101:8888/live/" + stream + ".m3u8";
                    liveroom.setSrc(src);
                    try {
                        this.facadeBean.getLiveroomFacade().create(liveroom);
                        targetCourse.setLiveroomId(liveroom);
                        this.facadeBean.getCourseFacade().edit(targetCourse);
                        //更新数据中心UserBean中的XhtmlTargetCourse的数据和直播地址数据
                        this.userBean.setXhtmlTargetCourse(targetCourse);
                    } catch (Exception e) {
                        FacesMessage msg = new FacesMessage("创建直播间失败，请联系系统管理员", "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }
        }
    }

    private boolean isUserATeacherOfTargetCourse(User user, Course course) {
        boolean flag = false;
        for (Teacher t : this.facadeBean.getTeacherFacade().findAll()) {
            if (course.equals(t.getCourse()) && user.equals(t.getUser())) {
                flag = true;
            }
        }
        return flag;
    }

    public String newChapter() {
        for (Chapter c : this.targetChapterList) {
            if (c.getChapterPK().getSequenceNumber() == this.sequence_number) {
                FacesMessage msg = new FacesMessage("序号已经存在，请换一个序号试试", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
        }
        if (this.chapter != null
                && this.chapterPK != null) {
            this.chapterPK.setSequenceNumber(sequence_number);
            this.chapterPK.setCourseStartDate(this.targetCourse.getCoursePK().getStartDate());
            this.chapterPK.setCourseEndDate(this.targetCourse.getCoursePK().getEndDate());
            this.chapterPK.setCourseSchoolId(this.targetCourse.getCoursePK().getSchoolId());
            this.chapterPK.setCourseId(this.targetCourse.getCoursePK().getId());
            this.chapter.setChapterPK(chapterPK);
            this.facadeBean.getChapterFacade().create(chapter);
            this.targetChapterList.add(chapter);
            this.quickSort(targetChapterList, 0, targetChapterList.size() - 1);
            FacesMessage msg = new FacesMessage("添加成功", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("添加失败", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }

    private void quickSort(List<Chapter> list, int low, int high) {// 传入low=0，high=array.length-1;
        int pivot, p_pos, i;// pivot->位索引;p_pos->轴值。
        Chapter t;
        if (low < high) {
            p_pos = low;
            pivot = list.get(p_pos).getChapterPK().getSequenceNumber();
            for (i = low + 1; i <= high; i++) {
                if (list.get(i).getChapterPK().getSequenceNumber() < pivot) {
                    p_pos++;
                    t = list.get(p_pos);
                    list.set(p_pos, list.get(i));
                    list.set(i, t);
                }
            }
            t = list.get(low);
            list.set(low, list.get(p_pos));
            list.set(p_pos, t);
            // 分而治之
            quickSort(list, low, p_pos - 1);// 排序左半部分
            quickSort(list, p_pos + 1, high);// 排序右半部分
        }

    }

    public void newLiveroomTitle() {
        if (this.targetCourse != null && this.targetCourse.getLiveroomId() != null) {
            this.facadeBean.getLiveroomFacade().edit(this.targetCourse.getLiveroomId());
            this.userBean.setXhtmlTargetCourse(this.targetCourse);//更新数据中心UserBean的数据
        }
    }



    public Course getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    public List<Chapter> getTargetChapterList() {
        return targetChapterList;
    }

    public void setTargetChapterList(List<Chapter> targetChapterList) {
        this.targetChapterList = targetChapterList;
    }

    public String getShowTeacherOptionDiv() {
        return showTeacherOptionDiv;
    }

    public void setShowTeacherOptionDiv(String showTeacherOptionDiv) {
        this.showTeacherOptionDiv = showTeacherOptionDiv;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public ChapterPK getChapterPK() {
        return chapterPK;
    }

    public void setChapterPK(ChapterPK chapterPK) {
        this.chapterPK = chapterPK;
    }

    public int getSequence_number() {
        return sequence_number;
    }

    public void setSequence_number(int sequence_number) {
        this.sequence_number = sequence_number;
    }


}
