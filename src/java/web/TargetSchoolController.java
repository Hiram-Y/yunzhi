/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.School;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "targetSchoolController")
@ViewScoped
public class TargetSchoolController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
//    private int targetSchoolId;
    private School targetSchool;
    private String isXhtmlDivShow = "none";
    List<Course> courses = new ArrayList<>();

    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    //=============================依赖注入部分=================================
    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;
    @ManagedProperty("#{userBean}")
    private UserBean userBean;

    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    

    /**
     * Creates a new instance of TargetSchoolController
     */
    public TargetSchoolController() {
        System.out.println("TargetSchoolController()]构造函数");
    }

    @PostConstruct
    public void enterTargetSchool() {

        this.targetSchool = this.userBean.getXhtmlTargetSchool();
        if (this.targetSchool != null) {
            System.out.println(this.facadeBean.getTimeNow() + "[TargetSchoolController.enterTargetSchool()]===数据初始化中");
            isXhtmlDivShow = "inline";//令页面显示
            for (Course c : this.facadeBean.getCourseFacade().findAll()) {
                if (c.getSchool().equals(this.targetSchool)) {
                    this.courses.add(c);
                }
            }
        }else{
             System.out.println(this.facadeBean.getTimeNow() + "[TargetSchoolController.enterTargetSchool()]===数据初始化失败");
        }
    }

    public School getTargetSchool() {
        return targetSchool;
    }

    public void setTargetSchool(School targetSchool) {
        this.targetSchool = targetSchool;
    }

//    public int getTargetSchoolId() {
//        return targetSchoolId;
//    }
//
//    public void setTargetSchoolId(int targetSchoolId) {
//        this.targetSchoolId = targetSchoolId;
//    }
    public String getIsXhtmlDivShow() {
        return isXhtmlDivShow;
    }

    public void setIsXhtmlDivShow(String isXhtmlDivShow) {
        this.isXhtmlDivShow = isXhtmlDivShow;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
