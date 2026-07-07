package com.rag.application.controller;

import com.rag.application.dto.AskResponse;
import com.rag.application.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ask")
    public AskResponse ask(@RequestParam String question) {

        return ragService.askQuestion(question);

    }

}