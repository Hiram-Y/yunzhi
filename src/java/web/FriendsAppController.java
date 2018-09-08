/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Friend;
import entity.FriendPK;
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
@ManagedBean(name = "friendsAppController")
@ViewScoped
public class FriendsAppController implements Serializable {

    /**
     * 下面是该类的“共有”属性，实现getter和setter方法
     */
    private User user;
    private List<User> friends = new ArrayList<>();

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
     * Creates a new instance of FriendsAppController
     */
    public FriendsAppController() {
        System.out.println(this.getTimeNow() + "[FriendsAppController()]");
    }

    /**
     * 为什么会需要这个函数呢？ 在构造函数中不可以直接使用该类（class CourseController）的属性，因为函数还没有构造完成。
     * initi()函数是在构造函数结束之后才自动运行的，所以要使用该类属性，请在init()函数中使用
     */
    @PostConstruct
    public void init() {
        user = this.userBean.getUser();
        if (user == null || user.getId() == null) {
            System.out.println(this.getTimeNow() + "[FriendsAppController.init()]没有取到正确的user数据");
        } else {
            System.out.println(this.getTimeNow() + "[FriendsAppController.init()]数据初始化中");
            for (Friend f : this.facadeBean.getFriendFacade().findAll()) {
                if (f.getFriendPK().getUserId1().equals(user.getId())) {
                    this.friends.add(this.facadeBean.getUserFacade().find(f.getFriendPK().getUserId2()));
                }
            }
        }
    }

    public String removeFriend() {
        User user2 = this.userBean.getUser2();
        User user = this.userBean.getUser();
        String msg;
        String detail = "[friendsAppController.addFriend()]user2===" + user2 + "********user==" + user;
        String navigator="";
        if (user == null || user.getId() == null || user2 == null || user2.getId() == null) {
            msg = "用户并没有登陆，不能执行删除好友操作";
            detail = this.getTimeNow() + "[friendsAppController.addFriend()]";
        } else {
            if (this.facadeBean.getUserFacade().find(user2.getId()) == null) {
                msg = "并不存在该用户";
                detail = this.getTimeNow() + "[friendsAppController.addFriend()]";
                navigator="";
            } else {
                FriendPK friendPK = new FriendPK();
                Friend friend = new Friend();
                friendPK.setUserId1(user.getId());
                friendPK.setUserId2(user2.getId());
                friend.setFriendPK(friendPK);
                if (this.facadeBean.getFriendFacade().find(friendPK) == null) {
                    msg = "你们已经不是是好友关系";
                    detail = "";
                } else {
                    this.facadeBean.getFriendFacade().remove(friend);
                    msg = "删除好友操作成功";
                    detail = user.getId() + "和" + user2.getId();
                    navigator="/mobile/friendsApp";
                }
                friendPK = new FriendPK();
                friend = new Friend();
                friendPK.setUserId1(user2.getId());
                friendPK.setUserId2(user.getId());
                friend.setFriendPK(friendPK);
                if (this.facadeBean.getFriendFacade().find(friendPK) == null) {
                    msg = "你们已经不是是好友关系";
                    detail = "";
                } else {
                    this.facadeBean.getFriendFacade().remove(friend);
                    msg = "删除好友操作成功";
                    detail = user.getId() + "和" + user2.getId();
                    navigator="/mobile/friendsApp";
                }
            }
        }
        FacesMessage msg2 = new FacesMessage(msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg2);
        System.out.println(msg);
        return navigator;
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

}
