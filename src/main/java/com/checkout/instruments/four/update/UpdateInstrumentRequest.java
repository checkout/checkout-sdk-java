package com.checkout.instruments.four.update;

import com.checkout.common.four.InstrumentType;
import lombok.Data;

@Data
public abstract class UpdateInstrumentRequest {

    protected final InstrumentType type;

    public UpdateInstrumentRequest(final InstrumentType type) {
        this.type = type;
    }

}
