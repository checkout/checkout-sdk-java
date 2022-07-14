package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Data;

@Data
public abstract class UpdateInstrumentRequest {

    protected final InstrumentType type;

    protected UpdateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
