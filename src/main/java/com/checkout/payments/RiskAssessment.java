package com.checkout.payments;

import lombok.Data;

@Data
public final class RiskAssessment {

    private Boolean flagged;

    private Integer score;
}
