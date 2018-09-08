/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "cloudDiskController")
@ViewScoped
public class CloudDiskController implements Serializable {

    private List<File> fileList = new ArrayList<>();
    private File selectFile;
    private StreamedContent streamFile;

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
    private User user = new User();
    private HttpServletRequest request;
    private String storagePath;
    private String path;

    /**
     * Creates a new instance of CloudDiskController
     */
    public CloudDiskController() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        storagePath = request.getRealPath("/media/cloudDisk");
        System.out.println("[CloudDiskController()]");
    }

    @PostConstruct
    public void init() {
        user = this.userBean.getUser();
        if (user != null && user.getId() != null) {
            //判断用户已经登陆.
            path = storagePath + "/" + user.getId();
            File dir = new File(path);
            this.facadeBean.judeDirExists(dir);
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    this.fileList.add(files[i]);
                }
            }
        }
    }

    public void initStreamFile() throws FileNotFoundException {
        if (this.selectFile != null) {
            String contentType = new MimetypesFileTypeMap().getContentType(selectFile);
//            String contentType="application/zip";
//            String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            System.out.println("================type===" + contentType + "*****exists==" + this.selectFile.exists());
            if (this.selectFile.exists()) {
                InputStream stream=new FileInputStream(selectFile);
                this.streamFile = new DefaultStreamedContent(stream, contentType, selectFile.getName());
                System.out.println("++++"+this.streamFile);
                this.userBean.setStreamFile(streamFile);
            }
        }
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public File getSelectFile() {
        return selectFile;
    }

    public void setSelectFile(File selectFile) {
        this.selectFile = selectFile;
    }

    public StreamedContent getStreamFile() {
        System.out.println("==="+this.streamFile);
        return streamFile;
    }

    public void setStreamFile(StreamedContent streamFile) {
        this.streamFile = streamFile;
    }

}
