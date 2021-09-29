package com.checkout.sessions.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class SdkEphemeralPublicKey {

    private String kty;

    private String crv;

    private String x;

    private String y;

}
