package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Memorandum or articles of association document, supplied when onboarding a sub-entity.
 *
 * <p>Required on EEA and GB Company Full (3.0); optional on US Company Full (3.0) and the US ISV
 * Seller variants. The API expects an object carrying the
 * document type and the uploaded file ID, which is why this class exists: the field on
 * {@link OnboardSubEntityDocuments} used to be the {@link ArticlesOfAssociationType} enum, so
 * the SDK serialized a bare string and the API rejected the request.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ArticlesOfAssociation {

    /**
     * The type of document used.
     * [Required]
     */
    private ArticlesOfAssociationType type;

    /**
     * The ID of the front side of the document as represented within Checkout.com systems.
     * [Required]
     * ^file_[a-z2-7]{26}$
     * 31 characters
     */
    private String front;

}
