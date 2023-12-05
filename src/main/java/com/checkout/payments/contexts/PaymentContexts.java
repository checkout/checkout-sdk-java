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

    public Long amount;

    public Currency currency;

    @SerializedName("payment_type")
    public PaymentType paymentType;

    public boolean capture;

    public ShippingDetails shipping;

    public PaymentContextsProcessing processing;

    @SerializedName("processing_channel_id")
    public String processingChannelId;

    public String reference;

    public String description;

    @SerializedName("success_url")
    public String successUrl;

    @SerializedName("failure_url")
    public String failureUrl;

    public List<PaymentContextsItems> items;
}
