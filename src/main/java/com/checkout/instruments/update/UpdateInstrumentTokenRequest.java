package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Update an instrument from a Checkout.com token.
 *
 * <p>The current specification's update discriminator declares card, bank_account, sepa, ach and
 * bacs only, so there is no token schema to align this request against. It is retained for
 * backwards compatibility.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentTokenRequest extends UpdateInstrumentRequest {

    /**
     * The Checkout.com token.
     * [Optional]
     */
    private String token;

    @Builder
    private UpdateInstrumentTokenRequest(final String token) {
        super(InstrumentType.TOKEN);
        this.token = token;
    }

    public UpdateInstrumentTokenRequest() {
        super(InstrumentType.TOKEN);
    }

}
