package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of a certification associated with the identity verification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Certification {

    /**
     * The certification type.
     * [Optional]
     */
    private CertificationType type;

    /**
     * The certification data. The properties returned depend on the certification type.
     * [Optional]
     */
    private DiatfCertificationData data;

}
