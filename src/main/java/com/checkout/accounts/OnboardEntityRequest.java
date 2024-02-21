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

    @SerializedName("contact_details")
    private ContactDetails contactDetails;

    private Profile profile;

    private Company company;

    private Individual individual;

    private OnboardSubEntityDocuments documents;

}
