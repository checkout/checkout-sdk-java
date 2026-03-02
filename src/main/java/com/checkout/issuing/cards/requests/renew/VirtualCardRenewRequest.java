package com.checkout.issuing.cards.requests.renew;

import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
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
                                   final IssuingCardMetadata metadata) {
        super(displayName, reference, metadata);
    }
}