/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.Liveroom;
import session.LiveroomFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Dawson
 */
@Named(value = "liveRoomController")
@SessionScoped
public class LiveRoomController implements Serializable {

    private String liveRoomId;
    private Liveroom liveroom = new Liveroom();

    @EJB
    private LiveroomFacade liveroomFacade = new LiveroomFacade();

    /**
     * Creates a new instance of LiveRoomController
     */
    public LiveRoomController() {
        System.out.println(this.getTimeNow() + "[LiveRoomController()]");
//        PrimeFaces.current().executeScript("var liveRoomId=" + liveRoomId);
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

    public void startLiveroom() {
        liveroom = liveroomFacade.find(liveRoomId);
        if (liveroom == null) {
            FacesMessage msg = new FacesMessage("没有找到有效的直播间", "您会看不到直播");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            System.out.println(getTimeNow() + "[LiveRoomController.startLiveroom()]"+liveroom);
        }
        System.out.println(getTimeNow() + "[LiveRoomController.startLiveroom()]");
    }
    
    

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public Liveroom getLiveroom() {
        return liveroom;
    }

    public void setLiveroom(Liveroom liveroom) {
        this.liveroom = liveroom;
    }

}
