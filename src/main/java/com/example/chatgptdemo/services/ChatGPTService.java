package com.example.chatgptdemo.services;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatGPTService {
    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions"; // This URL may change; always refer to the official documentation.

    private final RestTemplate restTemplate;

    private List<ChatMessage> messages = new ArrayList<>();

    public ChatGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ChatMessage> getChatCompletion(String prompt) {

        OpenAiService service = new OpenAiService(apiKey);

        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        messages.add(firstMsg);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0613")
                .messages(messages)
                .n(2)
                .maxTokens(100)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        messages.add(responseMessage); // don't forget to update the conversation with the latest response
        return messages;
   }
}
