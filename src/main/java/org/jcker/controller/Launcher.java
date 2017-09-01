package org.jcker.controller;

import org.jcker.service.GroupInfoService;
import org.jcker.service.GroupMessageService;
import org.jcker.service.GroupService;
import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupInfo;
import org.jcker.smartqq.model.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Launcher {

    @Autowired
    private MessageCallback messageCallback;
    @Autowired
    GroupMessageService groupMessageService;
    @Autowired
    GroupService groupService;
    @Autowired
    GroupInfoService groupInfoService;

    public static SmartQQClient client = null;

    private static boolean loginFlag = false;

    @ResponseBody
    @RequestMapping("/start")
    public List<GroupMessage> start() {
        List list = this.groupMessageService.getLatestGroupMessage();
        list = list.subList(list.size() - 7, list.size());
        return list;
    }

    @RequestMapping({"/login"})
    public String login(Model model) throws IOException {
        if (!loginFlag) {
            client = new SmartQQClient(this.messageCallback);
            loginFlag = true;

            List<Group> groupList = client.getGroupList();
            this.groupService.saveOrUpdateGroup(groupList);

            for (Group group : groupList) {
                SmartQQClient.groupInfoMap.put(group.getId(), client.getGroupInfo(group.getCode()));
            }
        } else {
            model.addAttribute("userInfo", client.getAccountInfo());
            return "error";
        }
        return "index";
    }

    @RequestMapping({"/logout"})
    public String logout() {
        try {
            client.close();
            loginFlag = false;
            System.out.println("smart qq closed.......................");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/qrcode")
    public void qrcode(HttpServletResponse response) throws IOException {
        if (client != null) {
            System.out.println("Start load groupinfo.........");
            List<Group> list = client.getGroupList();
            for (Group group : list) {
                SmartQQClient.groupInfoMap.put(group.getId(), client.getGroupInfo(group.getCode()));
            }
        }


        response.setContentType("image/png");
        File file = new File(SmartQQClient.filePath);
        if (file.exists()) {
            InputStream in = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                os.write(b);
            }
            in.close();
            os.flush();
            os.close();
        }
    }
}
