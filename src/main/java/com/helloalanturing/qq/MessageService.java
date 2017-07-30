package com.helloalanturing.qq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.helloalanturing.framework.utils.HttpHelper;
import com.helloalanturing.framework.utils.PropertiesUtil;
import com.helloalanturing.qq.constant.QQConstant;
import com.helloalanturing.qq.controller.Launcher;
import com.helloalanturing.turingbot.constant.TuringConstant;
import com.helloalanturing.turingbot.entity.TuringMessage;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Alan Turing on 2017/7/3.
 *
 */
@ServerEndpoint("/groupmessage")
public class MessageService implements MessageCallback {
    private SmartQQClient client;
    private Session session;
    private static final CopyOnWriteArraySet<MessageService> webSocketSet = new CopyOnWriteArraySet<MessageService>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("MessageService.onOpen");
        webSocketSet.add(this);
    }

    public void onMessage(Message message) {
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
    }

    @OnMessage
    public void onGroupMessage(String jsonString) {
        GroupMessage groupMessage = new GroupMessage(jsonString);
        String fmtStr = "";
        if(StringUtils.isEmpty(fmtStr = groupMessage.getContent().replaceAll("\\[\"face\",\\d++\\]",""))){
            return;
        }
        groupMessage.setContent(fmtStr);
        System.out.println(groupMessage.getGroupId() + " : " + groupMessage.getUserId() + " : " + fmtStr);

        boolean flag = false;
        String returnTag = "";
        for (String tag : PropertiesUtil.getTags()){
            if (StringUtils.containsIgnoreCase(groupMessage.getContent(),tag)){
                flag = true;
                returnTag = tag;
                break;
            }
        }
        if (flag) {
            String botAnswer = HttpHelper.sendPost(TuringConstant.API_URL, "userid=" + groupMessage.getUserId() + "&info=" + fmtStr);
            client.sendMessageToGroup(groupMessage.getGroupId(),JSON.parseObject(botAnswer, TuringMessage.class).getText());
            //send message to community.
            sendToCommunity(groupMessage, returnTag, webSocketSet);
        }
    }
    public void onDiscussMessage(DiscussMessage discussMessage) {
        System.out.println(discussMessage.getDiscussId() + " : " + discussMessage.getUserId() + " : " + discussMessage.getContent());
    }

    @OnError
    public void onError(Throwable throwable) throws IOException{
        System.out.println("ws closed................");
    }

    public static void sendToCommunity(GroupMessage groupMessage, String returnTag, CopyOnWriteArraySet<MessageService> webSocketSet){
        Map map = new HashMap();
        map.put("groupId", groupMessage.getGroupId());
        map.put("username", groupMessage.getUserId());
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
