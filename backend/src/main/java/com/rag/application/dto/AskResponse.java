package com.rag.application.dto;

import java.util.List;

public class AskResponse {

    private String answer;
    private List<SourceInfo> sources;

    public AskResponse(String answer, List<SourceInfo> sources) {
        this.answer = answer;
        this.sources = sources;
    }

    public String getAnswer() {
        return answer;
    }

    public List<SourceInfo> getSources() {
        return sources;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSources(List<SourceInfo> sources) {
        this.sources = sources;
    }
}