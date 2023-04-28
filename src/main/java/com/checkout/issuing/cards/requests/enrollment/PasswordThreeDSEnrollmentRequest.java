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
public class PasswordThreeDSEnrollmentRequest extends AbstractThreeDSEnrollmentRequest {

    private String password;

    @Builder
    public PasswordThreeDSEnrollmentRequest(String locale, Phone phoneNumber, String password) {
        super(locale, phoneNumber);
        this.password = password;
    }
}
