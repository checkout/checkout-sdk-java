package com.checkout.payments.beta.response;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentResponseBalances {

    @SerializedName("total_authorized")
    private Long totalAuthorized;

    @SerializedName("total_voided")
    private Long totalVoided;

    @SerializedName("available_to_void")
    private Long availableToVoid;

    @SerializedName("total_captured")
    private Long totalCaptured;

    @SerializedName("available_to_capture")
    private Long availableToCapture;

    @SerializedName("total_refunded")
    private Long totalRefunded;

    @SerializedName("available_to_refund")
    private Long availableToRefund;

}
