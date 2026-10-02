package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Certified authorised signatory document. Required when the legal representative or other role
 * owner is not registered on the certificate of incorporation. Representative documents only
 * ({@code company.representatives[].documents}), EEA, GB and US Company Full (3.0) and US ISV
 * Seller Company (3.0); not accepted at the top level.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CertifiedAuthorisedSignatory {

    /**
     * The type of document.
     * [Required]
     */
    private CertifiedAuthorisedSignatoryType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
