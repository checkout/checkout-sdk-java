package com.checkout.payments.sessions;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSessionsRequest {

    private Long amount;

    private Currency currency;

    private String reference;

    private Billing billing;

    private CustomerRequest customer;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

}
