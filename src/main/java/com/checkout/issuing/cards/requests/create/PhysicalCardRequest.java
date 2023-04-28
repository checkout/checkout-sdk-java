package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
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
public class PhysicalCardRequest extends CardRequest {

    @SerializedName("shipping_instructions")
    private ShippingInstruction shippingInstructions;

    @Builder
    private PhysicalCardRequest(ShippingInstruction shippingInstructions) {
        super(CardType.PHYSICAL);
        this.shippingInstructions = shippingInstructions;
    }

    private PhysicalCardRequest() {
        super(CardType.PHYSICAL);
    }
}
