package com.example.chatgptdemo.controllers;

import com.example.chatgptdemo.services.ChatGPTService;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGPTService chatGPTService;

    @Autowired
    public ChatGptController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @GetMapping("/process-request")
    public List<ChatMessage> processRequest(@RequestBody String prompt) {
        try {
            return chatGPTService.getChatCompletion(prompt);
        } catch (Exception e) {
            //e.printStackTrace();
            return Collections.singletonList(new ChatMessage(ChatMessageRole.SYSTEM.value(), "Error occurred: " + e.getMessage()));
        }
    }
}
