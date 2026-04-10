package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentContextsCustomerSummary {

    /**
     * The date the customer registered.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("registration_date")
    private LocalDate registrationDate;

    /**
     * The date of the customer's first transaction.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("first_transaction_date")
    private LocalDate firstTransactionDate;

    /**
     * The date of the customer's last payment.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("last_payment_date")
    private LocalDate lastPaymentDate;

    /**
     * The total number of orders the customer has placed.
     * [Optional]
     */
    @SerializedName("total_order_count")
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment.
     * [Optional]
     */
    @SerializedName("last_payment_amount")
    private Float lastPaymentAmount;

}
