package com.checkout.networkTokens.entities;

public enum NetworkTokenState {
    
    ACTIVE("active"),
    SUSPENDED("suspended"),
    INACTIVE("inactive"),
    DECLINED("declined"),
    REQUESTED("requested");

    private final String state;

    NetworkTokenState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}