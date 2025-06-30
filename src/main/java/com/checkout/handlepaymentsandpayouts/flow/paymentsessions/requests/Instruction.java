package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PurposeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Instruction {

    /**
     * The purpose of the payment. This field might be required for AFT transactions depending on the card's
     * issuer_country. To view a card's issuer_country, retrieve the card's metadata. See the AFT documentation page for
     * more details.
     */
    private PurposeType purpose;

}
