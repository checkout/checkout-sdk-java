package com.checkout.marketplace;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class OnboardEntityResponse extends Resource {

    private String id;

    private String reference;

    private Capabilities capabilities;

    private OnboardingStatus status;

    @SerializedName("requirements_due")
    private List<RequirementsDue> requirementsDue;

}
