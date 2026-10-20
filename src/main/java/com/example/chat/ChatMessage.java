package com.example.chat;

public record ChatMessage(String from, String text, String image) {

    public ChatMessage(String from, String text) {
        this(from, text, null);
    }
}
