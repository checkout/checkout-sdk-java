package com.checkout.instruments;

import com.checkout.common.InstrumentType;
import lombok.Data;

@Data
public final class UpdateInstrumentResponse {

    private InstrumentType type;

    private String fingerprint;

}
