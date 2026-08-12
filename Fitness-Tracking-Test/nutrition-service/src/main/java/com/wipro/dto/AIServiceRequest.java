package com.wipro.dto;

public class AIServiceRequest {

    private Long userId;
    private String prompt;
    private String requestType;

    public AIServiceRequest() {
    }

    public AIServiceRequest(Long userId, String prompt, String requestType) {
        this.userId = userId;
        this.prompt = prompt;
        this.requestType = requestType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}