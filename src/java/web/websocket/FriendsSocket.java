package web.websocket;

import com.google.gson.Gson;
import entity.Message;
import entity.MessagePK;
import entity.User;
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
import session.MessageFacade;
import session.UserFacade;

/**
 *
 * @author Dawson
 * @ServerEndpoint该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
 */
//
@ServerEndpoint("/friendsSocket/{userId}")
@Dependent
public class FriendsSocket {

    //静态变量，用来记录每个直播间对应的MyWebSocketSet对象
    private static ConcurrentHashMap<String, FriendsSocket> friendsMap = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;
    private String userId;

    @EJB
    private MessageFacade messageFacade;
    @EJB
    private UserFacade userFacade;

    /**
     * 连接建立成功调用的方法
     *
     * @param userId
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws java.io.IOException
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException {
        this.session = session;
        this.userId = userId;
        User u1 = this.userFacade.find(this.userId);
        if (u1 == null) {
            //错误信息处理
            MessageJson errorJson = new MessageJson();
            MessageJson.MsgJson msgJson = new MessageJson.MsgJson();
            errorJson.setType("message");
            msgJson.setDateTime(new Date());
            msgJson.setWhoSpeak("system");
            msgJson.setMsg("会话用户不存在:错误信息userId====" + u1);
            errorJson.getBody().add(msgJson);
            String errorMsg = new Gson().toJson(errorJson);
            session.getBasicRemote().sendText(errorMsg);
        }
        if (!friendsMap.containsKey(userId)) {
            friendsMap.put(userId, this);
        }
        
        System.out.println("有新连接加入！当前" + userId + "正在登陆学友圈");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (friendsMap.containsKey(userId)) {
            friendsMap.remove(userId);
            System.out.println("有一连接关闭！当前" + userId + "退出朋友圈页面");
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
//        System.out.println("来自" + this.userId + "的消息:" + message);
//        /**
//         * 如果talkTo在线,并且talkTo正在和userId通话， 且userId和talkTo不是同一个人（即不是和自己通话）
//         * 那就立即给他发信息,
//         */
//        if (talkMap.containsKey(this.talkTo)
//                && talkMap.get(this.talkTo).talkTo.equals(userId)
//                && (userId == null ? talkTo != null : !userId.equals(talkTo))) {
//            talkMap.get(this.talkTo).sendMessage(message);
//        }
//        MessageJson messageJson = new Gson().fromJson(message, MessageJson.class);
//        //System.out.println(messageBean.getBody().get(0).getMsg() + "==============Gson");
//        //持久化数据要保存两份  ，一分存在UserId中一份存在TalkTo中
//        if (userId == null ? talkTo != null : !userId.equals(talkTo)) {
//            storageEntity(userId, talkTo, messageJson);
//            storageEntity(talkTo, userId, messageJson);
//        } else {
//            storageEntity(userId, talkTo, messageJson);
//        }
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




    private String getTimeNow() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = "[" + df.format(day) + "]";
        return timeNow;
    }

}
