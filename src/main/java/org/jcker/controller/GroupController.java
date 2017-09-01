package org.jcker.controller;

import org.jcker.service.GroupService;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-29 at 8:31 PM
 *
 * @version 1.0
 */
@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping("grouplist")
    public String goToGroupList() {
        return "grouplist";
    }

    @RequestMapping("group/{id}")
    public String goToGroup(Group group, Model model) {
        model.addAttribute("group", groupService.getGroupById(group.getId()));
        return "/group";
    }

    @ResponseBody
    @RequestMapping("/getgrouplist")
    public List<Group> findAll() {
        return this.groupService.findAllGroup();
    }

    @RequestMapping("/updategrouplist")
    public String updateGroupList() {
        SmartQQClient client = Launcher.client;
        if (client != null) {
            this.groupService.saveOrUpdateGroup(Launcher.client.getGroupList());
        }
        return "grouplist";
    }
}
