package com.checkout.tokens;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardTokenResponse extends TokenResponse {

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    private String name;

}
