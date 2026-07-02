package com.checkout.payments.contexts;

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
    private LocalDate registrationDate;

    /**
     * The date of the customer's first transaction.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate firstTransactionDate;

    /**
     * The date of the customer's last payment.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate lastPaymentDate;

    /**
     * The total number of orders the customer has placed.
     * [Optional]
     */
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment.
     * [Optional]
     */
    private Float lastPaymentAmount;

}
