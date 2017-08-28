package org.jcker.controller;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jcker.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupMessageController
{

  @Autowired
  GroupMessageService groupMessageService;

  @ResponseBody
  @RequestMapping({"/getLatestGroupMessage"})
  public Map<String, Object> getLatestGroupMessage()
  {
    List list = this.groupMessageService.getLatestGroupMessage();
    Map map = new HashMap();
    map.put("groupMessages", list.subList(0, 8));
    return map;
  }

  public static void main(String[] args) {
    System.out.println();
  }
}
