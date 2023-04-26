package com.example.REST_API;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;

public class ChatGPTHelper {
    OpenAiService service;
    public ChatGPTHelper()
    {
        service = new OpenAiService("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", Duration.ofSeconds(120));
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
}
