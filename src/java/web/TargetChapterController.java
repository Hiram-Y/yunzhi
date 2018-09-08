/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Chapter;
import entity.Vedio;
import entity.VedioPK;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "targetChapterController")
@ViewScoped
public class TargetChapterController implements Serializable {

    private Vedio vedio;
    private List<Vedio> vedioList = new ArrayList<>();
    private Chapter targetChapter;

    private UploadedFile file;
    private HttpServletRequest request;
    private String storagePath;
    private String path = "/media/vedios/";

    private String showTeacherOptionDiv = "none";
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
     * Creates a new instance of TargetChapterController
     */
    public TargetChapterController() {
        System.out.println(this.getTimeNow() + "[TargetChapterController() ]");
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        storagePath = request.getRealPath(path);
    }

    @PostConstruct
    public void init() {
        targetChapter = this.userBean.getXhtmlTargetChapter();
        if (targetChapter == null || targetChapter.getChapterPK() == null) {
            System.out.println(this.getTimeNow() + "[TargetChapterController.init() ]===数据初始化失败");
        } else {
            System.out.println(this.getTimeNow() + "[TargetChapterController.init() ]===数据初始化中");
            for (Vedio v : this.facadeBean.getVedioFacade().findAll()) {
                if (targetChapter.getChapterPK().getCourseEndDate().equals(v.getVedioPK().getChapterCourseEndDate())
                        && targetChapter.getChapterPK().getCourseStartDate().equals(v.getVedioPK().getChapterCourseStartDate())
                        && targetChapter.getChapterPK().getCourseId() == v.getVedioPK().getChapterCourseId()
                        && targetChapter.getChapterPK().getCourseSchoolId() == v.getVedioPK().getChapterCourseSchoolId()
                        && targetChapter.getChapterPK().getSequenceNumber() == v.getVedioPK().getChapterSequenceNumber()) {
                    this.vedio = v;
                    vedioList.add(v);
                }
            }
            if (this.facadeBean.isUserATeacherOfTargetCourse(this.userBean.getUser(), this.userBean.getXhtmlTargetCourse())) {
                showTeacherOptionDiv = "";
            }
        }
    }

    public void startUpload() throws FileNotFoundException, IOException {
        if (targetChapter == null || targetChapter.getChapterPK() == null) {

        } else {
            if (file != null) {
                if (!"video/mp4".equals(file.getContentType())) {
                    FacesMessage text = new FacesMessage("只允许上传MP4格式的视频", file.getFileName());
                    FacesContext.getCurrentInstance().addMessage(null, text);
                } else {
                    System.out.println("type====" + file.getContentType()
                            + "*******size====" + file.getSize());
                    InputStream is = new BufferedInputStream(file.getInputstream());
                    byte[] buffer = new byte[(int) file.getSize()];
                    System.out.println(storagePath);
                    String vedioName = this.facadeBean.GernaratePWD(FacadeBean.PasswordType.Mixed, 16);
                    if (!vedioName.equals("")) {
//                    String filePath = storagePath + "/" + vedioName + ".mp4";
                        File dir = new File(storagePath);
                        this.facadeBean.judeDirExists(dir);
                        File f = new File(storagePath, vedioName + ".mp4");
                        if (f.exists()) {
                            FacesMessage text = new FacesMessage("上传失败请重新尝试一次", file.getFileName());
                            FacesContext.getCurrentInstance().addMessage(null, text);
                        } else {
                            try (FileOutputStream fos = new FileOutputStream(f)) {
                                while (is.read(buffer) > 0) {
                                    fos.write(buffer);
                                }
                                System.out.println("写入成功");
                                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                                FacesContext.getCurrentInstance().addMessage(null, message);
                                VedioPK vedioPK = new VedioPK();
                                vedioPK.setChapterCourseEndDate(this.targetChapter.getChapterPK().getCourseEndDate());
                                vedioPK.setChapterCourseId(this.targetChapter.getChapterPK().getCourseId());
                                vedioPK.setChapterCourseSchoolId(this.targetChapter.getChapterPK().getCourseSchoolId());
                                vedioPK.setChapterCourseStartDate(this.targetChapter.getChapterPK().getCourseStartDate());
                                vedioPK.setChapterSequenceNumber(this.targetChapter.getChapterPK().getSequenceNumber());
                                vedioPK.setSequenceNumber(this.targetChapter.getVedioCollection().size() + 1);
                                Vedio vedio = new Vedio();
                                vedio.setVedioPK(vedioPK);
                                vedio.setName(file.getFileName());
                                vedio.setUrl(path + vedioName + ".mp4");
                                vedio.setVedioPK(vedioPK);
                                this.facadeBean.getVedioFacade().create(vedio);
                                this.targetChapter.getVedioCollection().add(vedio);
                                this.userBean.setXhtmlTargetChapter(targetChapter);
                            }
                        }
                    }
//            File ff = new File(path, file.getFileName());
//            String type = new MimetypesFileTypeMap().getContentType(ff);
//            System.out.println("================type===" + type + "*********type=" + file.getContentType());
                }
            }
        }
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public Vedio getVedio() {
        return vedio;
    }

    public void setVedio(Vedio vedio) {
        this.vedio = vedio;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Vedio> getVedioList() {
        return vedioList;
    }

    public void setVedioList(List<Vedio> vedioList) {
        this.vedioList = vedioList;
    }

    public String getShowTeacherOptionDiv() {
        return showTeacherOptionDiv;
    }

}
