package org.jcker.controller;

import org.jcker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController
{
  private MessageService messageService;

  @Autowired
  private void setMessageService(MessageService messageService)
  {
    this.messageService = messageService;
  }

  @RequestMapping({"message"})
  public String getMessage()
  {
    return "index";
  }
}
