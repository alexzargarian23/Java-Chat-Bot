package com.example.chat;

import java.time.Instant;

public record ChatHistory(String from, String text, String image, Instant createdAt) {
}
