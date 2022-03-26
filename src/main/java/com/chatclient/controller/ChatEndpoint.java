package com.chatclient.controller;

import com.chatclient.entity.Message;
import com.chatclient.entity.Messageprivate;
import com.chatclient.entity.Messagepublic;
import com.chatclient.mapper.MessageprivateMapper;
import com.chatclient.mapper.MessagepublicMapper;
import com.chatclient.util.GetHttpSeeionconfig;
import com.chatclient.util.MessageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/say", configurator = GetHttpSeeionconfig.class)
@Component
public class ChatEndpoint {
    private final static Logger LOGGER = LogManager.getLogger(ChatEndpoint.class);
    /**
     * 用来储存在线用户的容器
     */
    public static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();
    public static HashMap<String,List<String>> onlineGroups = new HashMap<>();
    /**
     * 用来给客户端发送消息
     */
    private Session session;

    /**
     * 用来获取在登录成功后，放在httpsession域中存放的username
     */
    private HttpSession httpSession;
    //注入对象
    private static MessageprivateMapper messageprivateMapper;
    private static MessagepublicMapper messagepublicMapper;
    @Autowired
    public void setMessageprivateMapper(MessageprivateMapper messageprivateMapper){
        ChatEndpoint.messageprivateMapper=messageprivateMapper;
    }
    @Autowired
    public void setMessagepublicMapper(MessagepublicMapper messagepublicMapper){
        ChatEndpoint.messagepublicMapper=messagepublicMapper;
    }

    /*建立时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOGGER.info(session.getId()+"建立连接");
        this.session = session;
        //获取httpsession对象
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //获取httpsession域中存放的name对应的值
        String username = (String) httpSession.getAttribute("name");


        //输出session里面的所有值
        Enumeration enumeration = httpSession.getAttributeNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement().toString();
            Object value = httpSession.getAttribute(name);
            System.out.println(name+":"+value);
        }

        //存放到onlineUsers中保存
        onlineUsers.put(username, this);
        //系统消息推送所有在线用户给客户端
        //封装系统推送消息,前端onmessage接收的数据
        String message = MessageUtils.getMessage(true, null, "1",getAllOnlineUsername());
        broadcastMsgToAllOnlineUsers(message);
    }

    /**
     * .
     * @param message 给客户端群发上线通知
     * @return void
     */
    private void broadcastMsgToAllOnlineUsers(String message) {
        //所有登录用户名称
        Set<String> names = onlineUsers.keySet();
        for (String name : names) {
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            //获取推送对象
            RemoteEndpoint.Basic basicRemote = chatEndpoint.session.getBasicRemote();
            try {
                basicRemote.sendText(message);
            } catch (IOException e) {
                LOGGER.error("广播发送系统消息失败！{}", e);
                e.printStackTrace();
            }
        }
    }
    private void sendMsgToAllOnlinequn(String toName,String message){
        List<String > names=onlineGroups.get(toName);
        for(String name :names){
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            RemoteEndpoint.Basic basicRemote = chatEndpoint.session.getBasicRemote();
            try {
                basicRemote.sendText(message);
            } catch (IOException e) {
                LOGGER.error("广播发送系统消息失败！{}", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取发送给客户端的消息，消息数据示例值：{“systemMsgFlag”: true, "fromName": null, "message": ["Name1", "Name2"]}.
     * @return java.lang.Object
     */
    private Object getAllOnlineUsername() {
        return ChatEndpoint.onlineUsers.keySet();
    }



    /**
     * 接收到客户端发送的数据时调用.
     * @param message 客户端发送的数据
     * @param session session对象
     * @return void
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message msg = objectMapper.readValue(message, Message.class);
            //获取接收信息的用户
            String toName = msg.getToName();
            //获取发送的消息
            String msgData = msg.getMessage();
            //获取发送消息的用户
            String fromName = msg.getFromName();
            //获取当前登录的用户
            String username = (String) httpSession.getAttribute("name");
            //获取发送是图片还是文字 1为文字，2为图片，3为群聊文字，4为群聊图片，5为进入群聊
            String code = msg.getCode();
            //封装发送的消息
            String sendMsg = MessageUtils.getMessage(false,fromName,code,msgData);
            //发送消息
           //如果是私聊则发送给接受者
            if(code.equals("1")||code.equals("2"))
            {
                onlineUsers.get(toName).session.getBasicRemote().sendText(sendMsg);
            }
            //如果是文字，则存储到数据库中
            if(code.equals("1")) {
                Messageprivate messageprivate = new Messageprivate();
                messageprivate.setToname(toName);
                messageprivate.setFromname(fromName);
                messageprivate.setContent(msgData);
                messageprivateMapper.insert(messageprivate);
            }
            //如果是群聊则发送给群所有人
            if(code.equals("3")||code.equals("4"))
            {
                System.out.println(sendMsg);
                String message1 = MessageUtils.getMessage(false, fromName, code,msgData);
                sendMsgToAllOnlinequn(toName,message1);
            }
            if(code.equals("3"))
            {
                int qunhao = Integer.parseInt(toName);
                Messagepublic messagepublic = new Messagepublic();
                messagepublic.setSender(fromName);
                messagepublic.setFromgroup(qunhao);
                messagepublic.setContent(msgData);
                messagepublicMapper.insert(messagepublic);
            }
            if(code.equals("5"))
            {
                //如果当前群组没有在线用户则添加数组
                if(onlineGroups.get(toName)==null)
                {
                    List<String> cac = new ArrayList<>();
                    cac.add(fromName);
                    onlineGroups.put(toName,cac);
                }
                //如果当前群组没有此用户则添加
                else if(!onlineGroups.get(toName).contains(fromName))
                {
                    onlineGroups.get(toName).add(fromName);
                }
                //创建一个集合，存储群聊id里已加入的用户
                LOGGER.info(fromName+"加入"+toName+"群聊");
                LOGGER.info("当前"+toName+"在线用户"+onlineGroups.get(toName));
            }
            //System.out.println(toName+"\n"+sendMsg+"\n"+fromName);
        } catch (JsonProcessingException e) {
            LOGGER.error("接收客户端的消息，转换出错了！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**关闭时调用*/
    @OnClose
    public void onClose(Session session) {
        String username = (String) httpSession.getAttribute("name");
        //
        ChatEndpoint remove = onlineUsers.remove(username);
        LOGGER.info(session.getId()+"关闭连接");
        //广播
        String message = MessageUtils.getMessage(true, null, "1",getAllOnlineUsername());
        broadcastMsgToAllOnlineUsers(message);
    }

}