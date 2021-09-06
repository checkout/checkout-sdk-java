package com.checkout.risk.precapture;

import lombok.Data;

@Data
public final class PreCaptureResult {

    private PreCaptureDecision decision;

    private String details;

}

