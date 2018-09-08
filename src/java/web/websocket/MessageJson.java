/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.websocket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dawson
 */
public class MessageJson {

    private String type;
    private List<MsgJson> body=new ArrayList<>();

    public static class MsgJson {

        private Date dateTime;
        private String whoSpeak;
        private String msg;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MsgJson> getBody() {
        return body;
    }

    public void setBody(List<MsgJson> body) {
        this.body = body;
    }
    
    
 

}
