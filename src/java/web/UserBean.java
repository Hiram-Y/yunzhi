/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Chapter;
import entity.Course;
import entity.Liveroom;
import entity.Notice;
import entity.Resume;
import entity.School;
import entity.User;
import java.io.File;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.mobile.event.SwipeEvent;
import org.primefaces.model.StreamedContent;
import session.UserFacade;

/**
 *
 * @author Dawson
 */
//@Named(value="userBean")
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private String backViewUrl;
    private String liveRoomId = "index";

    /**
     * 该类实现了getter和setter的“公共”属性
     */
    //用于表示现在的登陆状态，主要是为了实现xhtml页面中toolbar的登陆按钮显示或是隐藏，位置在/web页/template文件夹下的xhtml
    private String signInStatus = "none";
    private String signOutStatus = "inline";
    //表示一个session会话中的登陆的用户
    private User user = new User();
    private User user2;
    //表示还在注册时间内的简历
    private List<Resume> resumeList = new ArrayList();
    private Resume selectResume;

    //data from couorse.xhtml for TargetCourseController
    private Course xhtmlTargetCourse = new Course();
    //data from school.xhtml for TargetSchoolController
    private School xhtmlTargetSchool = new School();
    //data from school.xhtml for TargetSchoolController
    private Chapter xhtmlTargetChapter = new Chapter();

    private String targetCourse;

    private String oldPwd;
    private String newPwd;
    private String newPwd2;
    private String newName;
    
    private User userId;
    private User talkTo;

    private String path="";
    
    private StreamedContent streamFile;
    
    
    @EJB
    private UserFacade userFacade;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        System.out.println(this.getTimeNow() + "[UserBean()]");
//        PrimeFaces.current().executeScript("var liveRoomId=" + liveRoomId);
    }

//    public void param(){
//             System.out.println(this.getTimeNow() + "[UserBean.getParam()]==="+targetCourse);
//    }
    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public void signIn() {
        System.out.println(this.getTimeNow() + "[signIn()]");
        signInStatus = "inline";
        signOutStatus = "none";
    }

    public String signOut() {
        //清理登陆数据
        signInStatus = "none";
        signOutStatus = "inline";
        user = new User();
        resumeList.clear();
        System.out.println(this.getTimeNow() + "[signOut()]=====注销成功");
        return "index";
    }

    public String backView() {
        System.out.println(this.getTimeNow() + "[UserBean.backView()]" + backViewUrl);
        return backViewUrl;
    }

    public void swipeUser(SwipeEvent e) {
        user2 = (User) e.getData();
    }

    public void changeUserName() {
        System.out.println(this.getTimeNow() + "[UserBean.changUserName()]");
        if (user != null && this.userFacade.find(user.getId()).equals(user)) {
            user.setName(newName);
            this.userFacade.edit(user);
        }
        newName = "";
    }

    public void changeUserPassword() {
        System.out.println(this.getTimeNow() + "[UserBean.changeUserPassword()]");
        String msg;
        if (user != null
                && this.userFacade.find(user.getId()).equals(user)) {
            if (newPwd.length() >= 6) {
                if (user.getPassword().equals(oldPwd)) {
                    if (newPwd2.equals(newPwd)) {
                        user.setPassword(newPwd);
                        this.userFacade.edit(user);
                        msg = "修改密码成功";
                    } else {
                        msg = "两次密码输入不一致";
                    }
                } else {
                    msg = "旧密码输入错误";
                }
            } else {
                msg = "密码长度应该至少6位";
            }
        } else {
            msg = "修改密码失败";
        }
        oldPwd = "";
        newPwd = "";
        newPwd2 = "";
        FacesMessage msg2 = new FacesMessage(msg, "");
        FacesContext.getCurrentInstance().addMessage(null, msg2);
    }

    public String getSignInStatus() {
        return signInStatus;
    }

    public void setSignInStatus(String signInStatus) {
        this.signInStatus = signInStatus;
    }

    public String getSignOutStatus() {
        return signOutStatus;
    }

    public void setSignOutStatus(String signOutStatus) {
        this.signOutStatus = signOutStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Resume> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<Resume> resumeList) {
        this.resumeList = resumeList;
    }

    public Course getXhtmlTargetCourse() {
        return xhtmlTargetCourse;
    }

    public void setXhtmlTargetCourse(Course xhtmlTargetCourse) {
        this.xhtmlTargetCourse = xhtmlTargetCourse;
    }

    public School getXhtmlTargetSchool() {
        return xhtmlTargetSchool;
    }

    public void setXhtmlTargetSchool(School xhtmlTargetSchool) {
        this.xhtmlTargetSchool = xhtmlTargetSchool;
    }

    public String getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(String targetCourse) {
        this.targetCourse = targetCourse;
    }

    public Chapter getXhtmlTargetChapter() {
        return xhtmlTargetChapter;
    }

    public void setXhtmlTargetChapter(Chapter xhtmlTargetChapter) {
        this.xhtmlTargetChapter = xhtmlTargetChapter;
    }

    public String getBackViewUrl() {
        return backViewUrl;
    }

    public void setBackViewUrl(String backViewUrl) {
        this.backViewUrl = backViewUrl;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPwd2() {
        return newPwd2;
    }

    public void setNewPwd2(String newPwd2) {
        this.newPwd2 = newPwd2;
    }

    public Resume getSelectResume() {
        return selectResume;
    }

    public void setSelectResume(Resume selectResume) {
        this.selectResume = selectResume;
    }

    public User getTalkTo() {
        return talkTo;
    }

    public void setTalkTo(User talkTo) {
        this.talkTo = talkTo;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StreamedContent getStreamFile() {
        System.out.println("fafasff==="+this.streamFile);
        return streamFile;
    }

    public void setStreamFile(StreamedContent streamFile) {
        this.streamFile = streamFile;
    }


    
}
