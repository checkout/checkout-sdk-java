package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proof of residential address of the representative. Representative documents only
 * ({@code company.representatives[].documents}), EEA Sole Trader Full (3.0); not accepted at the
 * top level.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProofOfResidentialAddress {

    /**
     * The type of document being used as address verification.
     * [Required]
     */
    private ProofOfResidentialAddressType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
