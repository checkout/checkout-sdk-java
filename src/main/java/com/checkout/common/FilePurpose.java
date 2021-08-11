package com.checkout.common;

import lombok.Getter;

public enum FilePurpose {

    DISPUTE_EVIDENCE("dispute_evidence");

    @Getter
    private final String purpose;

    FilePurpose(final String purpose) {
        this.purpose = purpose;
    }

}
