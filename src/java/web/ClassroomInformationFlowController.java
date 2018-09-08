/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Course;
import entity.Notice;
import entity.NoticePK;
import entity.User;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "classroomInformationFlowController")
@Dependent
public class ClassroomInformationFlowController implements Serializable {

    //实现setter和getter的方法
    private boolean upload;
    private Notice notice = new Notice();
    private List<Notice> noticeList = new ArrayList<>();
    private String showTeacherOptionDiv = "none";
    private UploadedFile file;

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

    //没有实现getter和setter的方法
    private Course targetCourse;
    private User user;
    private HttpServletRequest request;
    private String storagePath;

    /**
     *
     * Creates a new instance of ClassroomInformationFlow
     */
    public ClassroomInformationFlowController() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        storagePath = request.getRealPath("/media");
        System.out.println("[ClassroomInformationFlowController()]");
    }

    @PostConstruct
    public void init() {
        System.out.println("[ClassroomInformationFlowController.init()]");
        targetCourse = this.userBean.getXhtmlTargetCourse();
        if (targetCourse != null && targetCourse.getCoursePK() != null) {
            updateNoticeList();
            user = this.userBean.getUser();
            if (user != null && user.getId() != null) {
                //确保用户已经登陆
                if (this.facadeBean.isUserATeacherOfTargetCourse(user, targetCourse)) {
                    showTeacherOptionDiv = "";//显示教师操作接口
                }
            }
        }
    }

    public void checkUpload() {
        String msg;
        String detail;
        if (this.upload) {
            msg = "上传通道开启";
            detail = "学生上传的zip文件您可以在我的云盘中查看";
        } else {
            msg = "上传通道关闭";
            detail = "";
        }
        FacesMessage text = new FacesMessage(msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, text);
    }

    public void newNotice() {
        if (targetCourse != null && targetCourse.getCoursePK() != null) {
            NoticePK noticePK = new NoticePK();
            noticePK.setCourseEndDate(this.targetCourse.getCoursePK().getEndDate());
            noticePK.setCourseId(this.targetCourse.getCoursePK().getId());
            noticePK.setCourseSchoolId(this.targetCourse.getCoursePK().getSchoolId());
            noticePK.setCourseStartDate(this.targetCourse.getCoursePK().getStartDate());
            noticePK.setPublishDatetime(new Date());
            notice.setNoticePK(noticePK);
            if ((notice.getTitle() != null || !notice.getTitle().equals(""))
                    && (notice.getBody() != null || notice.getBody().equals(""))
                    && user != null
                    && user.getId() != null) {
                //确保用户已经登陆和确保Notice输入Title和body数据
                if (this.upload) {
                    //如果开启了上传通道，则设置默认的存储地址
                    notice.setUploadFileUrl("/cloudDisk/" + user.getId());
                }
                this.facadeBean.getNoticeFacade().create(notice);
                updateNoticeList();
                notice = new Notice();
                FacesMessage text = new FacesMessage("添加通知成功", "");
                FacesContext.getCurrentInstance().addMessage(null, text);
            }
        }
    }

    private void updateNoticeList() {
        noticeList.clear();
        for (Notice n : this.facadeBean.getNoticeFacade().findAll()) {
//            CoursePK coursePK=new Course
//            Course course=new Course();
//            course.setCoursePK(n.getNoticePK().getCourseId());
//            n.setCourse(targetCourse);
            if (this.targetCourse.equals(n.getCourse())) {
                noticeList.add(0, n);
            } else {
            }
        }
    }

    public String showUpload(Notice notice) {
        String flag = "none";
        if (notice.getUploadFileUrl() != null || "".equals(notice.getUploadFileUrl())) {
            flag = "";
        }
        return flag;
    }

    public void startUpload() throws FileNotFoundException, IOException {
        if (file != null) {
            System.out.println("type====" + file.getContentType()
                    + "*******size====" + file.getSize());
            InputStream is = new BufferedInputStream(file.getInputstream());
            byte[] buffer = new byte[(int) file.getSize()];
            System.out.println(storagePath);
            String path = storagePath + "/" + this.userBean.getPath();
            File dir = new File(path);
            this.facadeBean.judeDirExists(dir);
            File f = new File(path, file.getFileName());
            if (f.exists()) {
                FacesMessage text = new FacesMessage("文件名已经存在,请更改文件名后重传", file.getFileName());
                FacesContext.getCurrentInstance().addMessage(null, text);
            } else {
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    while (is.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                    System.out.println("写入成功");
                    FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
//            File ff = new File(path, file.getFileName());
//            String type = new MimetypesFileTypeMap().getContentType(ff);
//            System.out.println("================type===" + type + "*********type=" + file.getContentType());
        }
    }

    public void startUpload2(FileUploadEvent event) {
        if (event.getFile() != null) {
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public String getShowTeacherOptionDiv() {
        return showTeacherOptionDiv;
    }

    public void setShowTeacherOptionDiv(String showTeacherOptionDiv) {
        this.showTeacherOptionDiv = showTeacherOptionDiv;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    //boolean的getter
    public boolean isUpload() {
        return upload;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
