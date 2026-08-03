package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.AiRequest;
import com.tanmay.devpulse.dto.AiResponse;
import com.tanmay.devpulse.service.GeminiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiService geminiService;

    public AiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AiResponse> chat(
            @Valid @RequestBody AiRequest request) {

        return ResponseEntity.ok(
                geminiService.generateResponse(request.getPrompt())
        );
    }

    @PostMapping("/summarize")
    public ResponseEntity<AiResponse> summarize(
            @Valid @RequestBody AiRequest request) {

        String prompt = """
                Summarize the following text in concise bullet points:

                %s
                """.formatted(request.getPrompt());

        return ResponseEntity.ok(
                geminiService.generateResponse(prompt)
        );
    }

    @PostMapping("/explain-code")
    public ResponseEntity<AiResponse> explainCode(
            @Valid @RequestBody AiRequest request) {

        String prompt = """
                Explain the following code in simple language:

                %s
                """.formatted(request.getPrompt());

        return ResponseEntity.ok(
                geminiService.generateResponse(prompt)
        );
    }

    @PostMapping("/generate-task")
    public ResponseEntity<AiResponse> generateTask(
            @Valid @RequestBody AiRequest request) {

        String prompt = """
                Convert the following requirement into a professional software development task.

                Include:
                - Title
                - Description
                - Suggested Priority

                Requirement:
                %s
                """.formatted(request.getPrompt());

        return ResponseEntity.ok(
                geminiService.generateResponse(prompt)
        );
    }
}