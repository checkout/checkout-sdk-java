package com.checkout.payments;

public class RiskRequest {
    private final boolean enabled;

    public RiskRequest(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}