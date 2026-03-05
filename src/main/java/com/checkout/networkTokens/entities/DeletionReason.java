package com.checkout.networkTokens.entities;

public enum DeletionReason {
    
    FRAUD("fraud"),
    OTHER("other");

    private final String reason;

    DeletionReason(final String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}