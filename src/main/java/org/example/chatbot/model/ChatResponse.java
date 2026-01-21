package org.example.chatbot.model;

import java.util.Date;

public class ChatResponse {
    private String message;
    private Date timestamp;

    public ChatResponse() {
    }

    public ChatResponse(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}