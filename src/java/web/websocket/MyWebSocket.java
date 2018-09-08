/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.websocket;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.enterprise.context.Dependent;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Dawson
 * @ServerEndpoint该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
 */
//
//@ServerEndpoint("/websocket")
@ServerEndpoint("/websocket/{liveRoomId}")
@Dependent
public class MyWebSocket {

    //静态变量，用来记录每个直播间对应的MyWebSocketSet对象
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<MyWebSocket>> liveRoomMap = new ConcurrentHashMap<>();
//    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。  
//    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识  
//    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;
    private String liveRoomId;

    /**
     * 连接建立成功调用的方法
     *
     *
     * @param liveRoomId
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("liveRoomId") String liveRoomId, Session session) throws IOException {
        this.session = session;
        this.liveRoomId = liveRoomId;
        if (liveRoomId != null && !"".equals(liveRoomId)) {
            if (liveRoomMap.containsKey(liveRoomId)) {
                liveRoomMap.get(liveRoomId).add(this);
            } else {
                CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();
                liveRoomMap.put(liveRoomId, webSocketSet);
                liveRoomMap.get(liveRoomId).add(this);
            }
            LiveroomJson liveroomJson = new LiveroomJson();
            liveroomJson.setWhoSpeak("system");
            liveroomJson.setDateTime(new Date());
            liveroomJson.setMsg("链接成功,欢迎加入" + liveRoomId + "直播间！");
            liveroomJson.setPersonNumber(liveRoomMap.get(liveRoomId).size());
            String text = new Gson().toJson(liveroomJson);
            this.session.getBasicRemote().sendText(text);
//        webSocketSet.add(this); // 加入set中  
//        addOnlineCount(); // 在线数加1  
            System.out.println("有新连接加入！当前" + liveRoomId + "在线人数为" + liveRoomMap.get(liveRoomId).size());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (liveRoomMap.containsKey(liveRoomId) && liveRoomMap.get(liveRoomId).size() > 0) {
            liveRoomMap.get(liveRoomId).remove(this);
            System.out.println("有一连接关闭！当前" + liveRoomId + "在线人数为" + liveRoomMap.get(liveRoomId).size());
            if (liveRoomMap.get(liveRoomId).isEmpty()) {
                liveRoomMap.remove(liveRoomId);
            }
//        webSocketSet.remove(this); // 从set中删除  
//        subOnlineCount(); // 在线数减1   
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        // 群发消息
        if (liveRoomMap.containsKey(liveRoomId)) {
            LiveroomJson liveroomJson = new Gson().fromJson(message, LiveroomJson.class);
            liveroomJson.setPersonNumber(liveRoomMap.get(liveRoomId).size());
            String text = new Gson().toJson(liveroomJson);
            for (MyWebSocket item : liveRoomMap.get(liveRoomId)) {
                try {
                    item.sendMessage(text);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
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

//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        MyWebSocket.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        MyWebSocket.onlineCount--;
//    }
}
