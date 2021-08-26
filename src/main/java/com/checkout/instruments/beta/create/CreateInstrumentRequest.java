package com.checkout.instruments.beta.create;

import com.checkout.common.beta.InstrumentType;
import lombok.Data;

@Data
public abstract class CreateInstrumentRequest {

    protected final InstrumentType type;

    protected CreateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
