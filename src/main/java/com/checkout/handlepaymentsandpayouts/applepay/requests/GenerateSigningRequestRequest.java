package com.checkout.handlepaymentsandpayouts.applepay.requests;

import com.checkout.handlepaymentsandpayouts.applepay.entities.ProtocolVersions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GenerateSigningRequestRequest {

    /**
     * The protocol version of the encryption type used.
     * Default: "ec_v1"
     */
    @Builder.Default
    private ProtocolVersions protocolVersion = ProtocolVersions.EC_V1;

}