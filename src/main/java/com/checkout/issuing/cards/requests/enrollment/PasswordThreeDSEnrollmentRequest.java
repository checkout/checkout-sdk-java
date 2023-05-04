package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PasswordThreeDSEnrollmentRequest extends ThreeDSEnrollmentRequest {

    private String password;

    @Builder
    private PasswordThreeDSEnrollmentRequest(final String locale,
                                             final Phone phoneNumber,
                                             final String password) {
        super(locale, phoneNumber);
        this.password = password;
    }
}