package com.checkout.disputes;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentDispute extends Resource {

    private String id;

    @SerializedName("action_id")
    private String actionId;

    private Long amount;

    private Currency currency;

    private String method;

    private String arn;

    @SerializedName("processed_on")
    private Instant processedOn;

    //Available only on Four

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private String mcc;

    @SerializedName("3ds")
    private ThreeDSVersionEnrollment threeDSVersionEnrollment;

    private String eci;

    @SerializedName("has_refund")
    private Boolean hasRefund;

}
