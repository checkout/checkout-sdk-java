package com.checkout.handlepaymentsandpayouts.applepay.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UploadCertificateResponse extends Resource {

    /**
     * The identifier of the account domain
     * [Required]
     */
    private String id;

    /**
     * Hash of the certificate public key
     * [Required]
     */
    private String publicKeyHash;

    /**
     * When the certificate is valid from
     * [Required]
     */
    private Instant validFrom;

    /**
     * When the certificate is valid until
     * [Required]
     */
    private Instant validUntil;

}