/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.websocket;

import java.util.Date;

/**
 *
 * @author Dawson
 */
public class LiveroomJson {

    private int personNumber;
    private Date dateTime;
    private String whoSpeak;
    private String msg;
    

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }


    
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getWhoSpeak() {
        return whoSpeak;
    }

    public void setWhoSpeak(String whoSpeak) {
        this.whoSpeak = whoSpeak;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
