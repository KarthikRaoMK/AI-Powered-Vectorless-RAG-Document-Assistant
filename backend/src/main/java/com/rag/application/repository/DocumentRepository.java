package com.rag.application.repository;

import com.rag.application.entity.Document;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findTop5ByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
        String title,
        String content
);
@Modifying
@Transactional
void deleteByTitle(String title);

 

List<Document> findAll();

Optional<Document> findFirstByTitle(String title);

long count();

  long countDistinctByTitleIsNotNull();

}