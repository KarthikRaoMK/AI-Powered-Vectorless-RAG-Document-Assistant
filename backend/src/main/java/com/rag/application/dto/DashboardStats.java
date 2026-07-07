package com.rag.application.dto;

public class DashboardStats {

    private long documents;
    private long chunks;
    private String model;

    public DashboardStats(long documents, long chunks, String model) {
        this.documents = documents;
        this.chunks = chunks;
        this.model = model;
    }

    public long getDocuments() {
        return documents;
    }

    public long getChunks() {
        return chunks;
    }

    public String getModel() {
        return model;
    }

    public void setDocuments(long documents) {
        this.documents = documents;
    }

    public void setChunks(long chunks) {
        this.chunks = chunks;
    }

    public void setModel(String model) {
        this.model = model;
    }
}