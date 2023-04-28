package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SecurityQuestionThreeDSEnrollmentRequest extends AbstractThreeDSEnrollmentRequest {

    @SerializedName("security_pair")
    private SecurityPair securityPair;

    public SecurityQuestionThreeDSEnrollmentRequest(String locale, Phone phoneNumber, SecurityPair securityPair) {
        super(locale, phoneNumber);
        this.securityPair = securityPair;
    }
}
