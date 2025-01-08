package com.freelanceplatform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/test")
    @SendTo("/topic/messages")
    public String handleTestMessage(String message) {
        return "Server received: " + message;
    }
}
