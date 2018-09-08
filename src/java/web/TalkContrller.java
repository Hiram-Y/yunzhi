/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import session.UserFacade;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "talkContrller")
@ViewScoped
public class TalkContrller implements Serializable {

    private String userId;
    private String talkTo;
    private String back="/mobile/index";
    private User userUserId;
    private User userTalkTo;

    @EJB
    private UserFacade userFacade;

    /**
     * Creates a new instance of TalkContrller
     */
    public TalkContrller() {
        System.out.println(this.getTimeNow() + "[TalkContrller()]");
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public void initData() {
        this.userUserId = userFacade.find(this.userId);
        this.userTalkTo = userFacade.find(this.talkTo);
        System.out.println( this.back);
        if(this.back.equals("/mobile/classroom_friends") 
                || this.back.equals("/mobile/friendsApp")){
        }else{
            this.back="/mobile/index";
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTalkTo() {
        return talkTo;
    }

    public void setTalkTo(String talkTo) {
        this.talkTo = talkTo;
    }

    public User getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(User userUserId) {
        this.userUserId = userUserId;
    }

    public User getUserTalkTo() {
        return userTalkTo;
    }

    public void setUserTalkTo(User userTalkTo) {
        this.userTalkTo = userTalkTo;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
    
    
    
}
