package com.checkout.tokens;

import com.checkout.CheckoutArgumentException;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@AllArgsConstructor
public final class CardTokenRequest implements TokenRequest {

    private final String type = "card";

    private String number;

    @SerializedName("expiry_month")
    private int expiryMonth;

    @SerializedName("expiry_year")
    private int expiryYear;

    private String name;

    private String cvv;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    public CardTokenRequest(final String number, final int expiryMonth, final int expiryYear) {
        validateParams("number", number);
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new CheckoutArgumentException("The expiry month must be between 1 and 12");
        }
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    @Override
    public String getType() {
        return type;
    }

}