package org.jcker.smartqq;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.Discuss;
import org.jcker.smartqq.model.DiscussInfo;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.DiscussUser;
import org.jcker.smartqq.model.Friend;
import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupInfo;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.GroupUser;
import org.jcker.smartqq.model.Message;

public class Receiver
{
  private static List<Friend> friendList = new ArrayList();
  private static List<Group> groupList = new ArrayList();
  private static List<Discuss> discussList = new ArrayList();
  private static Map<Long, Friend> friendFromID = new HashMap();
  private static Map<Long, Group> groupFromID = new HashMap();
  private static Map<Long, GroupInfo> groupInfoFromID = new HashMap();
  private static Map<Long, Discuss> discussFromID = new HashMap();
  private static Map<Long, DiscussInfo> discussInfoFromID = new HashMap();
  private static boolean working;
  private static SmartQQClient client = new SmartQQClient(new MessageCallback()
  {
    public void onMessage(Message msg)
    {
      if (!Receiver.working)
        return;
      try
      {
        System.out.println("[" + "] [私聊] " + Receiver.getFriendNick(msg) + "：" + msg.getContent());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void onGroupMessage(GroupMessage msg)
    {
      if (!Receiver.working)
        return;
      try
      {
        System.out.println("[" + "] [" + Receiver.getGroupName(msg) + "] " + Receiver.getGroupUserNick(msg) + "：" + msg.getContent());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void onDiscussMessage(DiscussMessage msg)
    {
      if (!Receiver.working)
        return;
      try
      {
        System.out.println("[" + "] [" + Receiver.getDiscussName(msg) + "] " + Receiver.getDiscussUserNick(msg) + "：" + msg.getContent());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  });

  private static String getTime()
  {
    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return time.format(new Date());
  }

  private static GroupInfo getGroupInfoFromID(Long id)
  {
    if (!groupInfoFromID.containsKey(id)) {
      groupInfoFromID.put(id, client.getGroupInfo(((Group)groupFromID.get(id)).getCode()));
    }
    return (GroupInfo)groupInfoFromID.get(id);
  }

  private static DiscussInfo getDiscussInfoFromID(Long id)
  {
    if (!discussInfoFromID.containsKey(id)) {
      discussInfoFromID.put(id, client.getDiscussInfo(((Discuss)discussFromID.get(id)).getId()));
    }
    return (DiscussInfo)discussInfoFromID.get(id);
  }

  private static String getGroupName(GroupMessage msg)
  {
    return getGroup(msg).getName();
  }

  private static String getDiscussName(DiscussMessage msg)
  {
    return getDiscuss(msg).getName();
  }

  private static Group getGroup(GroupMessage msg)
  {
    return (Group)groupFromID.get(Long.valueOf(msg.getGroupId()));
  }

  private static Discuss getDiscuss(DiscussMessage msg)
  {
    return (Discuss)discussFromID.get(Long.valueOf(msg.getDiscussId()));
  }

  private static String getFriendNick(Message msg)
  {
    Friend user = (Friend)friendFromID.get(Long.valueOf(msg.getUserId()));
    if ((user.getMarkname() == null) || (user.getMarkname().equals(""))) {
      return user.getNickname();
    }
    return user.getMarkname();
  }

  private static String getGroupUserNick(GroupMessage msg)
  {
    for (GroupUser user : getGroupInfoFromID(Long.valueOf(msg.getGroupId())).getUsers()) {
      if (user.getUin() == msg.getUserId()) {
        if ((user.getCard() == null) || (user.getCard().equals(""))) {
          return user.getNick();
        }
        return user.getCard();
      }
    }

    return "系统消息";
  }

  private static String getDiscussUserNick(DiscussMessage msg)
  {
    for (DiscussUser user : getDiscussInfoFromID(Long.valueOf(msg.getDiscussId())).getUsers()) {
      if (user.getUin() == msg.getUserId()) {
        return user.getNick();
      }
    }
    return "系统消息";
  }

  public static void main(String[] args)
  {
    working = false;
    friendList = client.getFriendList();
    groupList = client.getGroupList();
    discussList = client.getDiscussList();
    for (Friend friend : friendList) {
      friendFromID.put(Long.valueOf(friend.getUserId()), friend);
    }
    for (Group group : groupList) {
      groupFromID.put(Long.valueOf(group.getId()), group);
    }
    for (Discuss discuss : discussList) {
      discussFromID.put(Long.valueOf(discuss.getId()), discuss);
    }
    working = true;
  }
}
