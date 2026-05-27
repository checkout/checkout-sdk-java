package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("is_draft")
    private boolean isDraft;

    private Profile profile;

    @SerializedName("contact_details")
    private ContactDetails contactDetails;

    private Company company;

    private ProcessingDetails processingDetails;

    private Individual individual;

    private OnboardSubEntityDocuments documents;

    @SerializedName("additional_info")
    private AdditionalInfo additionalInfo;

    /**
     * Identifier of a seller category configured on your platform during onboarding.
     * Categories define the pricing, capabilities, and risk profile applied to sub-entities;
     * ask your Checkout.com contact for the list available to your platform.
     * Used for US ISV onboarding variants.
     * [Optional]
     */
    @SerializedName("seller_category")
    private String sellerCategory;

    /**
     * Captures evidence of the end-user's consent to onboarding.
     * Used for US ISV onboarding variants.
     * [Optional]
     */
    private Submitter submitter;

}
