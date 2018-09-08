/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Inform;
import entity.User;
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
@ManagedBean(name = "noticeController")
@ViewScoped
public class NoticeController implements Serializable {

    List<User> newMessageUserList = new ArrayList<>();

    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    @ManagedProperty("#{userBean}")
    private UserBean userBean;
    @ManagedProperty("#{facadeBean}")
    private FacadeBean facadeBean;
    private User user;

    //非常重要，这是实现依赖注入的关键@ManagedProperty("#{userBean}")
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    //实现@ManagedProperty("#{facadeBean}")依赖注入的关键是实现它的setter方法
    public void setFacadeBean(FacadeBean facadeBean) {
        this.facadeBean = facadeBean;
    }

    /**
     * Creates a new instance of NoticeController
     */
    public NoticeController() {
    }

    /**
     * 为什么会需要这个函数呢？ 在构造函数中不可以直接使用该类（class CourseController）的属性，因为函数还没有构造完成。
     * initi()函数是在构造函数结束之后才自动运行的，所以要使用该类属性，请在init()函数中使用
     */
    @PostConstruct
    public void init() {
        user = this.userBean.getUser();
        if (user != null && user.getId() != null) {
            for (Inform i : this.facadeBean.getInformFacade().findAll()) {
                //找到跟User发最新消息的用户，并加入newMessageUserList中
                if (i.getInformPK().getUserid().equals(user.getId()) 
                        && i.getNewMessage()==1) {
                    User user2=this.facadeBean.getUserFacade().find(i.getInformPK().getTalkTo());
                    newMessageUserList.add(user2);
                }
            }
            System.out.println(this.facadeBean.getTimeNow() + "[NoticeController.init()]====数据初始化中");
        }
    }

    public List<User> getNewMessageUserList() {
        return newMessageUserList;
    }

    public void setNewMessageUserList(List<User> newMessageUserList) {
        this.newMessageUserList = newMessageUserList;
    }

}
