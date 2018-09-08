/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.websocket;

import com.google.gson.Gson;
import entity.InformPK;
import entity.Message;
import entity.MessagePK;
import entity.User;
import entity.Inform;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import session.InformFacade;
import session.MessageFacade;
import session.UserFacade;

/**
 *
 * @author Dawson
 * @ServerEndpoint该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
 */
//
@ServerEndpoint("/talkSocket/{userId}/{talkTo}")
@Dependent
public class TalkSocket {

    //静态变量，用来记录每个直播间对应的MyWebSocketSet对象
    private static ConcurrentHashMap<String, TalkSocket> talkMap = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;
    private String userId;
    private String talkTo;

    @EJB
    private MessageFacade messageFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private InformFacade informFacade;

    /**
     * 连接建立成功调用的方法
     *
     *
     * @param userId
     * @param talkTo
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws java.io.IOException
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, @PathParam("talkTo") String talkTo, Session session) throws IOException {

        this.session = session;
        this.userId = userId;
        this.talkTo = talkTo;
        User u1 = this.userFacade.find(this.userId);
        User u2 = this.userFacade.find(this.talkTo);
        if (u1 == null || u2 == null) {
            MessageJson errorJson = new MessageJson();
            MessageJson.MsgJson msgJson = new MessageJson.MsgJson();
            errorJson.setType("message");
            msgJson.setDateTime(new Date());
            msgJson.setWhoSpeak("system");
            msgJson.setMsg("会话用户不存在:错误信息userId====" + u1 + "*********talkTo====" + u2);
            errorJson.getBody().add(msgJson);
            String errorMsg = new Gson().toJson(errorJson);
            session.getBasicRemote().sendText(errorMsg);
        } else {
            if (!talkMap.containsKey(userId)) {
                talkMap.put(userId, this);
            }
            List<Message> messageList = this.messageFacade.findByUserIdAndTalkTo(userId, talkTo);
            if (messageList == null) {
            } else {
                String text = this.jsonMessageList(messageList);
                session.getBasicRemote().sendText(text);
            }
            userIdConsumerNewMessage();
        }
        System.out.println("有新连接加入！当前" + userId + "发起与" + talkTo + "的对话");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (talkMap.containsKey(userId)) {
            talkMap.remove(userId);
            System.out.println("有一连接关闭！当前" + userId + "退出对话");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自" + this.userId + "的消息:" + message);
        MessageJson messageJson = new Gson().fromJson(message, MessageJson.class);
        //System.out.println(messageBean.getBody().get(0).getMsg() + "==============Gson");
        //持久化数据要保存两份  ，一分存在UserId中一份存在TalkTo中
        if (userId == null ? talkTo != null : !userId.equals(talkTo)) {
            storageEntity(userId, talkTo, messageJson);
            storageEntity(talkTo, userId, messageJson);
        } else {
            storageEntity(userId, talkTo, messageJson);
        }
        tellTalkToHasNewMessage();
        /**
         * 如果talkTo在线,并且talkTo正在和userId通话， 且userId和talkTo不是同一个人（即不是和自己通话）
         * 那就立即给他发信息,
         */
        if (talkMap.containsKey(this.talkTo)
                && talkMap.get(this.talkTo).talkTo.equals(userId)
                && (userId == null ? talkTo != null : !userId.equals(talkTo))) {
            talkMap.get(this.talkTo).sendMessage(message);
            userIdConsumerNewMessage();
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);  
    }

    /*
     * One list message to start
     * {
     *     "type":"list", 
     *     "body":
     *        [ 
     *            {"dateTime":"dateTime1", "whoSpeak":"userName1", "msg":"msg1"}, 
     *            {"dateTime":"dateTime2", "whoSpeak":"userName2", "msg":"msg2"}, 
     *             ...
     *        ]
     *
     *  }
     */
    private String jsonMessageList(List<Message> messageList) {
        MessageJson messageJson = new MessageJson();
        List<MessageJson.MsgJson> body = new ArrayList<>();
        messageJson.setType("message");
        for (Message m : messageList) {
            MessageJson.MsgJson msgJson = new MessageJson.MsgJson();
            msgJson.setDateTime(m.getMessagePK().getDatetime());
            msgJson.setWhoSpeak(m.getWhospeak());
            msgJson.setMsg(m.getMsg());
            body.add(msgJson);
        }
        messageJson.setBody(body);
        return new Gson().toJson(messageJson);

//        StringWriter swriter = new StringWriter();
//        try (JsonGenerator gen = Json.createGenerator(swriter)) {
//            gen.writeStartObject();
//            gen.write("type", "message");
//            gen.writeStartArray("body");
//            for (Message message : messageList) {
//                gen.writeStartObject();
//                gen.write("datetime", message.getMessagePK().getDatetime().toString());
//                gen.write("whoSpeak", message.getWhospeak());
//                gen.write("msg", message.getMsg());
//                gen.writeEnd();
//            }
//            gen.writeEnd();
//            gen.writeEnd();
//        }
//        return swriter.toString();
    }

    private void tellTalkToHasNewMessage() {
        InformPK informPK = new InformPK();
        informPK.setUserid(talkTo);
        informPK.setTalkTo(userId);
        Inform inform = this.informFacade.find(informPK);
        if (inform == null) {
            inform = new Inform();
            inform.setInformPK(informPK);
            //告诉TalkTo你有新消息
            inform.setNewMessage(Short.valueOf("1"));
            this.informFacade.create(inform);
        } else {
            //告诉TalkTo你有新消息
            inform.setNewMessage(Short.valueOf("1"));
            this.informFacade.edit(inform);
        }
    }

    private void userIdConsumerNewMessage() {
        InformPK informPK = new InformPK();
        informPK.setUserid(userId);
        informPK.setTalkTo(talkTo);
        Inform inform = this.informFacade.find(informPK);
        if (inform == null) {
            inform = new Inform();
            inform.setInformPK(informPK);
            //告诉TalkTo你有新消息
            inform.setNewMessage(Short.valueOf("0"));
            this.informFacade.create(inform);
        } else {
            //告诉TalkTo你有新消息
            inform.setNewMessage(Short.valueOf("0"));
            this.informFacade.edit(inform);
        }
    }

    private void storageEntity(String userId, String talkTo, MessageJson messageJson) {
        if ("message".equals(messageJson.getType())) {
            for (MessageJson.MsgJson msgJson : messageJson.getBody()) {
                MessagePK msgPK = new MessagePK();
                Message msg = new Message();
                msgPK.setUserid(userId);
                msgPK.setTalkTo(talkTo);
                msgPK.setDatetime(msgJson.getDateTime());
                msg.setMessagePK(msgPK);
                msg.setWhospeak(msgJson.getWhoSpeak());
                if (msgJson.getMsg() == null
                        || msgJson.getMsg().equals("")) {
                    msg.setMsg("null");
                } else {
                    msg.setMsg(msgJson.getMsg());
                }
                if (this.messageFacade.find(msgPK) == null) {
                    this.messageFacade.create(msg);
                } else {
                    System.out.println(getTimeNow() + "数据已经存在");
                }
            }
        } else {
            System.out.println(getTimeNow() + "该json文件的type不是message");
        }
    }

    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

}
