package com.rag.application.dto;

public class DocumentInfo {

    private String title;
    private long chunks;

    public DocumentInfo(String title, long chunks) {
        this.title = title;
        this.chunks = chunks;
    }

    public String getTitle() {
        return title;
    }

    public long getChunks() {
        return chunks;
    }
}