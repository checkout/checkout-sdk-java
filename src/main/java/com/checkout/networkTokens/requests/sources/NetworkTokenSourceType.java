package com.checkout.networkTokens.requests.sources;

public enum NetworkTokenSourceType {
    
    ID("id"),
    CARD("card");

    private final String sourceType;

    NetworkTokenSourceType(final String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }
}