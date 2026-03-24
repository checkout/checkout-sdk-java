package com.checkout.handlepaymentsandpayouts.googlepay.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request to register a web domain for a Google Pay enrolled entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GooglePayRegisterDomainRequest {

    /**
     * The web domain to register for an actively enrolled entity.
     * Example: "some.example.com"
     */
    private String webDomain;

}
