package com.checkout.networkTokens.entities;

public enum InitiatedBy {
    
    CARDHOLDER("cardholder"),
    TOKEN_REQUESTOR("token_requestor");

    private final String initiatedBy;

    InitiatedBy(final String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }
}