package com.api.bkland.controller;

import com.api.bkland.payload.request.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Message chat(Message message) throws Exception {
        Thread.sleep(1000);
        return message;
    }
}
