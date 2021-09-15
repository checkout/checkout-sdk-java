package com.checkout.payments.four.capture;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public final class CaptureRequest {

    private Integer amount;

    @SerializedName("capture_type")
    private CaptureType captureType;

    private String reference;

    private Map<String, Object> metadata;

}