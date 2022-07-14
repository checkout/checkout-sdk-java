package com.checkout.payments.hosted;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class HostedPaymentDetailsResponse extends Resource {

    private String id;

    private HostedPaymentStatus status;

    @SerializedName("payment_id")
    private String paymentId;

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    private CustomerResponse customer;

    private BillingInformation billing;

    private List<Product> products;

    private Map<String, Object> metadata;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("cancel_url")
    private String cancelUrl;

    @SerializedName("failure_url")
    private String failureUrl;

}
