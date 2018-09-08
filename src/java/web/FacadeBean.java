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
import entity.Teacher;
import entity.User;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.ChapterFacade;
import session.CourseFacade;
import session.FriendFacade;
import session.InformFacade;
import session.LiveroomFacade;
import session.NoticeFacade;
import session.ResumeFacade;
import session.SchoolFacade;
import session.StudentFacade;
import session.TeacherFacade;
import session.UserFacade;
import session.VedioFacade;

/**
 *
 * 这个是生命周期是application的managedBean
 * 主要目的是为所有ManagedBean提供使用@EJB注册过的各种sessionBean，提高复用率
 *
 * @author Dawson
 */
@Named(value = "facadeBean")
@SessionScoped
public class FacadeBean implements Serializable {

    @EJB
    private UserFacade userFacade;
    @EJB
    private ResumeFacade resumeFacade;
    @EJB
    private SchoolFacade schoolFacade;
    @EJB
    private CourseFacade courseFacade;
    @EJB
    private StudentFacade studentFacade;
    @EJB
    private TeacherFacade teacherFacade;
    @EJB
    private ChapterFacade chapterFacade;
    @EJB
    private VedioFacade vedioFacade;
    @EJB
    private InformFacade informFacade;
    @EJB
    private FriendFacade friendFacade;
    @EJB
    private LiveroomFacade liveroomFacade;
    @EJB
    private NoticeFacade noticeFacade;

    private UserBean userBean;

    /**
     * Creates a new instance of FacadeBean
     */
    public FacadeBean() {
        System.out.println(this.getTimeNow() + "[FacadeBean()]===SessionScoped");
        FacesContext context = FacesContext.getCurrentInstance();
        userBean = (UserBean) context.getApplication()
                .getVariableResolver().resolveVariable(context, "userBean");
    }

    public String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    //是不是要显示有新消息的提示
    public String showOrNot(String talkTo) {
        Short flag = 0;
        if (userBean.getUser() == null
                || userBean.getUser().getId() == null
                || talkTo == null) {
            System.out.println("[FacadeBean.showOrNot()]方法出错");
        } else {
            String userId = userBean.getUser().getId();
            InformPK informPK = new InformPK();
            informPK.setTalkTo(talkTo);
            informPK.setUserid(userId);
            Inform inform = this.informFacade.find(informPK);
            if (inform == null) {
                System.out.println("[FacadeBean.showOrNot()]空指针");
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

    public void addFriend(User user, User user2) {
        String msg;
        String detail = "[ClassroomFriendsController.addFriend()]user2===" + user2 + "********user==" + user;
        if (user == null || user.getId() == null || user2 == null || user2.getId() == null) {
            msg = "用户并没有登陆，不能执行添加好友操作";
            detail = this.getTimeNow() + "[ClassroomFriendsController.addFriend()]";
        } else {
            if (userFacade.find(user2.getId()) == null) {
                msg = "并不存在该用户";
                detail = this.getTimeNow() + "[ClassroomFriendsController.addFriend()]";
            } else {
                FriendPK friendPK = new FriendPK();
                Friend friend = new Friend();
                friendPK.setUserId1(user.getId());
                friendPK.setUserId2(user2.getId());
                friend.setFriendPK(friendPK);
                if (friendFacade.find(friendPK) == null) {
                    friendFacade.create(friend);
                    msg = "添加用户操作成功";
                    detail = "";
                } else {
                    msg = "已经是好友关系";
                    detail = user.getId() + "和" + user2.getId();
                }
                friendPK = new FriendPK();
                friend = new Friend();
                friendPK.setUserId1(user2.getId());
                friendPK.setUserId2(user.getId());
                friend.setFriendPK(friendPK);
                if (friendFacade.find(friendPK) == null) {
                    friendFacade.create(friend);
                    msg = "添加用户操作成功";
                    detail = "";
                } else {
                    msg = "已经是好友关系";
                    detail = user.getId() + "和" + user2.getId();
                }
            }
        }
        FacesMessage msg2 = new FacesMessage(msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg2);
        System.out.println(msg);
    }

    public enum PasswordType {
        UpCase, //大写
        LowerCase, //小写
        Number, //数字
        Mixed           //混合
    }

    /**
     * 随机生成1-10位的密码
     *
     * @param pwdType 密码类型，大写，小写，数字 或三都的混合
     * @param length 生成密码的长度
     * @return 密码字符串
     */
    public String GernaratePWD(PasswordType pwdType, int length) {
        String rtnstr = "";
        try {
            for (int i = 0; i < length; i++) {
                switch (pwdType) {
                    case UpCase:
                        rtnstr += (char) (Math.random() * 26 + 'A');    //生成随机大写字母
                        break;
                    case LowerCase:
                        rtnstr += (char) (Math.random() * 26 + 'a');  //生成随机小写字母
                        break;
                    case Number:
                        rtnstr += String.valueOf((int) (Math.random() * 10));  //生成随机数字 
                        break;
                    case Mixed:        //生成随机大写字母、小写字母或数字
                        Random random = new Random();
                        switch (random.nextInt(3)) {
                            case 0:
                                rtnstr += (char) (Math.random() * 26 + 'A');
                                break;
                            case 1:
                                rtnstr += (char) (Math.random() * 26 + 'a');
                                break;
                            case 2:
                                rtnstr += String.valueOf((int) (Math.random() * 10));
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            rtnstr = "";
        }
        return rtnstr;
    }

    public boolean isUserATeacherOfTargetCourse(User user, Course course) {
        boolean flag = false;
        for (Teacher t : teacherFacade.findAll()) {
            if (course.equals(t.getCourse()) && user.equals(t.getUser())) {
                flag = true;
            }
        }
        return flag;
    }

    // 判断文件夹是否存在
    public void judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public ResumeFacade getResumeFacade() {
        return resumeFacade;
    }

    public SchoolFacade getSchoolFacade() {
        return schoolFacade;
    }

    public CourseFacade getCourseFacade() {
        return courseFacade;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public TeacherFacade getTeacherFacade() {
        return teacherFacade;
    }

    public ChapterFacade getChapterFacade() {
        return chapterFacade;
    }

    public VedioFacade getVedioFacade() {
        return vedioFacade;
    }

    public InformFacade getInformFacade() {
        return informFacade;
    }

    public FriendFacade getFriendFacade() {
        return friendFacade;
    }

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public LiveroomFacade getLiveroomFacade() {
        return liveroomFacade;
    }

    public NoticeFacade getNoticeFacade() {
        return noticeFacade;
    }

}
