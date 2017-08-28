package org.jcker.smartqq.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.dongliu.requests.Client;
import net.dongliu.requests.HeadOnlyRequestBuilder;
import net.dongliu.requests.PooledClientBuilder;
import net.dongliu.requests.PostRequestBuilder;
import net.dongliu.requests.Response;
import net.dongliu.requests.Session;
import net.dongliu.requests.exception.RequestException;
import net.dongliu.requests.struct.Cookie;
import net.dongliu.requests.struct.Cookies;
import org.apache.log4j.Logger;
import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.constant.ApiURL;
import org.jcker.smartqq.model.Category;
import org.jcker.smartqq.model.Discuss;
import org.jcker.smartqq.model.DiscussInfo;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.DiscussUser;
import org.jcker.smartqq.model.Font;
import org.jcker.smartqq.model.Friend;
import org.jcker.smartqq.model.FriendStatus;
import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupInfo;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.GroupUser;
import org.jcker.smartqq.model.Message;
import org.jcker.smartqq.model.Recent;
import org.jcker.smartqq.model.UserInfo;

public class SmartQQClient
        implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(SmartQQClient.class);

    private static int retryTimesOnFailed = 3;

    private static long MESSAGE_ID = 43690001L;
    private static final long Client_ID = 53999199L;
    private static final long RETRY_TIMES = 5L;
    private Client client;
    private Session session;
    private String qrsig;
    private String ptwebqq;
    private String vfwebqq;
    private long uin;
    private String psessionid;
    private volatile boolean pollStarted;
    public static String filePath = "";

    public SmartQQClient(final MessageCallback callback) {
        this.client = Client.pooled().maxPerRoute(5).maxTotal(10).build();
        this.session = this.client.session();
        login();
        if (callback != null) {
            this.pollStarted = true;
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (!SmartQQClient.this.pollStarted)
                            return;
                        try {
                            SmartQQClient.this.pollMessage(callback);
                        } catch (RequestException e) {
                            if (!(e.getCause() instanceof SocketTimeoutException))
                                SmartQQClient.LOGGER.error(e.getMessage());
                        } catch (Exception e) {
                            SmartQQClient.LOGGER.error(e.getMessage());
                        }
                    }
                }
            }).start();
        }
    }

    private void login() {
        getQRCode();
        String url = verifyQRCode();
        getPtwebqq(url);
        getVfwebqq();
        getUinAndPsessionid();
        getFriendStatus();

        UserInfo userInfo = getAccountInfo();
        LOGGER.info(userInfo.getNick() + "，欢迎！");
    }

    private void getQRCode() {
        LOGGER.debug("开始获取二维码");
        try {
            filePath = new File("qrcode.png").getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException("二维码保存失败");
        }
        Response response = this.session.get(ApiURL.GET_QR_CODE.getUrl())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                .file(filePath);

        for (Cookie cookie : response.getCookies()) {
            if (Objects.equals(cookie.getName(), "qrsig")) {
                this.qrsig = (cookie.getValue());
                break;
            }
        }
        LOGGER.info("二维码已保存在 " + filePath + " 文件中，请打开手机QQ并扫描二维码");
    }

    private static int hash33(String s) {
        int e = 0;
        int n = s.length();
        for (int i = 0; n > i; i++)
            e += (e << 5) + s.charAt(i);
        return 0x7FFFFFFF & e;
    }

    private String verifyQRCode() {
        LOGGER.debug("等待扫描二维码");
        while (true) {
            sleep(1L);
            Response response = get(ApiURL.VERIFY_QR_CODE, new Object[]{Integer.valueOf(hash33(this.qrsig))});
            String result = (String) response.getBody();
            if (result.contains("成功")) {
                for (String content : result.split("','"))
                    if (content.startsWith("http")) {
                        LOGGER.info("正在登录，请稍后");

                        return content;
                    }
            } else if (result.contains("已失效")) {
                LOGGER.info("二维码已失效，尝试重新获取二维码");
                getQRCode();
            }
        }
    }

    private void getPtwebqq(String url) {
        LOGGER.debug("开始获取ptwebqq");

        Response response = get(ApiURL.GET_PTWEBQQ, new Object[]{url});
        this.ptwebqq = ((String) ((Cookie) response.getCookies().get("ptwebqq").iterator().next()).getValue());
    }

    private void getVfwebqq() {
        LOGGER.debug("开始获取vfwebqq");

        Response response = get(ApiURL.GET_VFWEBQQ, new Object[]{this.ptwebqq});
        int retryTimes4Vfwebqq = retryTimesOnFailed;
        while ((response.getStatusCode() == 404) && (retryTimes4Vfwebqq > 0)) {
            response = get(ApiURL.GET_VFWEBQQ, new Object[]{this.ptwebqq});
            retryTimes4Vfwebqq--;
        }
        this.vfwebqq = getJsonObjectResult(response).getString("vfwebqq");
    }

    private void getUinAndPsessionid() {
        LOGGER.debug("开始获取uin和psessionid");

        JSONObject r = new JSONObject();
        r.put("ptwebqq", this.ptwebqq);
        r.put("clientid", Long.valueOf(53999199L));
        r.put("psessionid", "");
        r.put("status", "online");

        Response response = post(ApiURL.GET_UIN_AND_PSESSIONID, r);
        JSONObject result = getJsonObjectResult(response);
        this.psessionid = result.getString("psessionid");
        this.uin = result.getLongValue("uin");
    }

    public List<Group> getGroupList() {
        LOGGER.debug("开始获取群列表");

        JSONObject r = new JSONObject();
        r.put("vfwebqq", this.vfwebqq);
        r.put("hash", hash());

        Response response = post(ApiURL.GET_GROUP_LIST, r);
        int retryTimes4getGroupList = retryTimesOnFailed;
        while ((response.getStatusCode() == 404) && (retryTimes4getGroupList > 0)) {
            response = post(ApiURL.GET_GROUP_LIST, r);
            retryTimes4getGroupList--;
        }
        JSONObject result = getJsonObjectResult(response);
        return JSON.parseArray(result.getJSONArray("gnamelist").toJSONString(), Group.class);
    }

    private void pollMessage(MessageCallback callback) {
        LOGGER.debug("开始接收消息");

        JSONObject r = new JSONObject();
        r.put("ptwebqq", this.ptwebqq);
        r.put("clientid", Long.valueOf(53999199L));
        r.put("psessionid", this.psessionid);
        r.put("key", "");

        Response response = post(ApiURL.POLL_MESSAGE, r);
        JSONArray array = getJsonArrayResult(response);
        for (int i = 0; (array != null) && (i < array.size()); i++) {
            JSONObject message = array.getJSONObject(i);
            String type = message.getString("poll_type");
            if ("message".equals(type))
                callback.onMessage(new Message(message.getJSONObject("value")));
            else if ("group_message".equals(type))
                callback.onGroupMessage(new GroupMessage(message.getJSONObject("value")));
            else if ("discu_message".equals(type))
                callback.onDiscussMessage(new DiscussMessage(message.getJSONObject("value")));
        }
    }

    public void sendMessageToGroup(long groupId, String msg) {
        LOGGER.debug("开始发送群消息");

        JSONObject r = new JSONObject();
        r.put("group_uin", Long.valueOf(groupId));
        r.put("content", JSON.toJSONString(Arrays.asList(new Object[]{msg, Arrays.asList(new Object[]{"font", Font.DEFAULT_FONT})})));
        r.put("face", Integer.valueOf(573));
        r.put("clientid", Long.valueOf(53999199L));
        r.put("msg_id", Long.valueOf(MESSAGE_ID++));
        r.put("psessionid", this.psessionid);

        Response response = postWithRetry(ApiURL.SEND_MESSAGE_TO_GROUP, r);
        checkSendMsgResult(response);
    }

    public void sendMessageToDiscuss(long discussId, String msg) {
        LOGGER.debug("开始发送讨论组消息");

        JSONObject r = new JSONObject();
        r.put("did", Long.valueOf(discussId));
        r.put("content", JSON.toJSONString(Arrays.asList(new Object[]{msg, Arrays.asList(new Object[]{"font", Font.DEFAULT_FONT})})));
        r.put("face", Integer.valueOf(573));
        r.put("clientid", Long.valueOf(53999199L));
        r.put("msg_id", Long.valueOf(MESSAGE_ID++));
        r.put("psessionid", this.psessionid);

        Response response = postWithRetry(ApiURL.SEND_MESSAGE_TO_DISCUSS, r);
        checkSendMsgResult(response);
    }

    public void sendMessageToFriend(long friendId, String msg) {
        LOGGER.debug("开始发送消息");

        JSONObject r = new JSONObject();
        r.put("to", Long.valueOf(friendId));
        r.put("content", JSON.toJSONString(Arrays.asList(new Object[]{msg, Arrays.asList(new Object[]{"font", Font.DEFAULT_FONT})})));
        r.put("face", Integer.valueOf(573));
        r.put("clientid", Long.valueOf(53999199L));
        r.put("msg_id", Long.valueOf(MESSAGE_ID++));
        r.put("psessionid", this.psessionid);

        Response response = postWithRetry(ApiURL.SEND_MESSAGE_TO_FRIEND, r);
        checkSendMsgResult(response);
    }

    public List<Discuss> getDiscussList() {
        LOGGER.debug("开始获取讨论组列表");

        Response response = get(ApiURL.GET_DISCUSS_LIST, new Object[]{this.psessionid, this.vfwebqq});
        return JSON.parseArray(getJsonObjectResult(response).getJSONArray("dnamelist").toJSONString(), Discuss.class);
    }

    public List<Category> getFriendListWithCategory() {
        LOGGER.debug("开始获取好友列表");

        JSONObject r = new JSONObject();
        r.put("vfwebqq", this.vfwebqq);
        r.put("hash", hash());

        Response response = post(ApiURL.GET_FRIEND_LIST, r);
        JSONObject result = getJsonObjectResult(response);

        Map friendMap = parseFriendMap(result);

        JSONArray categories = result.getJSONArray("categories");
        Map categoryMap = new HashMap();
        categoryMap.put(Integer.valueOf(0), Category.defaultCategory());
        for (int i = 0; (categories != null) && (i < categories.size()); i++) {
            Category category = (Category) categories.getObject(i, Category.class);
            categoryMap.put(Integer.valueOf(category.getIndex()), category);
        }
        JSONArray friends = result.getJSONArray("friends");
        for (int i = 0; (friends != null) && (i < friends.size()); i++) {
            JSONObject item = friends.getJSONObject(i);
            Friend friend = (Friend) friendMap.get(Long.valueOf(item.getLongValue("uin")));
            ((Category) categoryMap.get(Integer.valueOf(item.getIntValue("categories")))).addFriend(friend);
        }
        return new ArrayList(categoryMap.values());
    }

    public List<Friend> getFriendList() {
        LOGGER.debug("开始获取好友列表");

        JSONObject r = new JSONObject();
        r.put("vfwebqq", this.vfwebqq);
        r.put("hash", hash());

        Response response = post(ApiURL.GET_FRIEND_LIST, r);
        return new ArrayList(parseFriendMap(getJsonObjectResult(response)).values());
    }

    private static Map<Long, Friend> parseFriendMap(JSONObject result) {
        Map friendMap = new HashMap();
        JSONArray info = result.getJSONArray("info");
        for (int i = 0; (info != null) && (i < info.size()); i++) {
            JSONObject item = info.getJSONObject(i);
            Friend friend = new Friend();
            friend.setUserId(item.getLongValue("uin"));
            friend.setNickname(item.getString("nick"));
            friendMap.put(Long.valueOf(friend.getUserId()), friend);
        }
        JSONArray marknames = result.getJSONArray("marknames");
        for (int i = 0; (marknames != null) && (i < marknames.size()); i++) {
            JSONObject item = marknames.getJSONObject(i);
            ((Friend) friendMap.get(Long.valueOf(item.getLongValue("uin")))).setMarkname(item.getString("markname"));
        }
        JSONArray vipinfo = result.getJSONArray("vipinfo");
        for (int i = 0; (vipinfo != null) && (i < vipinfo.size()); i++) {
            JSONObject item = vipinfo.getJSONObject(i);
            Friend friend = (Friend) friendMap.get(Long.valueOf(item.getLongValue("u")));
            friend.setVip(item.getIntValue("is_vip") == 1);
            friend.setVipLevel(item.getIntValue("vip_level"));
        }
        return friendMap;
    }

    public UserInfo getAccountInfo() {
        LOGGER.debug("开始获取登录用户信息");

        Response response = get(ApiURL.GET_ACCOUNT_INFO, new Object[0]);
        int retryTimes4AccountInfo = retryTimesOnFailed;
        while ((response.getStatusCode() == 404) && (retryTimes4AccountInfo > 0)) {
            response = get(ApiURL.GET_ACCOUNT_INFO, new Object[0]);
            retryTimes4AccountInfo--;
        }
        return (UserInfo) JSON.parseObject(getJsonObjectResult(response).toJSONString(), UserInfo.class);
    }

    public UserInfo getFriendInfo(long friendId) {
        LOGGER.debug("开始获取好友信息");

        Response response = get(ApiURL.GET_FRIEND_INFO, new Object[]{Long.valueOf(friendId), this.vfwebqq, this.psessionid});
        return (UserInfo) JSON.parseObject(getJsonObjectResult(response).toJSONString(), UserInfo.class);
    }

    public List<Recent> getRecentList() {
        LOGGER.debug("开始获取最近会话列表");

        JSONObject r = new JSONObject();
        r.put("vfwebqq", this.vfwebqq);
        r.put("clientid", Long.valueOf(53999199L));
        r.put("psessionid", "");

        Response response = post(ApiURL.GET_RECENT_LIST, r);
        return JSON.parseArray(getJsonArrayResult(response).toJSONString(), Recent.class);
    }

    public long getQQById(long friendId) {
        LOGGER.debug("开始获取QQ号");

        Response response = get(ApiURL.GET_QQ_BY_ID, new Object[]{Long.valueOf(friendId), this.vfwebqq});
        return getJsonObjectResult(response).getLongValue("account");
    }

    public long getQQById(Friend friend) {
        return getQQById(friend.getUserId());
    }

    public long getQQById(GroupUser user) {
        return getQQById(user.getUin());
    }

    public long getQQById(DiscussUser user) {
        return getQQById(user.getUin());
    }

    public long getQQById(Message msg) {
        return getQQById(msg.getUserId());
    }

    public long getQQById(GroupMessage msg) {
        return getQQById(msg.getUserId());
    }

    public long getQQById(DiscussMessage msg) {
        return getQQById(msg.getUserId());
    }

    public List<FriendStatus> getFriendStatus() {
        LOGGER.debug("开始获取好友状态");

        Response response = get(ApiURL.GET_FRIEND_STATUS, new Object[]{this.vfwebqq, this.psessionid});
        return JSON.parseArray(getJsonArrayResult(response).toJSONString(), FriendStatus.class);
    }

    public GroupInfo getGroupInfo(long groupCode) {
        LOGGER.debug("开始获取群资料");

        Response response = get(ApiURL.GET_GROUP_INFO, new Object[]{Long.valueOf(groupCode), this.vfwebqq});
        JSONObject result = getJsonObjectResult(response);
        GroupInfo groupInfo = (GroupInfo) result.getObject("ginfo", GroupInfo.class);

        Map groupUserMap = new HashMap();
        JSONArray minfo = result.getJSONArray("minfo");
        for (int i = 0; (minfo != null) && (i < minfo.size()); i++) {
            GroupUser groupUser = (GroupUser) minfo.getObject(i, GroupUser.class);
            groupUserMap.put(Long.valueOf(groupUser.getUin()), groupUser);
            groupInfo.addUser(groupUser);
        }
        JSONArray stats = result.getJSONArray("stats");
        for (int i = 0; (stats != null) && (i < stats.size()); i++) {
            JSONObject item = stats.getJSONObject(i);
            GroupUser groupUser = (GroupUser) groupUserMap.get(Long.valueOf(item.getLongValue("uin")));
            groupUser.setClientType(item.getIntValue("client_type"));
            groupUser.setStatus(item.getIntValue("stat"));
        }
        JSONArray cards = result.getJSONArray("cards");
        for (int i = 0; (cards != null) && (i < cards.size()); i++) {
            JSONObject item = cards.getJSONObject(i);
            ((GroupUser) groupUserMap.get(Long.valueOf(item.getLongValue("muin")))).setCard(item.getString("card"));
        }
        JSONArray vipinfo = result.getJSONArray("vipinfo");
        for (int i = 0; (vipinfo != null) && (i < vipinfo.size()); i++) {
            JSONObject item = vipinfo.getJSONObject(i);
            GroupUser groupUser = (GroupUser) groupUserMap.get(Long.valueOf(item.getLongValue("u")));
            groupUser.setVip(item.getIntValue("is_vip") == 1);
            groupUser.setVipLevel(item.getIntValue("vip_level"));
        }
        return groupInfo;
    }

    public DiscussInfo getDiscussInfo(long discussId) {
        LOGGER.debug("开始获取讨论组资料");

        Response response = get(ApiURL.GET_DISCUSS_INFO, new Object[]{Long.valueOf(discussId), this.vfwebqq, this.psessionid});
        JSONObject result = getJsonObjectResult(response);
        DiscussInfo discussInfo = (DiscussInfo) result.getObject("info", DiscussInfo.class);

        Map discussUserMap = new HashMap();
        JSONArray minfo = result.getJSONArray("mem_info");
        for (int i = 0; (minfo != null) && (i < minfo.size()); i++) {
            DiscussUser discussUser = (DiscussUser) minfo.getObject(i, DiscussUser.class);
            discussUserMap.put(Long.valueOf(discussUser.getUin()), discussUser);
            discussInfo.addUser(discussUser);
        }
        JSONArray stats = result.getJSONArray("mem_status");
        for (int i = 0; (stats != null) && (i < stats.size()); i++) {
            JSONObject item = stats.getJSONObject(i);
            DiscussUser discussUser = (DiscussUser) discussUserMap.get(Long.valueOf(item.getLongValue("uin")));
            discussUser.setClientType(item.getIntValue("client_type"));
            discussUser.setStatus(item.getString("status"));
        }
        return discussInfo;
    }

    private Response<String> get(ApiURL url, Object[] params) {
        HeadOnlyRequestBuilder request = (HeadOnlyRequestBuilder) this.session.get(url.buildUrl(params))
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        if (url.getReferer() != null) {
            request.addHeader("Referer", url.getReferer());
        }
        return request.text();
    }

    private Response<String> post(ApiURL url, JSONObject r) {
        return
                ((PostRequestBuilder) ((PostRequestBuilder) ((PostRequestBuilder) this.session.post(url.getUrl())
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"))
                        .addHeader("Referer", url
                                .getReferer()))
                        .addHeader("Origin", url
                                .getOrigin()))
                        .addForm("r", r
                                .toJSONString())
                        .text();
    }

    private Response<String> postWithRetry(ApiURL url, JSONObject r) {
        int times = 0;
        Response response;
        do {
            response = post(url, r);
            times++;
        } while ((times < 5L) && (response.getStatusCode() != 200));
        return response;
    }

    private static JSONObject getJsonObjectResult(Response<String> response) {
        return getResponseJson(response).getJSONObject("result");
    }

    private static JSONArray getJsonArrayResult(Response<String> response) {
        return getResponseJson(response).getJSONArray("result");
    }

    private static void checkSendMsgResult(Response<String> response) {
        if (response.getStatusCode() != 200) {
            LOGGER.error(String.format("发送失败，Http返回码[%d]", new Object[]{Integer.valueOf(response.getStatusCode())}));
        }
        JSONObject json = JSON.parseObject((String) response.getBody());
        Integer errCode = json.getInteger("errCode");
        if ((errCode != null) && (errCode.intValue() == 0))
            LOGGER.debug("发送成功");
        else
            LOGGER.error(String.format("发送失败，Api返回码[%d]", new Object[]{json.getInteger("retcode")}));
    }

    private static JSONObject getResponseJson(Response<String> response) {
        if (response.getStatusCode() != 200) {
            throw new RequestException(String.format("请求失败，Http返回码[%d]", new Object[]{Integer.valueOf(response.getStatusCode())}));
        }
        JSONObject json = JSON.parseObject((String) response.getBody());
        Integer retCode = json.getInteger("retcode");
        if (retCode == null)
            throw new RequestException(String.format("请求失败，Api返回异常", new Object[]{retCode}));
        if (retCode.intValue() != 0) {
            switch (retCode.intValue()) {
                case 103:
                    LOGGER.error("请求失败，Api返回码[103]。你需要进入http://w.qq.com，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常");
                    break;
                case 100100:
                    LOGGER.debug("请求失败，Api返回码[100100]");
                    break;
                default:
                    throw new RequestException(String.format("请求失败，Api返回码[%d]", new Object[]{retCode}));
            }
        }

        return json;
    }

    private String hash() {
        return hash(this.uin, this.ptwebqq);
    }

    private static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException localInterruptedException) {
        }
    }

    private static String hash(long x, String K) {
        int[] N = new int[4];
        for (int T = 0; T < K.length(); T++) {
            N[(T % 4)] ^= K.charAt(T);
        }
        String[] U = {"EC", "OK"};
        long[] V = new long[4];
        V[0] = (x >> 24 & 0xFF ^ U[0].charAt(0));
        V[1] = (x >> 16 & 0xFF ^ U[0].charAt(1));
        V[2] = (x >> 8 & 0xFF ^ U[1].charAt(0));
        V[3] = (x & 0xFF ^ U[1].charAt(1));

        long[] U1 = new long[8];

        for (int T = 0; T < 8; T++) {
            U1[T] = (T % 2 == 0 ? N[(T >> 1)] : V[(T >> 1)]);
        }

        String[] N1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String V1 = "";
        for (long aU1 : U1) {
            V1 = V1 + N1[((int) (aU1 >> 4 & 0xF))];
            V1 = V1 + N1[((int) (aU1 & 0xF))];
        }
        return V1;
    }

    public void close() throws IOException {
        this.pollStarted = false;
        if (this.client != null)
            this.client.close();
    }
}
