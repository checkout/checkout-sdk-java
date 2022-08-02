package com.checkout.accounts;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class OnboardEntityDetailsResponse extends Resource {

    private String id;

    private String reference;

    private OnboardingStatus status;

    private Capabilities capabilities;

    @SerializedName("requirements_due")
    private List<RequirementsDue> requirementsDue;

    @SerializedName("contact_details")
    private ContactDetails contactDetails;

    private Profile profile;

    private Company company;

    private Individual individual;

    private List<Instrument> instruments;

}
