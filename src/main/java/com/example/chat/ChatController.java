package com.example.chat;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatHistoryService historyService;

    public ChatController(
            SimpMessagingTemplate template,
            ChatHistoryService historyService
    ) {
        this.template = template;
        this.historyService = historyService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        historyService.save(message);
        return message;
    }

    @EventListener
    public void onUserConnected(SessionConnectedEvent event) {
        ChatMessage message = new ChatMessage("System", "A user joined the chat");
        historyService.save(message);
        template.convertAndSend("/topic/messages", message);
    }
    @EventListener
    public void onUserDisconnected(SessionDisconnectEvent event) {
        ChatMessage message = new ChatMessage("System", "A user left the chat");
        historyService.save(message);
        template.convertAndSend("/topic/messages", message);
    }
}
