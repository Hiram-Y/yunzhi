/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.Friend;
import entity.FriendPK;
import entity.Inform;
import entity.InformPK;
import entity.Student;
import entity.Teacher;
import entity.User;
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

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "classroomFriendsController")
@ViewScoped
public class ClassroomFriendsController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    private List<User> students = new ArrayList<>();
    private List<User> teachers = new ArrayList<>();

    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    @ManagedProperty("#{userBean}")
    private UserBean userBean;
    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;
    private Course targetCourse;

    //非常重要，这是实现依赖注入的关键@ManagedProperty("#{userBean}")
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    /**
     * Creates a new instance of ClassroomFriends
     */
    public ClassroomFriendsController() {
        System.out.println(this.getTimeNow() + "[ClassroomFriendsController()]");

    }

    /**
     * 为什么会需要这个函数呢？ 在构造函数中不可以直接使用该类（class CourseController）的属性，因为函数还没有构造完成。
     * initi()函数是在构造函数结束之后才自动运行的，所以要使用该类属性，请在init()函数中使用
     */
    @PostConstruct
    public void init() {
        this.targetCourse = userBean.getXhtmlTargetCourse();//从@ManagedProperty("#{userBean}")传入选择的课程
        if (targetCourse.getSchool() != null) {
            for (Student s : this.facadeBean.getStudentFacade().findAll()) {
                if (s.getStudentPK().getCourseId() == this.targetCourse.getCoursePK().getId()) {
                    students.add(s.getUser());
                }
            }
            for (Teacher t : this.facadeBean.getTeacherFacade().findAll()) {
                if (t.getTeacherPK().getCourseId() == this.targetCourse.getCoursePK().getId()) {
                    teachers.add(t.getUser());
                }
            }
            System.out.println(this.facadeBean.getTimeNow() + "[ClassroomFriendsController.init()]" + "this.targetCourse====" + this.targetCourse);
        }
    }

    public String showOrNot(String talkTo) {
        Short flag = 0;
        if (userBean.getUser() == null
                || userBean.getUser().getId() == null
                || talkTo == null) {
            System.out.println("[ClassroomFriendsController.showOrNot()]方法出错");
        } else {
            String userId = userBean.getUser().getId();
            InformPK informPK = new InformPK();
            informPK.setTalkTo(talkTo);
            informPK.setUserid(userId);
            Inform inform = this.facadeBean.getInformFacade().find(informPK);
            if (inform == null) {
                System.out.println("[ClassroomFriendsController.showOrNot()]空指针");
            } else {
                flag = inform.getNewMessage();
            }
        }
        if (flag == 0) {
            return "none";
        } else {
            return "";
        }
    }
    
    public void addFriend(){
        User user2 = this.userBean.getUser2();
        User user = this.userBean.getUser();
        this.facadeBean.addFriend(user, user2);
    }
    
    
    
    
    

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

}
