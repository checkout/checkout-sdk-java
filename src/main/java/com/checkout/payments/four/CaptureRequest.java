package com.checkout.payments.four;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public final class CaptureRequest {

    private Long amount;

    @SerializedName("capture_type")
    private CaptureType captureType;

    private String reference;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}
