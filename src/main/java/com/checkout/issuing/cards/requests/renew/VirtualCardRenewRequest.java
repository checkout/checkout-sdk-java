package com.checkout.issuing.cards.requests.renew;

import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.requests.update.CardMetadata;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VirtualCardRenewRequest extends RenewCardRequest {

    @Builder
    private VirtualCardRenewRequest(final String displayName,
                                   final String reference,
                                   final CardMetadata metadata) {
        super(CardType.VIRTUAL, displayName, reference, metadata);
    }
}