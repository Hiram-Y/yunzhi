/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import session.CourseFacade;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "courseController")
@ViewScoped
public class CourseController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    List<Course> courses = new ArrayList<>();

    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    //=============================依赖注入部分=================================
    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;

    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    @EJB
    private CourseFacade courseFacade;

    /**
     *
     * Creates a new instance of CourseController
     */
    public CourseController() {
        System.out.println("[CourseController.CourseController()]构造函数");
    }

    /**
     * 为什么会需要这个函数呢？ 在构造函数中不可以直接使用该类（class CourseController）的属性，因为函数还没有构造完成。
     * initi()函数是在构造函数结束之后才自动运行的，所以要使用该类属性，请在init()函数中使用
     */
    @PostConstruct
    public void init() {
        System.out.println(this.facadeBean.getTimeNow() + "[CourseController.init()]");
        courses = this.facadeBean.getCourseFacade().findAll();
//        courses = courseFacade.findAll();
    }


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
