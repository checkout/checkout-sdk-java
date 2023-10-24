package com.checkout.reconciliation.previous;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentPeriodBreakdown {

    @SerializedName("processed_amount")
    private Long processedAmount;

    @SerializedName("refund_amount")
    private Long refundAmount;

    @SerializedName("chargeback_amount")
    private Long chargebackAmount;

    @SerializedName("payouts_to_card_amount")
    private Long payoutsToCardAmount;
    
    @SerializedName("processing_fees")
    private Integer processingFees;

    @SerializedName("processing_fees_breakdown")
    private ProcessingFeesBreakdown processingFeesBreakdown;

    @SerializedName("rolling_reserve_amount")
    private Integer rollingReserveAmount;

    private Integer tax;

    @SerializedName("admin_fees")
    private Integer adminFees;

    @SerializedName("general_adjustments")
    private Integer generalAdjustments;
}
