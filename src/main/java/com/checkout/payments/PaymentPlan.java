package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentPlan {

    // Recurring
    @SerializedName("amount_variability")
    private AmountVariabilityType amountVariabilityType;

    // Installment
    private Boolean financing;

    /**
     * The amount to charge for each payment in the plan, in the minor currency unit.
     * Required when {@code source.type} is {@code blik}, {@code payment_plan.amount_variability}
     * is {@code Fixed}, and the recurring agreement is created without an initial payment
     * ({@code amount} set to {@code 0}).
     * [Optional]
     * min 1
     */
    private Long amount;

    // Common properties
    private Integer daysBetweenPayments;

    private Integer totalNumberOfPayments;

    private Integer currentPaymentNumber;

    private String expiry;

    /**
     * The name of the payment plan. Required when {@code source.type} is {@code blik}.
     * For Blik merchant-initiated requests using an external {@code partner_agreement_id},
     * this value is used as the Blik Alias Label.
     * [Optional]
     * max 35 characters
     */
    private String name;

    /**
     * The date on which the first payment will be taken, in {@code YYYYMMDD} format.
     * Required when {@code source.type} is {@code blik} and the recurring agreement is
     * created without an initial payment ({@code amount} set to {@code 0}).
     * [Optional]
     */
    private String startDate;

}
