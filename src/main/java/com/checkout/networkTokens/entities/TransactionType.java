package com.checkout.networkTokens.entities;

public enum TransactionType {
    
    ECOM("ecom"),
    RECURRING("recurring"),
    POS("pos"),
    AFT("aft");

    private final String transactionType;

    TransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}