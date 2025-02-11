package com.checkout.payments.contexts;

import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ShippingDetails;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContexts {

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("authorization_type")
    private String authorizationType;

    private Boolean capture;

    private PaymentContextsCustomerRequest customer;

    private ShippingDetails shipping;

    private PaymentContextsProcessing processing;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private String reference;

    private String description;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    private List<PaymentContextsItems> items;
}
