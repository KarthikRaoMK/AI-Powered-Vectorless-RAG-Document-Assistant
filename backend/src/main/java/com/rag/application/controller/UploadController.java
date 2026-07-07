package com.rag.application.controller;

import com.rag.application.service.DocumentService;
import com.rag.application.service.PdfService;
import com.rag.application.util.TextChunker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final PdfService pdfService;
    private final DocumentService documentService;

    public UploadController(PdfService pdfService,
                            DocumentService documentService) {

        this.pdfService = pdfService;
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<String> uploadPdf(
            @RequestParam("file") MultipartFile file) {

        try {

            String text = pdfService.extractText(file);

            List<String> chunks =
                    TextChunker.chunk(text, 500);

            documentService.saveChunks(
                    file.getOriginalFilename(),
                    chunks
            );

            return ResponseEntity.ok(
                    "Uploaded successfully.\nChunks stored : "
                            + chunks.size());

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());

        }

    }

}