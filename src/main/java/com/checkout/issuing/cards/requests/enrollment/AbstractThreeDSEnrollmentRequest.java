package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractThreeDSEnrollmentRequest {

    private String locale;

    @SerializedName("phone_number")
    private Phone phoneNumber;
}
