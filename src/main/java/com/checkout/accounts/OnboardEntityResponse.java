package com.checkout.accounts;

import com.checkout.common.Resource;
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

    private OnboardingStatus status;

    private Capabilities capabilities;

    private List<RequirementsDue> requirementsDue;

}
