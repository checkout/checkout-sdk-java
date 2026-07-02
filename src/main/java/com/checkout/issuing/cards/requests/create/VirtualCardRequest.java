package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.checkout.issuing.controls.requests.create.CardControlRequest;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class VirtualCardRequest extends CardRequest {

    private Boolean isSingleUse;

    /**
     * The credentials to retrieve on card creation.
     * [Optional]
     */
    private List<ReturnCredentialsType> returnCredentials;

    /**
     * The control profiles you want to add the card to as a target.
     * [Optional]
     */
    private List<String> controlProfiles;

    /**
     * The controls that will be set on the card.
     * [Optional]
     */
    private List<CardControlRequest> controls;

    @Builder
    private VirtualCardRequest(final String cardholderId,
                               final CardLifetime lifetime,
                               final String reference,
                               final String cardProductId,
                               final String displayName,
                               final Boolean activateCard,
                               final Boolean isSingleUse,
                               final IssuingCardMetadata metadata,
                               final LocalDate revocationDate,
                               final String activationDate,
                               final List<ReturnCredentialsType> returnCredentials,
                               final List<String> controlProfiles,
                               final List<CardControlRequest> controls) {
        super(CardType.VIRTUAL, cardholderId, lifetime, reference, cardProductId, displayName, activateCard);
        this.isSingleUse = isSingleUse;
        this.returnCredentials = returnCredentials;
        this.controlProfiles = controlProfiles;
        this.controls = controls;
        setMetadata(metadata);
        setRevocationDate(revocationDate);
        setActivationDate(activationDate);
    }
}