package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class OnboardEntityRequest {

    private String reference;

    @SerializedName("is_draft")
    private boolean isDraft;

    private Profile profile;

    @SerializedName("contact_details")
    private ContactDetails contactDetails;

    private Company company;

    @SerializedName("processing_details")
    private ProcessingDetails processingDetails;

    private Individual individual;

    private OnboardSubEntityDocuments documents;

    @SerializedName("additional_info")
    private AdditionalInfo additionalInfo;

}
