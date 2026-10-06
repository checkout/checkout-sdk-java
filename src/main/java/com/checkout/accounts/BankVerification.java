package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A document showing transactions from the last 3 months.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BankVerification {

    /**
     * The type of document being used as bank verification.
     * [Required]
     */
    private BankVerificationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
