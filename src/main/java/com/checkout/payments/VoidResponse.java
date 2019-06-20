package com.checkout.payments;

import com.checkout.common.Resource;

public class VoidResponse extends Resource {
    private String actionId;
    private String reference;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}