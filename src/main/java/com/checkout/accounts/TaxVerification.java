package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IRS-issued Employer Identification Number document used to verify the entity's tax
 * identification (US variants).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TaxVerification {

    /**
     * The type of IRS-issued document used for tax verification.
     * [Required]
     */
    private TaxVerificationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
