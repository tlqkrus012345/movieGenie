package com.project.moviegenie.chatgpt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moviegenie")
public class MovieGenieController {
    private final MovieGenieService movieGenieService;

    @PostMapping("/ask")
    public String askToMovieGenie(@RequestBody String question) {
        return movieGenieService.getAnswer(question);
    }
}
