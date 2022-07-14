package com.checkout.instruments.update;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpdateInstrumentResponse extends HttpMetadata {

    private String fingerprint;

}
