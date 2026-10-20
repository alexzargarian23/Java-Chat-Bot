package com.example.chat;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatHistoryService {

    private final List<ChatHistory> messages = new CopyOnWriteArrayList<>();

    public void save(ChatMessage message) {
        messages.add(new ChatHistory(
                message.from(),
                message.text(),
                message.image(),
                Instant.now()
        ));
    }

    public List<ChatHistory> getAll() {
        return List.copyOf(messages);
    }
}
