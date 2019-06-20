package com.checkout.payments;

public class RiskAssessment {
    private boolean flagged;

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}