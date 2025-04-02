package org.zsj.demo_v1.dto;

public class DetectionResultResponse {
    private int aiProbability;
    private String analysis;
    
    public DetectionResultResponse() {
    }
    
    public DetectionResultResponse(int aiProbability, String analysis) {
        this.aiProbability = aiProbability;
        this.analysis = analysis;
    }
    
    public int getAiProbability() {
        return aiProbability;
    }
    
    public void setAiProbability(int aiProbability) {
        this.aiProbability = aiProbability;
    }
    
    public String getAnalysis() {
        return analysis;
    }
    
    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
} 