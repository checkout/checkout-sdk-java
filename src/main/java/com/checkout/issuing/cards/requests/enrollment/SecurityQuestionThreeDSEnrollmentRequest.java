package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SecurityQuestionThreeDSEnrollmentRequest extends ThreeDSEnrollmentRequest {

    @SerializedName("security_pair")
    private SecurityPair securityPair;

    @Builder
    private SecurityQuestionThreeDSEnrollmentRequest(final String locale,
                                                     final Phone phoneNumber,
                                                     final SecurityPair securityPair) {
        super(locale, phoneNumber);
        this.securityPair = securityPair;
    }
}
