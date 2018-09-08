/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Resume;
import entity.ResumePK;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    /**
     * 下面是该类的公开属性，使用getter和setter方法实现公开
     */
    private String userID;
    private String userPassword;
    private String userPassword2;
    private String userName;
    private String identity;

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
     * Creates a new instance of LoginController
     */
    public LoginController() {
        System.out.println("[LoginController()]");
    }

    public String logIn() {
        System.out.println(facadeBean.getTimeNow() + "[LoginController.logIn()]=====userID:" + userID + "====userPassword:" + userPassword);
        User u = facadeBean.getUserFacade().find(userID);
        if (u != null && u.getPassword().equals(userPassword)) {
            List<Resume> resumeList = new ArrayList();
            //将与User u相关的resume(简历)找出来,并且满足resume.startDate<现在时间<resume.endDate的resume
            Date dateTimeNow = new Date();
            for (Resume r : facadeBean.getResumeFacade().findAll()) {
                if (u.equals(r.getUser()) 
                        && r.getResumePK().getStartDate().before(dateTimeNow) 
                        && r.getResumePK().getEndDate().after(dateTimeNow)) {
                    resumeList.add(r);
                }
            }
            if(resumeList.isEmpty()){
                ResumePK rPK=new ResumePK();
                Resume r=new Resume();
                rPK.setIdentityName("");
                r.setResumePK(rPK);
                resumeList.add(r);
            }
            userBean.signIn();//编辑UserBean.signInStatus和UserBean.signOutStatus的数据，令toolbar上的登陆注册按钮消失
            userBean.setUser(u);//给UserBean.user注入数据
            userBean.setResumeList(resumeList);//给UserBean.resumeList注入数据
            userBean.setSelectResume(resumeList.get(0));//给UserBean.selectResume注入数据
            return "myCourses";
        } else {
            FacesMessage message = new FacesMessage("账号或密码错误", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "login";
        }
    }

    public String register() {
        if (userPassword.equals(userPassword2)) {
            User u = facadeBean.getUserFacade().find(userID);
            if (u == null) {
                u = new User();
                u.setId(userID);
                u.setName(userName);
                u.setPassword(userPassword);
                u.setLogo("/media/images/logo_user.jpg");
                facadeBean.getUserFacade().create(u);
                this.logIn();
                return "index";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "注册失败", "该用户名已经被注册"));
                return "";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "注册失败", "两次输入的密码不一致"));
            return "";
        }

    }

    
    
    
    
    
    
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword2() {
        return userPassword2;
    }

    public void setUserPassword2(String userPassword2) {
        this.userPassword2 = userPassword2;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    

}
