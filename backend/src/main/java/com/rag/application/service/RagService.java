package com.rag.application.service;

import com.rag.application.dto.AskResponse;
import com.rag.application.dto.SourceInfo;
import com.rag.application.entity.Document;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final DocumentService documentService;

    // Stop words that should not affect retrieval
    private static final List<String> STOP_WORDS = List.of(
            "what",
            "is",
            "the",
            "a",
            "an",
            "of",
            "about",
            "define",
            "explain",
            "tell",
            "me",
            "who",
            "are",
            "does",
            "do",
            "how",
            "to",
            "and",
            "for"
    );

    public RagService(ChatClient.Builder builder,
                      DocumentService documentService) {

        this.chatClient = builder.build();
        this.documentService = documentService;
    }

    public AskResponse askQuestion(String question) {

        String keyword = question
                .replaceAll("(?i)what is", "")
                .replaceAll("(?i)who is", "")
                .replaceAll("(?i)define", "")
                .replaceAll("(?i)explain", "")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .trim();

        List<Document> documents = documentService.search(keyword);

        // Remove stop words
        List<String> keywords = List.of(keyword.toLowerCase().split("\\s+"))
                .stream()
                .filter(word -> !STOP_WORDS.contains(word))
                .filter(word -> !word.isBlank())
                .toList();

        // Rank documents
        documents = documents.stream()
        .sorted((d1, d2) -> {

            int score1 = calculateScore(d1.getContent(), keywords);
            int score2 = calculateScore(d2.getContent(), keywords);

            return Integer.compare(score2, score1);

        })
        .filter(d -> calculateScore(d.getContent(), keywords) > 0)

        // Remove duplicate chunks with identical content
        .collect(Collectors.toMap(
                Document::getContent,
                doc -> doc,
                (d1, d2) -> d1
        ))
        .values()
        .stream()

        .limit(5)
        .toList();

        String context = documents.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        String prompt = """
                You are a helpful AI assistant.

                Answer ONLY using the context below.

                If the answer is not present, reply:
                "I don't have enough information."

                Context:
                %s

                Question:
                %s
                """.formatted(context, question);

        try {

            String answer = chatClient.prompt(prompt)
                    .call()
                    .content();

            List<SourceInfo> sources = documents.stream()
                    .map(doc -> new SourceInfo(
                            doc.getTitle(),
                            doc.getChunkNumber()
                    ))
                    .toList();

            return new AskResponse(answer, sources);

        } catch (Exception e) {

            e.printStackTrace();

            return new AskResponse(
                    "ERROR: " + e.getMessage(),
                    List.of()
            );

        }

    }

    // Improved ranking algorithm
    private int calculateScore(String text, List<String> keywords) {

        text = text.toLowerCase();

        int score = 0;

        // Exact phrase bonus
        String phrase = String.join(" ", keywords);

        if (!phrase.isBlank() && text.contains(phrase)) {
            score += 10;
        }

        for (String keyword : keywords) {

            int occurrences = countOccurrences(text, keyword);

            if (occurrences > 0) {

                // Frequency bonus
                score += occurrences * 2;

                // Presence bonus
                score += 1;

            }

        }

        return score;

    }

    // Count how many times a keyword appears
    private int countOccurrences(String text, String keyword) {

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(keyword, index)) != -1) {

            count++;

            index += keyword.length();

        }

        return count;

    }

}