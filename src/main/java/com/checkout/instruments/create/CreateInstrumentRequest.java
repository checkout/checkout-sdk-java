package com.checkout.instruments.create;

import com.checkout.common.InstrumentType;
import lombok.Data;

@Data
public abstract class CreateInstrumentRequest {

    protected final InstrumentType type;

    protected CreateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
