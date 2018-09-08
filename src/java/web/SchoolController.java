/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

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
@ManagedBean(name = "schoolController")
@ViewScoped
public class SchoolController implements Serializable {

    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;
    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    List<School> schools = new ArrayList<>();

    /**
     * Creates a new instance of UniversityController
     */
    public SchoolController() {
        System.out.println("[SchoolController.SchoolController()]构造函数");
    }

    //使用依赖项schoolFacade要在init中实现，因为在构造函数调用的时候，类还没有完成初始化，无法注入依赖项
    @PostConstruct
    public void init() {
        System.out.println(facadeBean.getTimeNow()+"[SchoolController.init()]");
        schools = facadeBean.getSchoolFacade().findAll();
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}
