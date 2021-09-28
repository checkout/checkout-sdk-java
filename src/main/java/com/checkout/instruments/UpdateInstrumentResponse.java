package com.checkout.instruments;

import lombok.Data;

@Data
public final class UpdateInstrumentResponse {

    private String type;

    private String fingerprint;

}
