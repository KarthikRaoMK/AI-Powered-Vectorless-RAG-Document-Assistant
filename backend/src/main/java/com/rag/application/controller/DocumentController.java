package com.rag.application.controller;

import com.rag.application.dto.DashboardStats;
import com.rag.application.dto.DocumentInfo;
import com.rag.application.entity.Document;
import com.rag.application.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import com.rag.application.dto.DocumentInfo;

import java.util.List;


@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping
    public List<DocumentInfo> getDocuments() {
    return service.getAllDocuments();
    }
    
    @DeleteMapping 
    public String deleteDocument(@RequestParam String title) {
         boolean deleted = service.deleteDocument(title);
         if(deleted){
            return "Document with title '" + title + "' has been deleted.";
         }else {
            return "Document with title '" + title + "' not found.";
         }
    }

    @GetMapping("/dashboard")
    public DashboardStats dashboard() {

    return service.getDashboardStats();

}
}