package org.jcker.controller;

import org.jcker.service.GroupMessageService;
import org.jcker.service.GroupService;
import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-29 at 4:37 PM
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private GroupMessageService groupMessageService;
    @Autowired
    private GroupService groupService;

    @RequestMapping("/personal/{userId}")
    public String goToPersonal(GroupMessage groupMessage, Model model) {

        model.addAttribute("id", groupMessage.getUserId());
        return "personal";
    }

    @ResponseBody
    @RequestMapping("/personalmessage/{userId}")
    public List<GroupMessage> queryPersonalMessages(GroupMessage groupMessage) {

        return this.groupMessageService.findAllByUserId(groupMessage.getUserId());
    }


    @ResponseBody
    @RequestMapping("/groupmessage/{groupId}")
    public List<GroupMessage> queryGroupMessages(GroupMessage groupMessage) {
        System.out.println("groupMessage = " + groupMessage);
        return this.groupMessageService.findAllByGroupId(groupMessage.getGroupId());
    }
}
