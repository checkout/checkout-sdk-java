package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The document to use to confirm the company's identity (certified by a power of attorney within
 * the last 3 months).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CompanyVerification {

    /**
     * The type of document used for company verification. {@code articles_of_association} is
     * accepted on the US Company (2.0) variants only.
     * [Required]
     */
    private CompanyVerificationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
