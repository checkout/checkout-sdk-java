package com.checkout.instruments.previous;

import com.checkout.HttpMetadata;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UpdateInstrumentResponse extends HttpMetadata {

    private InstrumentType type;

    private String fingerprint;

}
