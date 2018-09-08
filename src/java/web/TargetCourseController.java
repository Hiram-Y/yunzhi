/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.Student;
import entity.StudentPK;
import entity.Teacher;
import entity.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;


/**
 * 会话过程中的用户数据存储中心
 *
 * @author Dawson
 */
@ManagedBean(name = "targetCourseController")
@ViewScoped
public class TargetCourseController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    private String isXhtmlDivShow = "inline";
    private Course targetCourse;
    private String xhtmlButtonValue;
    private boolean xhtmlbuttonDisabled;

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
     * Creates a new instance of TargetCourseController
     */
    public TargetCourseController() {
        System.out.println("[TargetCourseController()]");
    }

    //因为在构造函数调用的时候，类还没有完成初始化，无法注入依赖项,也就是无法使用该类的属性
    @PostConstruct
    public void init() {
        this.targetCourse = userBean.getXhtmlTargetCourse();//从@ManagedProperty("#{userBean}")传入选择的课程
        if (this.targetCourse == null || this.targetCourse.getCoursePK()==null) {
            isXhtmlDivShow = "none";//页面不显示内容
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.init()]" + "this.targetCourse====" + this.targetCourse);
        } else {
            this.xhtmlButtonValue();
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.init()]" + "this.targetCourse====" + this.targetCourse);
        }
    }

    /**
     * 如果user（不论是老师还是学生）已经拥有该课程，则显示“进入教室”按钮，触发enterClassroom()事件。
     * 如果user没有拥有该课程，并且该课程没有过期则，显示“参加课程按钮”，触发enterClassroom()事件。
     * 否则显示disable的“参加课程按钮”，不触发任何事件
     *
     * ======== 3月29日编辑==========之后计划接入支付宝支付系统
     */
    private void xhtmlButtonValue() {
        if (this.isUserHasTargetCourse()) {
            this.xhtmlButtonValue = "进入教室";
        } else if (!this.isTargetCourseTimeOut()) {
            this.xhtmlButtonValue = "参加课程";
        } else {
            this.xhtmlButtonValue = "参加课程";
            this.xhtmlbuttonDisabled = true;
        }
    }

    public String xhtmlButtonAction() {
        if (this.isUserHasTargetCourse()) {
            return this.enterClassroom();
        } else if (!this.isTargetCourseTimeOut()) {
            return this.joinCourse();
        }
        return "";
    }

    private boolean isTargetCourseTimeOut() {
        Date dateTimeNow = new Date();
        if (this.targetCourse.getCoursePK().getEndDate().before(dateTimeNow)) {
            return true;
        }
        return false;
    }

    private boolean isUserHasTargetCourse() {
        User user = this.userBean.getUser();
        List<Student> studentList = this.facadeBean.getStudentFacade().findAll();
        List<Teacher> teacherList = this.facadeBean.getTeacherFacade().findAll();
        for (Student s : studentList) {
            //如果student表中存在存在该user与该targetCourse相对应的记录，则表示该userHasTargetCourse
            if (s.getUser().equals(user)
                    && s.getCourse().equals(this.targetCourse)) {
                return true;
            }
        }
        for (Teacher t : teacherList) {
            //如果Teacher表中存在存在该user与该targetCourse相对应的记录，则表示该userHasTargetCourse
            if (t.getUser().equals(user) && t.getCourse().equals(this.targetCourse)) {
                return true;
            }
        }
        //否则返回false
        return false;
    }

    //user(不论user的身份)参加课程的功能
    private String joinCourse() {
        User user=this.userBean.getUser();
        if (user.getId()==null || user.getId().equals("") ) {
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.joinCourse()]===return 'login'");
            return "login";
        } else {
            Student student=new Student();
            StudentPK studentPK=new StudentPK();
            studentPK.setCourseEndDate(this.targetCourse.getCoursePK().getEndDate());
            studentPK.setCourseId(this.targetCourse.getCoursePK().getId());
            studentPK.setCourseSchoolId(this.targetCourse.getCoursePK().getSchoolId());
            studentPK.setCourseStartDate(this.targetCourse.getCoursePK().getStartDate());
            studentPK.setUserId(user.getId());
            student.setStudentPK(studentPK);
            student.setCourse(targetCourse);
            student.setUser(user);
            this.facadeBean.getStudentFacade().create(student);
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.joinCourse()]===加入课程成功");
            return "myCourses";
        }
    }

    //user(不论user的身份)进入教室的功能
    private String enterClassroom() {
        if (this.userBean.getUser().getId()==null || this.userBean.getUser().getId().equals("") ) {
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.enterClassroom()]===return 'login'");
            return "login";
        } else {
            System.out.println(this.facadeBean.getTimeNow() + "[TargetCourseController.enterClassroom()]");
            return "classroom_informationFlow";
        }
    }
    
    public String dateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateString=sdf.format(date);
        return dateString;
    }
    
    
    
    
    

    public Course getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    public String getXhtmlButtonValue() {
        return xhtmlButtonValue;
    }

    public void setXhtmlButtonValue(String xhtmlButtonValue) {
        this.xhtmlButtonValue = xhtmlButtonValue;
    }

    public boolean isXhtmlbuttonDisabled() {
        return xhtmlbuttonDisabled;
    }

    public void setXhtmlbuttonDisabled(boolean xhtmlbuttonDisabled) {
        this.xhtmlbuttonDisabled = xhtmlbuttonDisabled;
    }

    public String getIsXhtmlDivShow() {
        return isXhtmlDivShow;
    }

    public void setIsXhtmlDivShow(String isXhtmlDivShow) {
        this.isXhtmlDivShow = isXhtmlDivShow;
    }

}
