/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.Teacher;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import session.CourseFacade;
import session.UserFacade;

/**
 *
 * @author Dawson
 */
@Named(value = "searchController")
@SessionScoped
public class SearchController implements Serializable {

    private List<Course> courseList = new ArrayList();
    private List<User> userList = new ArrayList();
    private String back;
    private Course emptyCourse=new Course();

    /**
     * 下面是该类的私有属性，也就是没实现getter和setter方法
     */
    //=============================依赖注入部分=================================
    private UserBean userBean;
    private FacadeBean facadeBean;
    @EJB
    private CourseFacade courseFacade;
    @EJB
    private UserFacade userFacade;

    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        System.out.println(" SearchController()");
        FacesContext context = FacesContext.getCurrentInstance();
        userBean = (UserBean) context.getApplication()
                .getVariableResolver().resolveVariable(context, "userBean");
        facadeBean = (FacadeBean) context.getApplication()
                .getVariableResolver().resolveVariable(context, "facadeBean");
    }

    public List<Course> searchCourse(String query) {
        this.courseList.clear();
        for (Course c : courseFacade.findAll()) {
            for (Teacher t : c.getTeacherCollection()) {
                System.out.println("enter for(Teacher t:)");
                if (t.getUser().getName().contains(query)) {
                    courseList.add(c);
                    System.out.println("enter for(Teacher t:)=======if" + c);
                }
            }
            if ((c.getName().contains(query)
                    || c.getSchool().getName().contains(query)
                    || c.getSchool().getId().toString().contains(query))
                    && !courseList.contains(c)) {
                System.out.println("enter for=======if" + c);
                courseList.add(c);
            }
        }
        if(courseList.isEmpty()){
            FacesMessage msg=new FacesMessage("很遗憾，没有相关课程","");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return courseList;
    }

    public List<User> searchUser(String query) {
        this.userList.clear();
        for (User u : userFacade.findAll()) {
            if (u.getName().contains(query)
                    || u.getId().contains(query)
                    && !userList.contains(u)) {
                System.out.println("enter for=======if" + u);
                userList.add(u);
            }
        }
        return userList;
    }

    public void onItemSelect(SelectEvent event) {
        Course course = (Course) event.getObject();
        System.out.println("SearchController.onItemSelect" + course);
        this.userBean.setXhtmlTargetCourse(course);
    }

    public void onUserSelect(SelectEvent event) {
        User selectUser = (User) event.getObject();
        System.out.println("SearchController.onUserSelect" + selectUser);
        this.userBean.setUser2(selectUser);
    }

    public void addFriend() {
        User user2 = this.userBean.getUser2();
        User user = this.userBean.getUser();
        this.facadeBean.addFriend(user, user2);
    }
    

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
    
    
    
    

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Course getEmptyCourse() {
        return emptyCourse;
    }

    public void setEmptyCourse(Course emptyCourse) {
        this.emptyCourse = emptyCourse;
    }

}
