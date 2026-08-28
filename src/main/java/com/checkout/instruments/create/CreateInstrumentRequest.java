package com.checkout.instruments.create;

import com.checkout.common.InstrumentType;
import lombok.Data;

/**
 * The shared properties of a payment instrument being stored.
 *
 * <p>Each concrete variant fixes the type in its constructor, which is what selects the schema the
 * API validates the request against.
 */
@Data
public abstract class CreateInstrumentRequest {

    /**
     * The type of instrument.
     * [Required]
     */
    protected final InstrumentType type;

    protected CreateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
