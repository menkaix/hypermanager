package com.menkaix.hypermanager.models;

public class PromptQueryDTO {

    private String prompt;
    private MessageDTO[] history ;

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
}
