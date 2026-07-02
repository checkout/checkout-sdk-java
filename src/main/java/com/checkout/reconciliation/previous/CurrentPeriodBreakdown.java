package com.checkout.reconciliation.previous;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CurrentPeriodBreakdown {

    private Long processedAmount;

    private Long refundAmount;

    private Long chargebackAmount;

    private Long payoutsToCardAmount;
    
    private Integer processingFees;

    private ProcessingFeesBreakdown processingFeesBreakdown;

    private Integer rollingReserveAmount;

    private Integer tax;

    private Integer adminFees;

    private Integer generalAdjustments;
}
