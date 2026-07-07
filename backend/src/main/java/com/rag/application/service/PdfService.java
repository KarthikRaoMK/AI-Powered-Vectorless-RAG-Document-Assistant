package com.rag.application.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    public String extractText(MultipartFile file) throws IOException {

        PDDocument document =
                Loader.loadPDF(file.getBytes());

        String text =
                new org.apache.pdfbox.text.PDFTextStripper()
                        .getText(document);

        document.close();

        return text;

    }

}