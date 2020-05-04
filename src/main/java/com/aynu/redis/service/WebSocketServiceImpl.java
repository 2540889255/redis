package com.aynu.redis.service;

import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author admin
 */

@Service
@ServerEndpoint("/ws")
public class WebSocketServiceImpl {

    /**
     * 静态变量，用来记录当前的连接数 应该设置成线程安全的
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全的Set，用来存放每个客户端对应的webSocketServiceImpl
     */
    private static CopyOnWriteArrayList<WebSocketServiceImpl> webSocketServices=new CopyOnWriteArrayList<>();
    /**
     * 与某个客户端的连接绘画 需要通过他来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketServices.add(this);
        addOnlineCount();
        System.out.println("有新的连接加入！当前的连接数为"+getOnlineCount());
        try {
            senMessage("有新的连接加入了");
        }catch (IOException e){
            System.out.println("IO异常");
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketServices.remove(this);
        subOnlineCount();
        System.out.println("有一个连接关闭,当前的连接人数为"+getOnlineCount());
    }

    /**
     * 接收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println("来自客户端的消息");
        //群发消息
        for (WebSocketServiceImpl serviceImpl:webSocketServices
             ) {
            /**
             * 获取当前用户的名称
             */
            serviceImpl.senMessage(message);
        }
    }



    /**
     * 发送消息
     * @param message
     */
    private void senMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 返回在线人数
     * @return
     */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 当连接人数增加时
     */
    private static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 当连接人数减少时
     */
    private static synchronized void subOnlineCount() {
        onlineCount--;
    }

}
