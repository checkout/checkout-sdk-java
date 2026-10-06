package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proof of the sole trader's registration, for example an extract from a trade register.
 * Representative documents only ({@code company.representatives[].documents}), EEA Sole Trader
 * Full (3.0); not accepted at the top level.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProofOfRegistration {

    /**
     * The type of document being used as proof of registration.
     * [Required]
     */
    private ProofOfRegistrationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
