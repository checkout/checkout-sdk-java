package com.checkout.instruments.beta.update;

import com.checkout.common.beta.InstrumentType;
import lombok.Data;

@Data
public abstract class UpdateInstrumentRequest {

    protected final InstrumentType type;

    public UpdateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
