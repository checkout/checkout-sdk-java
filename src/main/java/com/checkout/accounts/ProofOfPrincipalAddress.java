package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proof of the company's principal place of business.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProofOfPrincipalAddress {

    /**
     * The type of document being used as address verification.
     * [Required]
     */
    private ProofOfPrincipalAddressType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
