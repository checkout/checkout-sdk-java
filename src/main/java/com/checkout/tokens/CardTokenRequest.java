package com.checkout.tokens;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class CardTokenRequest {

    private final TokenType type = TokenType.CARD;

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private String cvv;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

}
