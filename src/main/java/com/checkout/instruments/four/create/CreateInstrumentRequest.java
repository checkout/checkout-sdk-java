package com.checkout.instruments.four.create;

import com.checkout.common.four.InstrumentType;
import lombok.Data;

@Data
public abstract class CreateInstrumentRequest {

    protected final InstrumentType type;

    protected CreateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
