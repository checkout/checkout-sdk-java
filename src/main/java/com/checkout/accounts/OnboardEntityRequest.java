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

    private Individual individual;

    private OnboardSubEntityDocuments documents;

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
     */
    private Submitter submitter;

}
