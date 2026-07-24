package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class OnboardEntityRequest {

    private String reference;

    private boolean isDraft;

    private Profile profile;

    private ContactDetails contactDetails;

    private Company company;

    private ProcessingDetails processingDetails;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; a sub-entity is represented via
     * {@link #company} (with representatives). Retained for v2.0 compatibility.
     */
    @Deprecated
    private Individual individual;

    private OnboardSubEntityDocuments documents;

    /**
     * @deprecated Not present in the current Accounts API schema
     * retained for backwards compatibility only.
     */
    @Deprecated
    private AdditionalInfo additionalInfo;

    /**
     * Identifier of a seller category configured on your platform during onboarding.
     * Categories define the pricing, capabilities, and risk profile applied to sub-entities;
     * ask your Checkout.com contact for the list available to your platform.
     * Used for US ISV onboarding variants.
     * [Optional]
     */
    private String sellerCategory;

    /**
     * Captures evidence of the end-user's consent to onboarding.
     * Used for US ISV onboarding variants.
     * [Optional]
     *
     * @deprecated Not present in the current Accounts API schema
     * retained for backwards compatibility only.
     */
    @Deprecated
    private Submitter submitter;

    private AgreedTerms agreedTerms;

}
