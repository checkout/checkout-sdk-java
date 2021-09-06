package com.checkout.risk;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class Device {

    private String ip;

    private Location location;

    private String os;

    private String type;

    private String model;

    private Instant date;

    @SerializedName("user_agent")
    private String userAgent;

    private String fingerprint;

}
