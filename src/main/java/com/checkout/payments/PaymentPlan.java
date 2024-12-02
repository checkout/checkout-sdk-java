package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class PaymentPlan {

    // Recurring
    @SerializedName("amount_variability")
    private AmountVariabilityType amountVariabilityType;

    // Installment
    private Boolean financing;

    private String amount;

    // Common properties
    @SerializedName("days_between_payments")
    private Integer daysBetweenPayments;

    @SerializedName("total_number_of_payments")
    private Integer totalNumberOfPayments;

    @SerializedName("current_payment_number")
    private Integer currentPaymentNumber;

    @SerializedName("expiry")
    private Instant expiry;

}
