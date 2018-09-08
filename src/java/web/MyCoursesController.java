/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.CoursePK;
import entity.Student;
import entity.Teacher;
import entity.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "myCoursesController")
@ViewScoped
public class MyCoursesController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    private String isXhtmlDivShow = "";
    private List<Course> myCoursesList = new ArrayList();
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
     * Creates a new instance of MyCoursesController
     */
    public MyCoursesController() {
        System.out.println(this.getTimeNow() + "[MyCoursesController()]===构造函数");
    }

    //因为在构造函数调用的时候，类还没有完成初始化，无法注入依赖项,也就是无法使用该类的属性
    @PostConstruct
    public void init() {
        User user = userBean.getUser();//从@ManagedProperty("#{userBean}")传入选择的课程
        if (user == null || user.getId() == null) {
            isXhtmlDivShow = "none";//页面不显示内容
            System.out.println(this.getTimeNow() + "[MyCoursesController.init()]" + "====没有用户登陆，缺乏相应起始数据，不进行数据初始化，页面不显示");
        } else {
            //找出User拥有的相关课程包括参与学习的课程和参与教学的课程
            List<Student> studentList = this.facadeBean.getStudentFacade().findAll();
            List<Teacher> teacherList = this.facadeBean.getTeacherFacade().findAll();
            for (Student s : studentList) {
                //如果student表中存在存在该user与该targetCourse相对应的记录，则表示该userHasThisCourse
                if (s.getStudentPK().getUserId().equals(user.getId())) {
                    this.myCoursesList.add(s.getCourse());
                }
            }
            for (Teacher t : teacherList) {
                //如果Teacher表中存在存在该user与该targetCourse相对应的记录，则表示该userHasThisCourse
                if (t.getUser().equals(user)) {
                    this.myCoursesList.add(t.getCourse());
                }
            }
            System.out.println(this.getTimeNow() + "[MyCoursesController.init()]" + "====数据初始化中。。。");
        }
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public String getIsXhtmlDivShow() {
        return isXhtmlDivShow;
    }

    public void setIsXhtmlDivShow(String isXhtmlDivShow) {
        this.isXhtmlDivShow = isXhtmlDivShow;
    }

    public List<Course> getMyCoursesList() {
        return myCoursesList;
    }

    public void setMyCoursesList(List<Course> myCoursesList) {
        this.myCoursesList = myCoursesList;
    }
    
    

}
