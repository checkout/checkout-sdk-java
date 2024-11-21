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

}
