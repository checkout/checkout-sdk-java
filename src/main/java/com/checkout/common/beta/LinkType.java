package com.checkout.common.beta;

public enum LinkType {

    SELF("self"),
    ACTIONS("actions"),
    AUTHORIZE("authorize"),
    VOID("void"),
    CAPTURE("capture");

    private final String key;

    LinkType(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
