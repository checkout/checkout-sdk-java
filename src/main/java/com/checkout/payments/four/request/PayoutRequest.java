package com.checkout.payments.four.request;

import com.checkout.common.Currency;
import com.checkout.payments.four.request.destination.PaymentRequestDestination;
import com.checkout.payments.four.request.source.PayoutRequestSource;
import com.checkout.payments.four.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

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

}
