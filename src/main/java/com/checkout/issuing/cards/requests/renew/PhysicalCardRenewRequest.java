package com.checkout.issuing.cards.requests.renew;

import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.requests.create.ShippingInstruction;
import com.checkout.issuing.cards.requests.update.CardMetadata;
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
public class PhysicalCardRenewRequest extends RenewCardRequest {

    @SerializedName("shipping_instructions")
    private ShippingInstruction shippingInstructions;

    @Builder
    private PhysicalCardRenewRequest(final String displayName,
                                    final String reference,
                                    final CardMetadata metadata,
                                    final ShippingInstruction shippingInstructions) {
        super(CardType.PHYSICAL, displayName, reference, metadata);
        this.shippingInstructions = shippingInstructions;
    }
}