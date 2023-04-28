package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThreeDSUpdateRequest {

    @SerializedName("security_pair")
    private SecurityPair securityPair;

    private String password;

    private String locale;

    @SerializedName("phone_number")
    private Phone phoneNumber;
}
