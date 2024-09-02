package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
public final class PaymentPlanType {

    // Recurring
    @SerializedName("amount_variability")
    private AmountVariability amountVariability;

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
