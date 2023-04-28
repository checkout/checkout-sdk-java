package com.checkout.issuing.cards.requests.create;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingInstruction {

    @SerializedName("shipping_recipient")
    private String shippingRecipient;

    @SerializedName("shipping_address")
    private Address shippingAddress;

    @SerializedName("additional_comment")
    private String additionalComment;
}
