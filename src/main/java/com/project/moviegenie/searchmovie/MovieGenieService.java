package com.project.moviegenie.searchmovie;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieGenieService {

    private final ChatgptService chatgptService;

    public String getAnswer(String prompt) {
        return chatgptService.sendMessage(prompt);
    }
}
