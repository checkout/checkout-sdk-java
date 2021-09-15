package com.checkout.disputes.four;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PaymentDispute {

    private String id;

    @SerializedName("action_id")
    private String actionId;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private Long amount;

    private String currency;

    private String method;

    private String arn;

    private String mcc;

    @SerializedName("3ds")
    private ThreeDSVersionEnrollment threeDSVersionEnrollment;

    private String eci;

    @SerializedName("has_refund")
    private boolean hasRefund;

    @SerializedName("processed_on")
    private Instant processedOn;

}
