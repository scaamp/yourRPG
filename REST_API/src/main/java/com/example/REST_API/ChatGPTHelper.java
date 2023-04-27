package com.example.REST_API;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ChatGPTHelper {

    OpenAiService service;
    public ChatGPTHelper() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/token.txt"));
        String token;
        try {
            StringBuilder sb = new StringBuilder();
            token = br.readLine();
        } finally {
            br.close();
        }
        service = new OpenAiService(token, Duration.ofSeconds(120));
    }


    public String getQuestsIdea(String category)
    {
        String question = "How I can develop " + category;

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder().messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo").build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();
        choices.stream().map(ChatCompletionChoice::getMessage).map(ChatMessage::getContent).forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    public String getAnswerFromOracle(String question)
    {
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder().messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo").build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();
        choices.stream().map(ChatCompletionChoice::getMessage).map(ChatMessage::getContent).forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
