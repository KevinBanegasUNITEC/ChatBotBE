package org.example.chatbot.controller;
import org.example.chatbot.model.ChatRequest;
import org.example.chatbot.model.ChatResponse;
import org.example.chatbot.service.GeminiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private GeminiChatService geminiChatService;

    @PostMapping("/message")
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        String response = geminiChatService.sendMessage(request.getMessage());
        return new ChatResponse(response, new Date());
    }

    @PostMapping("/reset")
    public void resetChat() {
        geminiChatService.resetChat();
    }
}