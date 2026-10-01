package com.checkout.accounts;

import com.checkout.common.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The document to use to confirm an individual's identity ({@code identity_verification}): on a
 * representative (Accounts API v3.0), or at the top level of the v2.0 sole trader variants.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Document {

    /**
     * The type of document used for identity verification.
     * [Required]
     */
    private DocumentType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

    /**
     * The ID of the back side of the document as represented within Checkout.com systems.
     * [Optional]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String back;

}
