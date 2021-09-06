package com.checkout.risk.precapture;

import lombok.Data;

import java.util.List;

@Data
public final class PreCaptureWarning {

    private PreCaptureDecision decision;

    private List<String> reasons;

}
