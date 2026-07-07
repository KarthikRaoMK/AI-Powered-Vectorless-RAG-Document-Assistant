package com.rag.application.dto;

public class SourceInfo {

    private String title;
    private int chunkNumber;

    public SourceInfo(String title, int chunkNumber) {
        this.title = title;
        this.chunkNumber = chunkNumber;
    }

    public String getTitle() {
        return title;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }
}