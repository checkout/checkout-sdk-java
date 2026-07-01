package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PhysicalCardRequest extends CardRequest {

    private ShippingInstruction shippingInstructions;

    @Builder
    private PhysicalCardRequest(final String cardholderId,
                                final CardLifetime lifetime,
                                final String reference,
                                final String cardProductId,
                                final String displayName,
                                final Boolean activateCard,
                                final ShippingInstruction shippingInstructions,
                                final IssuingCardMetadata metadata,
                                final LocalDate revocationDate,
                                final String activationDate) {
        super(CardType.PHYSICAL, cardholderId, lifetime, reference, cardProductId, displayName, activateCard);
        this.shippingInstructions = shippingInstructions;
        setMetadata(metadata);
        setRevocationDate(revocationDate);
        setActivationDate(activationDate);
    }

}