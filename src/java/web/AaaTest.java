///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package web;
//
//import entity.Chapter;
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.context.FacesContext;
//import javax.inject.Named;
//import javax.faces.view.ViewScoped;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 这是我的一个测试managedbean
// *
// * @author Dawson
// */
//@ManagedBean(name = "aaaTest")
//@ViewScoped
//public class AaaTest implements Serializable {
//
//    private String contextPath;
//
//    /**
//     * Creates a new instance of aaaTest
//     */
//    @ManagedProperty("#{facadeBean}")
//    private FacadeBean facadeBean;
//
//    public void setFacadeBean(FacadeBean facadeBean) {
//        this.facadeBean = facadeBean;
//    }
//
//    public AaaTest() {
//        HttpServletRequest request;
//        FacesContext context = FacesContext.getCurrentInstance();
//        request = (HttpServletRequest) context.getExternalContext().getRequest();
//        // 得到相对路径
//        this.contextPath = request.getRealPath("/js");
//    }
//
//    // 判断文件是否存在
//    private  void judeFileExists(File file) {
//        if (file.exists()) {
//            System.out.println("file exists");
//        } else {
//            System.out.println("file not exists, create it ...");
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//    
//    public void createVedios(){
//        int i=1;
//        for(Chapter c:this.facadeBean.getChapterFacade().findAll()){
//            
//        }
//        
//    }
//    
//    
//
//    public void printContextPath() {
//        System.out.println(this.contextPath);
//    }
//
//    public String getContextPath() {
//        return contextPath;
//    }
//
//    public void setContextPath(String contextPath) {
//        this.contextPath = contextPath;
//    }
//
//}
