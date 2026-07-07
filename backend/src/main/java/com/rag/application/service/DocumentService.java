package com.rag.application.service;

import com.rag.application.entity.Document;
import com.rag.application.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import com.rag.application.dto.DocumentInfo;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import com.rag.application.dto.DashboardStats;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public List<Document> search(String keyword) {
     return repository.findTop5ByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            keyword,
            keyword
    );

}

    public Document save(Document document) {
        return repository.save(document);
    }



public void saveChunks(String title, List<String> chunks) {

    repository.deleteByTitle(title);

    int chunkNo = 1;

    for (String chunk : chunks) {

        Document document = new Document();

        document.setTitle(title);
        document.setChunkNumber(chunkNo++);
        document.setContent(chunk);

        repository.save(document);
    }
}

public List<DocumentInfo> getAllDocuments() {

    return repository.findAll()
            .stream()
            .collect(Collectors.groupingBy(Document::getTitle))
            .entrySet()
            .stream()
            .map(entry ->
                    new DocumentInfo(
                            entry.getKey(),
                            entry.getValue().size()))
            .toList();
}

public DashboardStats getDashboardStats() {

    long totalChunks = repository.count();

    long totalDocuments = repository.findAll()
            .stream()
            .map(Document::getTitle)
            .distinct()
            .count();

    return new DashboardStats(
            totalDocuments,
            totalChunks,
            "Gemini 2.0 Flash"
    );

}

@Transactional
public boolean deleteDocument(String title){
    if(repository.findFirstByTitle(title).isEmpty()){
        
        return false;
    }
    repository.deleteByTitle(title);
    return true;
}
}