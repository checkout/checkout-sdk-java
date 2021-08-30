package com.checkout.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;

@Data
public final class AttemptSummaryResponse {

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("response_body")
    private String responseBody;

    @SerializedName("send_mode")
    private String sendMode;

    private Instant timestamp;

}
