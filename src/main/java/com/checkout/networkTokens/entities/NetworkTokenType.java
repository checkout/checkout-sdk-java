package com.checkout.networkTokens.entities;

public enum NetworkTokenType {
    
    VTS("vts"),
    MDES("mdes");

    private final String type;

    NetworkTokenType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}