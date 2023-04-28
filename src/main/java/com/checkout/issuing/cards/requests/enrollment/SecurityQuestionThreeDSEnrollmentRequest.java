package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SecurityQuestionThreeDSEnrollmentRequest extends AbstractThreeDSEnrollmentRequest {

    @SerializedName("security_pair")
    private SecurityPair securityPair;

    @Builder
    public SecurityQuestionThreeDSEnrollmentRequest(String locale, Phone phoneNumber, SecurityPair securityPair) {
        super(locale, phoneNumber);
        this.securityPair = securityPair;
    }
}
