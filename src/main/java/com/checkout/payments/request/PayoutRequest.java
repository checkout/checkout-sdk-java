package com.checkout.payments.request;

import com.checkout.common.Currency;
import com.checkout.payments.request.destination.PaymentRequestDestination;
import com.checkout.payments.request.source.PayoutRequestSource;
import com.checkout.payments.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public final class PayoutRequest {

    private PayoutRequestSource source;

    private PaymentRequestDestination destination;

    private Long amount;

    private Currency currency;

    private String reference;

    @SerializedName("billing_descriptor")
    private PayoutBillingDescriptor billingDescriptor;

    private PaymentSender sender;

    private PaymentInstruction instruction;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private Map<String, Object> metadata;

}
