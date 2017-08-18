package org.jcker.web;

import com.alibaba.fastjson.JSONObject;
import org.jcker.database.h2.DbUtil;
import org.jcker.framework.utils.PropertiesUtil;
import org.jcker.smartqq.constant.QQConstant;
import org.jcker.web.dao.GroupMessageDao;
import org.jcker.web.dao.impl.GroupMessageDaoImpl;
import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Alan Turing on 2017/7/3.
 *
 */
@ServerEndpoint("/groupmessage")
public class MessageService implements MessageCallback {
    @Autowired
    private GroupMessageDao groupMessageDao = new GroupMessageDaoImpl();

    private SmartQQClient client;
    private Session session;
    private static final CopyOnWriteArraySet<MessageService> webSocketSet = new CopyOnWriteArraySet<MessageService>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    public void onMessage(Message message){
        try {
//            messageDao.createMessage(message);
            Connection conn = DbUtil.getConnection();
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO message(time,userId,content)VALUES (" + message.getTime() + "," + message.getUserId() + ",'" + message.getContent() + "')");
            conn.close();

            System.out.println(message.getUserId() + " : " + message.getContent());
            String msg = message.getContent();
            if (msg.indexOf(QQConstant.ADD_KEY)==0) {
                PropertiesUtil.addValueToProperty("qq.interests.tags", msg.replace(QQConstant.ADD_KEY, ""));
                System.out.println("QQConstant.ADD_KEY = " + QQConstant.ADD_KEY);
            }
            if (msg.indexOf(QQConstant.DELETE_KEY)==0) {
                PropertiesUtil.delValueToProperty("qq.interests.tags", msg.replace(QQConstant.DELETE_KEY,""));
                System.out.println("QQConstant.DELETE_KEY = " + QQConstant.DELETE_KEY);
            }
            if (msg.indexOf(QQConstant.PRINT_KEY) == 0) {
                System.out.println("QQConstant.PRINT_KEY = " + QQConstant.PRINT_KEY);
                client.sendMessageToFriend(message.getUserId(), PropertiesUtil.printProperties());
            }
            if (msg.indexOf(QQConstant.QA) == 0) {

                System.out.println("QQConstant.QA = " + QQConstant.QA);
            }
        }catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }

    }

    @OnMessage
    public void onGroupMessage(String jsonString) {
        GroupMessage groupMessage = new GroupMessage(jsonString);
        String fmtStr = "";
        if(StringUtils.isEmpty(fmtStr = groupMessage.getContent().replaceAll("\\[\"face\",\\d++\\]",""))){
            //过滤表情后为空的信息不发送到论坛。
            return;
        }
        groupMessage.setContent(fmtStr);
        //save group message to database.

        try {
            System.out.println("groupMessageDao = " + groupMessageDao);
            groupMessageDao.createGroupMessage(groupMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(groupMessage.getGroupId() + " : " + groupMessage.getUserId() + " : " + fmtStr);
        sendToCommunity(groupMessage, webSocketSet);
/*        if (true) {
            String botAnswer = HttpHelper.sendPost(TuringConstant.API_URL, "userid=" + groupMessage.getUserId() + "&info=" + fmtStr);
            client.sendMessageToGroup(groupMessage.getGroupId(),JSON.parseObject(botAnswer, TuringMessage.class).getText());

        }*/
    }

    public void onDiscussMessage(DiscussMessage discussMessage) {
        System.out.println(discussMessage.getDiscussId() + " : " + discussMessage.getUserId() + " : " + discussMessage.getContent());
    }

    @OnError
    public void onError(Throwable throwable) throws IOException{
        System.out.println("ws closed................");
    }

    public static void sendToCommunity(GroupMessage groupMessage, CopyOnWriteArraySet<MessageService> webSocketSet){
        Map map = new HashMap();
        map.put("groupId", groupMessage.getGroupId());
        map.put("userId", groupMessage.getUserId());
        map.put("content", groupMessage.getContent());
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        for (MessageService session : webSocketSet) {
            try {
                if (session.session.isOpen()) {
                    session.session.getBasicRemote().sendText(jsonObject.toString());
                } else {
                    webSocketSet.remove(session);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("PropertiesUtil.printProperties() = " + PropertiesUtil.printProperties());

/*        String botAnswer = HttpHelper.sendPost("http://www.tuling123.com/openapi/api?key=7891e8bbdf5b4315a35acf9a0bedfafa", "userid=" + "123123" + "&info=" + "管理员");
        System.out.println("botAnswer = " + botAnswer);*/
    }
}
