package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Financial statement document. Becomes mandatory depending on the answer provided for
 * {@code annual_processing_volume}; the sub-entity's status changes to {@code requirements_due}
 * when it is needed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FinancialVerification {

    /**
     * The type of the file.
     * [Required]
     */
    private FinancialVerificationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
