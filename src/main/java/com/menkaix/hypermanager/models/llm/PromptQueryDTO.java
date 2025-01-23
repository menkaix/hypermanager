package com.menkaix.hypermanager.models.llm;

public class PromptQueryDTO {

    private String prompt;
    private MessageDTO[] history;
    private String path;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public MessageDTO[] getHistory() {
        return history;
    }

    public void setHistory(MessageDTO[] history) {
        this.history = history;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
