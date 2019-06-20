package com.checkout.payments;

import java.util.Map;

public class VoidRequest {
    private String reference;
    private Map<String, Object> metadata;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}