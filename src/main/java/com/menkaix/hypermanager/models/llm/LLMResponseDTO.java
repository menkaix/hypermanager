package com.menkaix.hypermanager.models.llm;

public class LLMResponseDTO {

    private MessageDTO[] history;
    private MessageDTO message;
    private String error;

    public MessageDTO[] getHistory() {
        return history;
    }

    public void setHistory(MessageDTO[] history) {
        this.history = history;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
