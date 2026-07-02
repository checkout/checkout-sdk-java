package com.checkout.risk;

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

    private String userAgent;

    private String fingerprint;

}
