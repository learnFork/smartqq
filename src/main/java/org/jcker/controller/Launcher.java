package org.jcker.controller;

import java.io.IOException;
import java.io.PrintStream;
import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.client.SmartQQClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Launcher
{

  @Autowired
  private MessageCallback messageCallback;
  private static SmartQQClient client = null;

  private static boolean loginFlag = false;

  @RequestMapping({"/login"})
  public String login(Model model) {
    if (!loginFlag)
    {
      client = new SmartQQClient(this.messageCallback);
      loginFlag = true;
    } else {
      model.addAttribute("userInfo", client.getAccountInfo());
      System.out.println("Already Login");
      return "error";
    }
    return "index";
  }

  @RequestMapping({"/close"})
  public String close() {
    try {
      client.close();
      System.out.println("smart qq closed.......................");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "index";
  }
}
