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
    private PhysicalCardRequest(final String cardholderId,
                                final CardLifetime lifetime,
                                final String reference,
                                final String cardProductId,
                                final String displayName,
                                final Boolean activateCard,
                                final ShippingInstruction shippingInstructions) {
        super(CardType.PHYSICAL, cardholderId, lifetime, reference, cardProductId, displayName, activateCard);
        this.shippingInstructions = shippingInstructions;
    }

}