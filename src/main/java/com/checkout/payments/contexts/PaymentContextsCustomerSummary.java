package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContextsCustomerSummary {

    @SerializedName("registration_date")
    private Instant registrationDate;

    @SerializedName("first_transaction_date")
    private Instant firstTransactionDate;

    @SerializedName("last_payment_date")
    private Instant lastPaymentDate;

    @SerializedName("total_order_count")
    private Integer totalOrderCount;

    @SerializedName("last_payment_amount")
    private Float lastPaymentAmount;

}
