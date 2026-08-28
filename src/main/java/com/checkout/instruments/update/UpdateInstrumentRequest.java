package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Data;

/**
 * The shared properties of a payment instrument being updated.
 *
 * <p>Each concrete variant fixes the type in its constructor. The specification declares no
 * required list on the update variants, but the type is the discriminator that selects which
 * variant the API validates against, so it is always sent.
 */
@Data
public abstract class UpdateInstrumentRequest {

    /**
     * The type of instrument to be updated.
     * [Required]
     */
    protected final InstrumentType type;

    protected UpdateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
