package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/{room}")
    public void sendMessage(@DestinationVariable("room") String room, @Payload String payload) {
        messagingTemplate.convertAndSend("/topic/" + room, payload);
    }
}
